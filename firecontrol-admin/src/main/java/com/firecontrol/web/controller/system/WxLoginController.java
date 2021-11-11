package com.firecontrol.web.controller.system;
import com.firecontrol.framework.cloud.sms.Sms;
import com.firecontrol.framework.shiro.service.SysPasswordService;
import com.firecontrol.framework.web.service.TokenService;
import com.firecontrol.system.domain.SysUser;
import com.firecontrol.system.service.ISysUserService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序端登录控制器
 * @date 2019-11-11 18:09:38
 **/
@RestController
@RequestMapping("/wx")
public class WxLoginController extends WxCommonController {
    @Autowired
    TokenService tokenService;
    @Autowired
    ISysUserService iSysUserService;
    @Autowired
    SysPasswordService sysPasswordService;

    /**
     * 微信登录接口
     * @date 2019-11-08 14:57:06
     **/
    @GetMapping("/wxlogin")
    public Map<String, Object> wxLogin(HttpServletRequest request, HttpServletResponse response,
                                       String userName, String password) {
        Map<String,Object> respMap = new HashMap<>(3);
        //查询用户
        String flag=FALSE;
        SysUser sysUser = iSysUserService.selectUserByLoginName(userName);
        if (sysUser!=null){
            boolean matches = sysPasswordService.matches(sysUser, password);
            if (matches){
                //生成Token
                String token = tokenService.generateToken(sysUser.getUserName());
                //redis缓存token
                tokenService.saveToken(token,sysUser);
                respMap.put(TOKEN,token);
                //移除用户密码
                sysUser.setPassword("");
                respMap.put("sysUser",sysUser);
                flag=SUCCESSED;
            }
        }else {
            //电话号码不存在
            flag="PHONE_NOT_EXIST";
        }
        respMap.put(FLAG,flag);
        return  respMap;

    }

    /**
     * 微信小程序退出登录
     * @date 2019-11-14 16:38:25
     **/
    @GetMapping
    @RequestMapping("/loginOut")
    public String loginOut(@RequestParam("TOKEN") String token){
        if ( tokenService.delToken(token)){
            return SUCCESSED;
        }else {
            return FALSE;
        }
    }

    /**
     * 微信登录接口
     * @date 2019-11-08 14:57:06
     **/
    @GetMapping("/wxLoginSms")
    public Map<String, Object> wxLoginSms(HttpServletRequest request, HttpServletResponse response,
                                       String userName, String password) {
        Map<String,Object> respMap = new HashMap<>(3);
        //查询用户
        String flag=FALSE;
        SysUser sysUser = iSysUserService.selectUserByPhoneNumber(userName);
        if (sysUser!=null){
            String code = (String) request.getSession().getAttribute("WX_LOGIN_CODE");
            if (code != null && code.equals(password)){
                //生成Token
                String token = tokenService.generateToken(sysUser.getUserName());
                //redis缓存token
                tokenService.saveToken(token,sysUser);
                respMap.put(TOKEN,token);
                //移除用户密码
                sysUser.setPassword("");
                respMap.put("sysUser",sysUser);
                flag=SUCCESSED;
            }
        }else {
            //电话号码不存在
            flag="PHONE_NOT_EXIST";
        }
        respMap.put(FLAG,flag);
        return  respMap;

    }

    /**
     * 获取登录验证码
     * @return
     */
    @RequestMapping("/getLoginSms")
    public Map<String,Object> getLoginSms(@RequestParam(name = "phoneNumber", required = true) String phoneNumber,HttpServletRequest request){
        Sms sms = new Sms();
        long l = System.currentTimeMillis();
        String code = String.valueOf(l);
        request.getSession().setAttribute("WX_LOGIN_CODE", code);
        return sms.sendCodeSms(phoneNumber, code.substring(code.length() - 4));
    }

}

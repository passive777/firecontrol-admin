package com.firecontrol.web.controller.system;
import com.firecontrol.framework.shiro.service.SysPasswordService;
import com.firecontrol.framework.util.ShiroUtils;
import com.firecontrol.system.domain.SysUser;
import com.firecontrol.system.service.ISysUserService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 微信小程序端用户信息维护控制器
 * @date 2019-11-13 18:08
 */
@RequestMapping("/wx")
@RestController
public class WxSysUserController extends WxCommonController {

    @Autowired
    ISysUserService iSysUserService;

    @Autowired
    SysPasswordService passwordService;

    /**
     * 微信小程序更新用户信息（姓名+密码）
     * @date 2019-11-11 18:13:36
     **/
    @PostMapping("/editSysUser")
    @ResponseBody
    public int editSysUser(@RequestBody Map<String ,Object> map){
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            SysUser sysUser = gson.fromJson(json, SysUser.class);
            sysUser.setPassword(passwordService.encryptPassword(sysUser.getLoginName(),sysUser.getPassword(),sysUser.getSalt()));
            flag = iSysUserService.updateUserInfo(sysUser);
        }
        return flag;
    }

    /**
     * 微信小程序由单位超管人员添加用户(新增部门用户)
     * @date 2019-11-14 15:55:33
     **/
    @PostMapping("/addSysUser")
    @ResponseBody
    public int addSysUser(@RequestBody Map<String ,Object> map){
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            SysUser sysUser = gson.fromJson(json, SysUser.class);
            sysUser.setSalt(ShiroUtils.randomSalt());
            sysUser.setPassword(passwordService.encryptPassword(sysUser.getLoginName(),sysUser.getPassword(),sysUser.getSalt()));
            flag = iSysUserService.insertUser(sysUser);
        }
        return flag;
    }

}

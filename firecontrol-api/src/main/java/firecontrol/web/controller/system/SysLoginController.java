package firecontrol.web.controller.system;

import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.RedisUtil;
import com.firecontrol.common.utils.ServletUtils;
import com.firecontrol.common.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录验证
 * 
 * @author firecontrol
 */
@RequestMapping("/wx")
@Controller
public class SysLoginController extends BaseController
{
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/wxlogin")
    public String wxlogin(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("Get-wxlogin");
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "wxlogin";
    }

    @PostMapping("/wxlogin")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        System.out.println("Post-wxlogin");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/wxunauth")
    public String unauth()
    {
        return "error/unauth";
    }
}

package com.firecontrol.web.controller.system;

import com.firecontrol.framework.web.service.TokenService;
import com.firecontrol.module.domain.GroupDuty;
import com.firecontrol.module.service.IGroupDutyService;
import com.firecontrol.module.service.IGroupService;
import com.firecontrol.system.domain.SysUser;
import com.firecontrol.system.service.ISysUserService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序端管辖域责任人信息控制器
 * @date 2020-02-20 20:54
 */
@RequestMapping("/wx")
@RestController
public class WxGroupDutyController extends WxCommonController {
    @Autowired
    private IGroupDutyService iGroupDutyServicel;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private IGroupService iGroupService;
    @Autowired
    TokenService tokenService;
    private Object Exception;

    /**
     * 微信小程序添加责任人
     * @date 2020-02-16 20:55:33
     **/
    @PostMapping("/addGroupDuty")
    @ResponseBody
    public int addGroupDuty(@RequestBody Map<String, Object> map) {
        String json = analysisPostBefore(map);
        int flag = 0;
        if (json != null || !json.equals("")) {
            try {
                GroupDuty groupDuty = gson.fromJson(json, GroupDuty.class);
                flag = iGroupDutyServicel.insertGroupDuty(groupDuty);
                if (flag == 1) {
                    //更新人员信息
                    SysUser sysUser = new SysUser();
                    sysUser.setUserId(Long.valueOf(groupDuty.getUserId()));
                    sysUser.setGroupId(groupDuty.getGroupId());
                    iSysUserService.updateUserInfo(sysUser);
                } else {
                    throw (Throwable) Exception;
                }
            } catch (Exception e) {
                flag=0;
                e.printStackTrace();
            } catch (Throwable throwable) {
                flag=0;
                throwable.printStackTrace();
            }

        }
        return flag;
    }

    /**
     * 微信小程序查询该单位仍未指派为责任人或管辖员的用户
     * @date 2020-02-16 21:04:24
     **/
    @PostMapping("/listGroupDuty")
    @ResponseBody
    public Map<String, Object> listGroupDuty(@RequestBody Map<String, Object> map) {
        Map<String, Object> respMap = new HashMap<>(2);
        String flag = FALSE;
        String json = analysisPostBefore(map);
        if (json != null || !json.equals("")) {
            SysUser sysUser = gson.fromJson(json, SysUser.class);
            List<SysUser> sysUserList =iSysUserService.selectUserListByGroupIdForNull(sysUser.getDeptId());
            flag = SUCCESSED;
            respMap.put("sysUserList",sysUserList);
        }
        respMap.put(FLAG, flag);
        return respMap;
    }

    /**
     * 微信小程序查询指定管辖域责任人
     * @date 2020-02-18 11:34:55
     **/
    @PostMapping("/listGroupDutyByGroupId")
    @ResponseBody
    public Map<String, Object> listGroupDutyByGroupId(@RequestBody Map<String, Object> map) {
        Map<String, Object> respMap = new HashMap<>(2);
        String flag = FALSE;
        String json = analysisPostBefore(map);
        if (json != null || !json.equals("")) {
            GroupDuty groupDuty = gson.fromJson(json, GroupDuty.class);
            List<SysUser> groupDutyList = iGroupDutyServicel.selectGroupDutyListByGroupId(groupDuty.getGroupId());
            flag = SUCCESSED;
            respMap.put("groupDutyList", groupDutyList);
        }
        respMap.put(FLAG, flag);
        return respMap;
    }

    /**
     * 微信小程序删除指定管辖域的责任人
     * @date 2020-02-18 12:51:09
     **/
    @PostMapping("/delGroupDuty")
    @ResponseBody
    public int delGroupDuty(@RequestBody Map<String, Object> map) {
        String json = analysisPostBefore(map);
        int flag = 1;
        if (json != null || !json.equals("")) {
            try {
                GroupDuty groupDuty = gson.fromJson(json, GroupDuty.class);
                iGroupDutyServicel.deleteGroupDutyByUser(Long.valueOf(groupDuty.getUserId()));
                iSysUserService.updateGroup(Long.valueOf(groupDuty.getUserId()));
            } catch (Exception e) {
                flag=0;
                e.printStackTrace();
            }
        }
        return flag;
    }
}

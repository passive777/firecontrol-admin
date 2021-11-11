package com.firecontrol.web.controller.system;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.domain.GroupPatrol;
import com.firecontrol.module.service.IGroupPatrolService;
import com.firecontrol.system.domain.SysUser;
import com.firecontrol.system.service.ISysUserService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序端管辖员信息控制器
 *
 * @date 2019-11-20 11:27
 */
@RequestMapping("/wx")
@RestController
public class WxGroupPatrolController extends WxCommonController {
    @Autowired
    private IGroupPatrolService iGroupPatrolServicel;

    @Autowired
    private ISysUserService iSysUserService;

    private Object Exception;

    /**
     * 查询指定管辖域管辖员
     * @date 2019-11-18 11:34:55
     **/
    @PostMapping("/listGroupPatrolByGroupId")
    @ResponseBody
    public Map<String, Object> listGroupPatrolByGroupId(@RequestBody Map<String, Object> map) {
        Map<String, Object> respMap = new HashMap<>(2);
        String flag = FALSE;
        String json = analysisPostBefore(map);
        if (json != null || !json.equals("")) {
            GroupPatrol groupPatrol = gson.fromJson(json, GroupPatrol.class);
            List<SysUser> groupPatrolList = iGroupPatrolServicel.selectGroupPatrolListByGroupId(groupPatrol.getGroupId());
            flag = SUCCESSED;
            respMap.put("groupPatrolList", groupPatrolList);
        }
        respMap.put(FLAG, flag);
        return respMap;
    }

    /**
     * 微信小程序删除指定管辖域管辖员
     * @date 2019-11-18 12:59:15
     **/
    @PostMapping("/delGroupPatrol")
    @ResponseBody
    public int delGroupPatrol(@RequestBody Map<String, Object> map) {
        String json = analysisPostBefore(map);
        int flag = 1;
        if (json != null || !json.equals("")) {
            try {
                GroupPatrol groupPatrol = gson.fromJson(json, GroupPatrol.class);
                iGroupPatrolServicel.deleteGroupPatrolByUser(Long.valueOf(groupPatrol.getUserId()));
                iSysUserService.updateGroup(Long.valueOf(groupPatrol.getUserId()));
            } catch (Exception e) {
                flag=0;
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 使用方所有管辖人员 分页数据 ----- 正在做
     * @param map
     * @return
     * spring
     */
    @PostMapping("/queryPatrolByDeptId")
    @ResponseBody
    public R queryPatrolByDeptId(@RequestBody Map<String, Object> map){
        return analysisPostBeforeB(map) ? iGroupPatrolServicel.queryPatrolByDeptId(map) : R.ok().put("data", null);
    }

    /**
     * 微信小程序添加管辖员
     * @date 2019-11-16 20:55:33
     **/
    @PostMapping("/addGroupPatrol")
    @ResponseBody
    public int addGroupPatrol(@RequestBody Map<String, Object> map) {
        String json = analysisPostBefore(map);
        int flag = 0;
        if (json != null || !json.equals("")) {
            try {
                GroupPatrol groupPatrol = gson.fromJson(json, GroupPatrol.class);
                flag = iGroupPatrolServicel.insertGroupPatrol(groupPatrol);
                if (flag == 1) {
                    //更新人员信息
                    SysUser sysUser = new SysUser();
                    sysUser.setUserId(Long.valueOf(groupPatrol.getUserId()));
                    sysUser.setGroupId(groupPatrol.getGroupId());
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
}

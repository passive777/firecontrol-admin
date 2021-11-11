package com.firecontrol.web.controller.system;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.domain.GroupLinkman;
import com.firecontrol.module.service.IGroupLinkmanService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序端报警联系人信息控制器
 * @date 2020-02-08 14:19
 */
@RestController
@RequestMapping("/wx")
public class WxGroupLinkmanController extends WxCommonController {
    @Autowired
    private IGroupLinkmanService iGroupLinkmanService;

    @PostMapping("/listGroupLinkman")
    @ResponseBody
    public Map<String,Object> listGroupLinkman(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        Map<String, Object> respMap = new HashMap<>(2);
        String flag = FALSE;
        if (json != null || !json.equals("")) {
            GroupLinkman groupLinkman = gson.fromJson(json, GroupLinkman.class);
            List<GroupLinkman> groupLinkmanList = iGroupLinkmanService.selectGroupLinkmanList(groupLinkman);//.selectGroupLinkmanListByImei(equipmentLinkman.getImei());
            respMap.put("data",groupLinkmanList);
            flag=SUCCESSED;
//            respMap.put("equipmentLinkmanList",equipmentLinkmanList);
        }
        respMap.put(FLAG,flag);
        return respMap;
    }

    /**
     * 微信小程序添加报警联系人
     * @date 2020-02-17 17:31:44
     **/
    @PostMapping("/addGroupLinkman")
    @ResponseBody
    public int addGroupLinkman(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        int flag = 0;
        if (json != null || !json.equals("")) {
            GroupLinkman equipmentLinkman = gson.fromJson(json, GroupLinkman.class);
            flag = iGroupLinkmanService.insertGroupLinkman(equipmentLinkman);
        }
        return flag;
    }
    /**
     * 微信小程序更新报警联系人
     * @date 2020-02-17 17:32:00
     **/
    @PostMapping("/editGroupLinkman")
    @ResponseBody
    public int editGroupLinkman(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        int flag = 0;
        if (json != null || !json.equals("")) {
            GroupLinkman equipmentLinkman = gson.fromJson(json, GroupLinkman.class);
            flag = iGroupLinkmanService.update(equipmentLinkman);
        }
        return flag;
    }

    /**
     * 微信小程序删除报警联系
     * @date 2020-02-17 17:32:13
     **/
    @PostMapping("/delGroupLinkman")
    @ResponseBody
    public int delGroupLinkman(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        int flag = 0;
        if (json != null || !json.equals("")) {
            GroupLinkman equipmentLinkman = gson.fromJson(json, GroupLinkman.class);
            flag = iGroupLinkmanService.deleteGroupLinkmanByIds(equipmentLinkman.getId().toString());
        }
        return flag;
    }

    /**
     * 查询指定 使用方下的所有报警联系人列表
     * @param map + token + deptId
     * @return
     */
    @PostMapping("/queryGroupLinkmanListByDeptId")
    @ResponseBody
    public R queryGroupLinkmanListByDeptId(@RequestBody Map<String ,Object> map) {
        return analysisPostBeforeB(map) ? iGroupLinkmanService.queryGroupLinkmanListByDeptId(map) : R.error().put("data", null);
    }

}

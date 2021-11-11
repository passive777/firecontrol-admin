package com.firecontrol.web.controller.system;

import com.firecontrol.common.annotation.Log;
import com.firecontrol.common.enums.BusinessType;
import com.firecontrol.common.utils.bean.R;
import com.firecontrol.framework.web.service.TokenService;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.service.IEquipmentService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序端设备信息控制器
 * @date 2020-02-14 15:59
 */
@RequestMapping("/wx")
@RestController
public class WxEquipmentController extends WxCommonController {
    @Autowired
    private IEquipmentService iEquipmentService;
    @Autowired
    TokenService tokenService;

    /**
     * 微信小程序新增设备
     * @date 2020-02-14 16:00:16
     **/
    @PostMapping("/addEquipment")
    @ResponseBody
    public int addEquipment(@RequestBody Map<String ,Object> map){
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            Equipment equipment = gson.fromJson(json, Equipment.class);
            flag = iEquipmentService.insertEquipment(equipment);
        }
        return flag;
    }

    /**
     * 微信小程序编辑设备信息
     * @date 2020-02-17 14:14:36
     **/
    @PostMapping("/editEquipment")
    @ResponseBody
    public int editEquipment(@RequestBody Map<String ,Object> map){
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            Equipment equipment = gson.fromJson(json, Equipment.class);
            flag = iEquipmentService.updateEquipment(equipment);
        }
        return flag;
    }
    /**
     * 微信小程序删除设备
     * @date 2020-02-17 14:15:05
     **/
    @Log(title = "设备删除", businessType = BusinessType.DELETE)
    @PostMapping("/delEquipment")
    @ResponseBody
    public int delEquipment(@RequestBody Map<String ,Object> map){
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            Equipment equipment = gson.fromJson(json, Equipment.class);
            flag = iEquipmentService.deleteEquipmentByIds(equipment.getId().toString());
        }
        return  flag;
    }


    /**
     * 微信小程序模糊查询所有设备
     * @date 2020-02-14 16:05:40
     **/
    @PostMapping("/listEquipment")
    @ResponseBody
    public Map<String,Object> listEquipment(@RequestBody Map<String ,Object> map){
        String json = analysisPostBefore(map);
        Map<String,Object> respMap=new HashMap<>(2);
        String flag=FALSE;
        if (json!=null||!json.equals("")){
            String id =(String) map.get("id");
            //Equipment equipment = gson.fromJson(json, Equipment.class);
            //List<Equipment> equipmentList = iEquipmentService.selectEquipmentList(equipment);
            List<Equipment> equipments = iEquipmentService.groupEqumentList(Integer.valueOf(id), null);
            respMap.put("equipmentList",equipments);
            flag=SUCCESSED;
        }
        respMap.put(FLAG,flag);
        return respMap;
    }
    /**
     * 微信小程序根据ID查询指定设备详细信息
     * @date 2020-02-14 16:11:45
     **/
    @PostMapping("/equipmentById")
    @ResponseBody
    public Map<String,Object> equipmentById(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        Map<String,Object> respMap=new HashMap<>(2);
        String flag=FALSE;
        if (json!=null||!json.equals("")){
            Equipment equipment = gson.fromJson(json, Equipment.class);
            equipment = iEquipmentService.selectEquipmentById(equipment.getId());
            respMap.put("equipment",equipment);
            flag=SUCCESSED;
        }
        respMap.put(FLAG,flag);
        return respMap;
    }

    /**
     * 查询指定使用方 的所有设备 分页查询
     * @param map + token + deptId
     * @return
     */
    @PostMapping("/queryEquipmentByDeptId")
    @ResponseBody
    public R queryEquipmentByDeptId(@RequestBody Map<String ,Object> map) {
        return analysisPostBeforeB(map) ? iEquipmentService.queryEquipmentByDeptId(map) : R.error().put("data", null);
    }

}

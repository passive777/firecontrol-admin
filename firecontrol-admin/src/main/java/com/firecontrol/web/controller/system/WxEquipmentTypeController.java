package com.firecontrol.web.controller.system;

import com.firecontrol.framework.web.service.TokenService;
import com.firecontrol.module.domain.EquipmentType;
import com.firecontrol.module.service.IEquipmentTypeService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序端设备类型信息控制器
 *
 * @date 2019-12-14 16:57
 */
@RequestMapping("/wx")
@RestController
public class WxEquipmentTypeController extends WxCommonController {
    @Autowired
    private  IEquipmentTypeService iEquipmentTypeService;
    @Autowired
    TokenService tokenService;
    /**
     * 微信小程序请求设备类型列表
     * @date 2019-12-15 17:03:37
     **/
    @GetMapping("/listEquipmentType")
    @ResponseBody
    public Map<String, Object> listEquipmentType(@RequestParam("TOKEN") String token) {
        Map<String,Object> respMap=new HashMap<>(2);
        String flag=FALSE;
        if (tokenService.checkToken(token)){
            List<EquipmentType> equipmentTypeList = iEquipmentTypeService.selectEquipmentTypeList(new EquipmentType());
            respMap.put("equipmentTypeList",equipmentTypeList);
            flag=SUCCESSED;
        }
        respMap.put(FLAG,flag);
        return respMap;
    }

    /**
     * 根据适用房部门id按设备类型查询设备, 微信
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/equipmentTypeAndEquipment")
    public Map<String, Object> queryEquipmentTypeAndEquipmentBydeptId(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        Map<String,Object> respMap = new HashMap<>();
        String flag=FALSE;
        if (json!=null||!json.equals("")) {
            Integer deptId = (Integer)map.get("deptId");
            List<EquipmentType> type = iEquipmentTypeService.queryEquipmentTypeAndEquipmentBydeptId(deptId);
            respMap.put("data",type);
            flag=SUCCESSED;
        }
        respMap.put(FLAG,flag);
        return respMap;
    }

}

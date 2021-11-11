package com.firecontrol.web.controller.system;

import com.firecontrol.common.utils.DateUtils;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Group;
import com.firecontrol.module.domain.Install;
import com.firecontrol.module.service.IEquipmentService;
import com.firecontrol.module.service.IGroupService;
import com.firecontrol.module.service.IInstallService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序端安装点信息控制器
 * @date 2020-02-23 17:13
 */
@RestController
@RequestMapping("/wx")
public class WxInstallController extends WxCommonController {
    @Autowired
    private IInstallService iInstallService;

    @Autowired
    private IEquipmentService iEquipmentService;

    @Autowired
    private IGroupService iGroupService;

    private Object Exception;

    /**
     * 微信小程序新增安装点（安装设备）
     * @date 2020-02-14 16:00:16
     **/
    @PostMapping("/addInstall")
    @ResponseBody
    public int addInstall(@RequestBody Map<String ,Object> map){
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            try {
//                Equipment equipment=gson.fromJson(json,Equipment.class);
//                flag = iEquipmentService.insertEquipment(equipment);
//                if (flag==1){
                    Install install = gson.fromJson(json, Install.class);
                    //置入安装时间
                    install.setInstallTime(DateUtils.getNowDate());
                    flag = iInstallService.insertInstall(install);

                    //根据安装点上的管辖域查询对应的部门
                    Group group = iGroupService.selectGroupById(install.getGroupId());

                //2019.12.29修复bug（向安装的对应设备置入deptId部门ID）
                    Equipment equipment = gson.fromJson(json, Equipment.class);
                    equipment.setImei(install.getImei());
                    equipment.setDeptId(group.getDeptId());
                    equipment.setStatus(1);//安装状态 1
                    iEquipmentService.updateEquipmentDeptIdByImei(equipment);
//                }else {
//                    throw (Throwable) Exception;
//                }
            }catch (Exception e){
                flag=0;
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 微信小程序查询安装点列表
     * @date 2020-02-16 15:32:07
     **/
    @PostMapping("/listInstall")
    @ResponseBody
    public Map<String, Object> listInstall(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        Map<String ,Object> respMap=new HashMap<>(2);
        String  flag=FALSE;
        if (json!=null||!json.equals("")){
            Install install = gson.fromJson(json, Install.class);
            List<Install> installList = iInstallService.selectInstallList(install);
            respMap.put("installList",installList);
            flag=SUCCESSED;
        }
        respMap.put(FLAG,flag);
        return respMap;
    }

    /**
     * 微信小程序查询z指定id的安装点
     * @author spring
     **/
    @RequestMapping("/queryInstallById")
    @ResponseBody
    public Install queryInstallById(@RequestBody Map<String ,Object> map) {
        String id = (String) map.get("id");
        return analysisPostBeforeB(map) ? iInstallService.selectInstallById(Integer.valueOf(id)) : null;
    }

    /**
     * 微信小程序查询 使用方所有的设备信息
     * @author spring
     **/
    @RequestMapping("/queryInstallsByGroupId")
    @ResponseBody
    public Map<String, Object> queryInstallsByGroupId(@RequestBody Map<String ,Object> map) {
        return analysisPostBeforeB(map) ? iInstallService.queryInstallsByGroupId(map) : null;
    }

    /**
     * 微信小程序端解绑设备与安装点的关联
     * @date 2020-02-18 13:24:26
     **/
    @PostMapping("/delInstall")
    @ResponseBody
    public int delInstall(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        int  flag=0;
        if (json!=null||!json.equals("")){
                Install install = gson.fromJson(json, Install.class);
                flag = iInstallService.deleteInstallByImei(install.getImei());
        }
        return flag;
    }

    /**
     * 微信小程序删除安装点
     * @param map
     * @return
     * @author spring
     */
    @PostMapping("/deleteInstall")
    @ResponseBody
    public Map<String,Object> deleteInstall(@RequestBody Map<String ,Object> map) {
        Integer iid = (Integer)map.get("id");
        Long id = new Long(iid);
        return analysisPostBeforeB(map) ? iInstallService.deleteInstallById(id) : null;
    }

    /**
     * 修改安装点信息
     * @date 2020-02-18 14:09:50
     **/
    @PostMapping("/editInstall")
    @ResponseBody
    public int editInstall(@RequestBody Map<String ,Object> map) {
        String json = analysisPostBefore(map);
        int  flag=0;
        if (json!=null||!json.equals("")) {
//            Install install = gson.fromJson(json, Install.class);
//            flag = iInstallService.updateInstall(install);
            flag = iInstallService.updateInstallByMap(map);
        }
        return flag;
    }

}

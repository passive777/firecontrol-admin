package com.firecontrol.web.controller.system;
import com.alibaba.fastjson.JSON;
import com.firecontrol.common.annotation.Log;
import com.firecontrol.common.enums.BusinessType;
import com.firecontrol.framework.cloud.sms.HuaweiSms;
import com.firecontrol.framework.util.LogUtils;
import com.firecontrol.framework.web.service.TokenService;
import com.firecontrol.framework.websocket.WebSocketServer;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Group;
import com.firecontrol.module.domain.Install;
import com.firecontrol.module.domain.Linkman;
import com.firecontrol.module.service.*;
import com.firecontrol.system.domain.SysUser;
import com.firecontrol.web.controller.common.WxCommonController;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序端管辖域控制器
 * @date 2020-02-20 14:55
 */
@RequestMapping("/wx")
@RestController
public class WxGroupController  extends WxCommonController {
    @Autowired
    private IGroupService iGroupService;
    @Autowired
    TokenService tokenService;
    @Autowired
    private IEquipmentService equipmentService;
    @Autowired
    private IInstallService installService;
    @Autowired
    private IGroupLinkmanService groupLinkmanService;
    @Autowired
    private IGroupDutyService groupDutyService;
    @Autowired
    private IGroupPatrolService groupPatrolService;

    private static WxGroupController  wxGroupController ;

    // 华为云短信服务
    private HuaweiSms huaweiSms = new HuaweiSms();

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        wxGroupController = this;
        wxGroupController.equipmentService = this.equipmentService;
        // 初使化时将已静态化的testService实例化
    }

    /**
     * 微信小程序查询所有的管辖域
     * @date 2020-02-08 14:56:23
     **/
    @GetMapping("/listGroup")
    public Map<String ,Object> listGroup(HttpServletRequest request, HttpServletResponse response,
                 @RequestParam("TOKEN")String token) {
        String flag=FALSE;
        Map<String,Object> respMap=new HashMap<>(2);
        if (tokenService.checkToken(token)){
           //查询管辖域
            flag=SUCCESSED;
            List<Group> groupList = iGroupService.selectTierGroupList();
            respMap.put("groupList",groupList);
        }
        respMap.put(FLAG,flag);
        return  respMap;
    }

    /**
     * 微信小程序新增管辖域
     * @date 2020-02-11 18:10:09
     **/
    @PostMapping("/addGroup")
    @ResponseBody
    public int addGroup(@RequestBody Map<String,Object> map) {
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            String params = JSON.toJSONString(map);
            Group group = gson.fromJson(params, Group.class);
             flag = iGroupService.insertGroup(group);
        }
        return flag;
    }

    /**
     * 微信小程序更新管辖域
     * @date 2020-02-11 18:10:31
     **/
    @PostMapping("/editGroup")
    @ResponseBody
    public int editGroup(@RequestBody Map<String,Object> map) {
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            Group group = gson.fromJson(json, Group.class);
            flag = iGroupService.updateGroup(group);
        }
        return flag;
    }

    /**
     * 微信小程序删除管辖域
     * @date 2020-02-11 18:10:46
     **/
    @PostMapping("/delGroup")
    @ResponseBody
    public int delGroup(@RequestBody Map<String,Object> map) {
        String json = analysisPostBefore(map);
        int flag=0;
        if (json!=null||!json.equals("")){
            Group group = gson.fromJson(json, Group.class);
            flag = iGroupService.deleteGroupByIds(group.getId().toString());
        }
        return flag;
    }

    /**
     * 根据管辖域id查询当前管辖信息，及其子管辖域
     * @param map
     * @return Map<String, Object>
     * @author spring
     */
    @RequestMapping("/queryGroupByIdOfChilds")
    @ResponseBody
    public Map<String, Object> queryGroupByIdOfChilds(@RequestBody Map<String,Object> map){
        return analysisPostBeforeB(map) ? iGroupService.queryGroupByIdOfChilds(map) : null;
    }

    /**
     * 查询指定id的管辖域
     * @param map
     * @return
     * @author spring
     */
    @RequestMapping("/queryGroupById")
    @ResponseBody
    public Group queryGroupById(@RequestBody Map<String,Object> map){
        return analysisPostBeforeB(map) ? iGroupService.selectGroupById(Integer.valueOf((String)map.get("id"))) : null;
    }

    /**
     * 发送报警信息+报警广播
     * @date 2020-02-06 21:58:07
     **/
    @Log(title = "发送报警信息", businessType = BusinessType.INSERT)
    @GetMapping( "/sendsms")
    @ResponseBody
    public String sendAlarmSms(@RequestParam("imei") String imei)
    {
        Map<String,Object> reqMap = new HashMap<>();
        int code = 3;
        String msg="error";
        //判断imei是否有效
        Equipment equipment = null;
        try{
            equipment = equipmentService.selectEquipmentByImei(imei);
        }catch (Exception e){
            e.printStackTrace();
        }
        Group group=null;
        if (equipment != null){
            try{
                //查询该集群下需要被发送短信的号码
                Install install = new Install();
                install.setImei(imei);
                List<Install> installs = installService.selectInstallList(install);
                //敏感取出操作，易失败
                install = installs.get(0);
                //获得该设备所属集群
                Integer groupId = install.getGroupId();

                //查询该集群下所有紧急联系人
                List<Linkman> linkmens = groupLinkmanService.selectByGroup(groupId);
                //查询该集群下所有责任人
                List<SysUser> sysDutyUserList = groupDutyService.selectGroupDutyListByGroupId(groupId);
                for (int i = 0; i < sysDutyUserList.size(); i++) {
                    SysUser sysDutyUser = sysDutyUserList.get(i);
                    Linkman linkman = new Linkman(sysDutyUser.getUserName(),sysDutyUser.getPhonenumber());
                    linkmens.add(linkman);
                }
                //查询该集群下所有管辖员
                List<SysUser> sysPatrolUserList = groupPatrolService.selectGroupPatrolListByGroupId(groupId);
                for (int i = 0; i < sysPatrolUserList.size(); i++) {
                    SysUser sysPatrolUser = sysPatrolUserList.get(i);
                    Linkman linkman = new Linkman(sysPatrolUser.getUserName(),sysPatrolUser.getPhonenumber());
                    linkmens.add(linkman);
                }

                //查询集群信息
                 group = iGroupService.selectGroupById(groupId);
                if (group.getParentId() != 0){
                    //说明有上一级集群
                    Group fatherGroup = iGroupService.selectGroupById(group.getParentId());
                    //重写集群名字
                    group.setGroupName(fatherGroup.getGroupName()+group.getGroupName());
                }

                /**
                 * @Author soldier
                 * @Date 2020-06-16 下午20:06
                 * @Email:583406411@qq.com
                 * @Version 1.0
                 * @Description:发送报警短信
                 */
                boolean flag = huaweiSms.sendAlarmSms(linkmens, group, install, equipment);
                if (flag){
                    code = 0;
                    msg = "success";
                }else {
                    code = 1;
                    msg = "false";
                    LogUtils.logError("警告短信发送失败！！！--短信业务调用出现异常",new Exception("WxGroupController sendAlarmSms error"));
                }
            }catch (Exception e){
                e.printStackTrace();
                LogUtils.logError("警告短信发送失败！！！--逻辑处理出现异常",e);
            }

            try{
                //刷新集群实时通信进行浏览器报警
                WebSocketServer.sendAalarmMessage(equipment, group);
            }catch (Exception e){
                e.printStackTrace();
                LogUtils.logError("警告广播向客户端发送失败！！！",e);
            }
        }else {
            code = 2;
            msg = "IMEI error";
        }
        reqMap.put("code",code);
        reqMap.put("msg",msg);

        return new Gson().toJson(reqMap);
    }

    /**
     * 解除警报信息
     * @date 2020-02-26 21:58:07
     **/
    @Log(title = "解除报警信息", businessType = BusinessType.INSERT)
    @GetMapping( "/relieveAlarmSms")
    @ResponseBody
    public String relieveAlarmSms(@RequestParam("imei") String imei)
    {
        Map<String,Object> reqMap = new HashMap<>();
        int code = 3;
        String msg="error";
        //判断imei是否有效
        Equipment equipment = equipmentService.selectEquipmentByImei(imei);
        Group group=null;
        if (equipment != null){
            try{
                //查询该集群下需要被发送短信的号码
                Install install = new Install();
                install.setImei(imei);
                List<Install> installs = installService.selectInstallList(install);
                //敏感取出操作，易失败
                install = installs.get(0);
                //获得该设备所属集群
                Integer groupId = install.getGroupId();

                //查询该集群下所有紧急联系人
                List<Linkman> linkmen = groupLinkmanService.selectByGroup(groupId);
                //查询该集群下所有责任人
                List<SysUser> sysDutyUserList = groupDutyService.selectGroupDutyListByGroupId(groupId);
                for (int i = 0; i < sysDutyUserList.size(); i++) {
                    SysUser sysDutyUser = sysDutyUserList.get(i);
                    Linkman linkman = new Linkman(sysDutyUser.getUserName(),sysDutyUser.getPhonenumber());
                    linkmen.add(linkman);
                }
                //查询该集群下所有管辖员
                List<SysUser> sysPatrolUserList = groupPatrolService.selectGroupPatrolListByGroupId(groupId);
                for (int i = 0; i < sysPatrolUserList.size(); i++) {
                    SysUser sysPatrolUser = sysPatrolUserList.get(i);
                    Linkman linkman = new Linkman(sysPatrolUser.getUserName(),sysPatrolUser.getPhonenumber());
                    linkmen.add(linkman);
                }

                //查询集群信息
                 group = iGroupService.selectGroupById(groupId);
                if (group.getParentId() != 0){
                    //说明有上一级集群
                    Group fatherGroup = iGroupService.selectGroupById(group.getParentId());
                    //重写集群名字
                    group.setGroupName(fatherGroup.getGroupName()+group.getGroupName());
                }

                /**
                 * @Author soldier
                 * @Date 2020-06-16 下午20:06
                 * @Email:583406411@qq.com
                 * @Version 1.0
                 * @Description:发送报警解除短信
                 */
                boolean flag = huaweiSms.relieveAlarmSms(linkmen, group, install);
                if (flag){
                    code = 0;
                    msg = "success";
                }else {
                    code = 1;
                    msg = "false";
                    LogUtils.logError("解除警告短信发送失败！！！--短信业务调用出现异常",new Exception("WxGroupController relieveAlarmSms error"));
                }
            }catch (Exception e){
                e.printStackTrace();
                LogUtils.logError("解除警告短信发送失败！！！--逻辑处理出现异常",e);
            }

            try{
                //刷新集群实时通信进行浏览器解除报警
                WebSocketServer.sendRelieveAalarmMessage(equipment, group);
            }catch (Exception e){
                e.printStackTrace();
                LogUtils.logError("解除警告广播向客户端发送失败！！！",e);
            }
        }else {
            code = 2;
            msg = "IMEI error";
        }
        reqMap.put("code",code);
        reqMap.put("msg",msg);

        return new Gson().toJson(reqMap);
    }

//    @Test
//    public void utest() {
//        wxGroupController.sendAlarmSms("867726034473647");
//    }

}

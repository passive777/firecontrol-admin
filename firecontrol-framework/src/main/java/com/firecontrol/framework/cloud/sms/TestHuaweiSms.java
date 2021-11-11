package com.firecontrol.framework.cloud.sms;

//如果JDK版本低于1.8,请使用三方库提供Base64类
//import org.apache.commons.codec.binary.Base64;

import com.firecontrol.module.domain.*;

import java.util.ArrayList;
import java.util.List;

//如果JDK版本是1.8,可使用原生Base64类

/**
 * @Author soldier
 * @Date 20-6-16 上午9:46
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:测试华为云发送短信
 */

public class TestHuaweiSms {

    public static void main(String[] args) throws Exception{
        List<Linkman> linkmenList = new ArrayList<>();
        Linkman linkman = new Linkman();
        linkman.setTelephone("18776006499");
        linkmenList.add(linkman);

        Group group = new Group();
        group.setGroupName("睿奕科技园");

        Install install = new Install();
        install.setInstallLocation("软件实验室");

        Equipment equipment = new Equipment();
        AlarmType alarmType = new AlarmType();
        alarmType.setName("拆卸报警");
        equipment.setAlarmType(alarmType);


        /**
         * 向黄结发送一条报警通知短信：+8618277404022
         */
//        new HuaweiSms().sendAlarmSms(linkmenList, group, install, equipment);



        /**
         * 向黄结发送一条报警解除通知短信：+8618277404022
         */
        new HuaweiSms().relieveAlarmSms(linkmenList, group, install);
    }

}


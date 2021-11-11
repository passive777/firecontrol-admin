package com.firecontrol.framework.cloud.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.firecontrol.framework.cloud.AliConstant;
import com.firecontrol.framework.cloud.voice.alibaba.AliVoice;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Group;
import com.firecontrol.module.domain.Install;
import com.firecontrol.module.domain.Linkman;

import java.util.HashMap;
import java.util.List;

/**
 * @Author soldier
 * @Date 20-6-16 下午8:08
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:摒弃阿里云，使用华为云短信服务
 */
public class Sms {

    protected static String TEMPLATE_ALARM_CONFIRMATION = "SMS_172007086";//报警短信模板编号

    protected static String TEMPLATE_RELIEVE_ALARM_CONFIRMATION = "SMS_172007089";//解除报警短信模板编号

    protected static String SIGN_AODUNIOT = "奥盾iot";


    /**
     * 批量发送集群报警信息
     *
     * @date 2020-02-06 22:40:46
     **/
    public boolean sendAlarmSms(List<Linkman> linkmen, Group group, Install install, Equipment equipment) {
        boolean flag = false;

        String templateParamStr = "{\"a\":\"" + group.getGroupName() + "\",\"b\":\"" + install.getInstallLocation() + "\",\"c\":\"" + equipment.getEquipmentName() + "\"}";

        try {
            for (int i = 0; i < linkmen.size(); i++) {
                sendSms(linkmen.get(i).getTelephone(), templateParamStr, TEMPLATE_ALARM_CONFIRMATION, SIGN_AODUNIOT);
                AliVoice aliVoice = new AliVoice();
                aliVoice.callAlarm(linkmen.get(i).getTelephone(), "{\"address\":\"" + group.getProvince() + group.getCity() + group.getCounty() + group.getLocationDetail() + group.getGroupName() + install.getInstallLocation() + equipment.getEquipmentName() + "\"}");
                flag = true;
            }

        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 批量发送集群解除报警信息
     *
     * @date 2020-02-26 22:40:46
     **/
    public boolean relieveAlarmSms(List<Linkman> linkmen, Group group, Install install, Equipment equipment) {
        boolean flag = false;

        String templateParamStr = "{\"a\":\"" + group.getGroupName() + "\",\"b\":\"" + install.getInstallLocation() + "\",\"c\":\"" + equipment.getEquipmentName() + "\"}";

        try {
            for (int i = 0; i < linkmen.size(); i++) {
                sendSms(linkmen.get(i).getTelephone(), templateParamStr, TEMPLATE_RELIEVE_ALARM_CONFIRMATION, SIGN_AODUNIOT);
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    private void sendSms(String phoneNumberStr, String TemplateParam, String TemplateCode, String SignName) {
        DefaultProfile profile = DefaultProfile.getProfile("default", AliConstant.ACCESS_KEY_ID, AliConstant.ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phoneNumberStr);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("TemplateParam", TemplateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送短信验证码
     *
     * @return
     */
    public java.util.Map<String, Object> sendCodeSms(String phoneNumberStr, String code) {
        java.util.Map<String, Object> result = new HashMap<>();
        result.put("msg", "发送成功，请注意查收");
        this.sendSms(phoneNumberStr, "{\"code\":\"" + code + "\"}", TEMPLATE_ALARM_CONFIRMATION, SIGN_AODUNIOT);
        return result;
    }

   /* private static void sendSms2(){
        DefaultProfile profile = DefaultProfile.getProfile("default","LTAIHRuI7kaJMFBH", "nS9sbLJRq9H4yX2tk89Q7IwsW8NeS0");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", "15177436102 ");
        request.putQueryParameter("SignName", SIGN_AODUNIOT);
        request.putQueryParameter("TemplateCode", TEMPLATE_LOGIN_CONFIRMATION);
        request.putQueryParameter("TemplateParam","{\"code\":\"1234\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args) {

//        Sms sms = new Sms();
//        Map<String, Object> map = sms.sendCodeSms("18577409493", "12345");
//        System.out.println(map);
        //sendSms2();
      /*  List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        System.out.println(list.toString().replace(" ",""));
        String[] strings = list.toArray(new String[list.size()]);
        System.out.println(strings.toString());*/
    }


}

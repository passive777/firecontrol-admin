package com.firecontrol.framework.cloud.sms;

import com.firecontrol.framework.cloud.HuaweiSmsConstant;
import com.firecontrol.framework.cloud.voice.huawei.CallNotifyMain;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Group;
import com.firecontrol.module.domain.Install;
import com.firecontrol.module.domain.Linkman;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author soldier
 * @Date 20-6-16 下午12:09
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:华为云短信根据类
 */
public class HuaweiSms {

    /**
     * 发送集群报警信息
     * @param linkmenList   管辖域-联系人
     * @param group         管辖域
     * @param install       设备安装信息
     * @param equipment     设备表
     * 测试接口127.0.0.1:8788/wx/sendsms?imei=867726034473647
     */
    public boolean sendAlarmSms(List<Linkman> linkmenList, Group group, Install install, Equipment equipment) {

        boolean flag = false;

        String groupName = group.getGroupName();
        String installLocation = install.getInstallLocation();
        String name = equipment.getAlarmType().getName();
        if (groupName == null || installLocation == null || name == null) {
            return false;
        }
        // 短信模板变量
        String smsTemplateParas = "[\"" + "管辖域："+ groupName + "下的" + installLocation + "\",\"" + "：" + name + "\"]";
        // 语音通话模板
        List<String> voiceTemplateParas = new ArrayList<String>();
        voiceTemplateParas.add(groupName+ "下的" + installLocation);
        voiceTemplateParas.add(name);
        voiceTemplateParas.add(groupName+ "下的" + installLocation);
        voiceTemplateParas.add(name);

        // 短信接收人号码
        int size = linkmenList.size();
        if (size <= 0) return false;
        String receiver = getPhones(size, linkmenList);
        List<String> calleeNbrList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            calleeNbrList.add(linkmenList.get(i).getTelephone());
        }
        if (calleeNbrList.size() <= 0) {
            return false;
        }
        CallNotifyMain callNotifyMain = new CallNotifyMain();

        try {
            // 第一步：向报警联系人发送报警短信
            sendSms(smsTemplateParas, receiver, HuaweiSmsConstant.notifyAlarmTemplateId);

            // 第二步：向报警联系人打电话，语音电话服务--摒弃阿里云，使用华为云
//            AliVoice aliVoice = new AliVoice();
//            aliVoice.callAlarm(linkmen.get(i).getTelephone(), "{\"address\":\"" + group.getProvince() + group.getCity() + group.getCounty() + group.getLocationDetail() + group.getGroupName() + install.getInstallLocation() + equipment.getEquipmentName() + "\"}");
            /**
             * 打电话
             */
            flag = callNotifyMain.executeCallNotify(calleeNbrList, voiceTemplateParas);
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 发送集群解除报警信息
     */
    public boolean relieveAlarmSms(List<Linkman> linkmenList, Group group, Install install) {

        boolean flag = false;

        // 模板变量
        String templateParas = "[\"" + "管辖域："+ group.getGroupName() + "下的" + install.getInstallLocation() + "\"]";

        // 短信接收人号码
        int size = linkmenList.size();
        String receiver = getPhones(size, linkmenList);

        try {
            // 向报警联系人发送报警解除短信
            sendSms(templateParas, receiver, HuaweiSmsConstant.notifyRelieveAlarmTemplateId);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 发送短信
     * @param templateParas     模板变量
     * @param receiver          短信接收人号码
     * @param templateId        模板id
     */
    public void sendSms(String templateParas, String receiver,String templateId) throws Exception {

        // APP接入地址+接口访问URI
        String url = HuaweiSmsConstant.accessAddress + HuaweiSmsConstant.batchSendSmsApi;

        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "";

        //请求Body,不携带签名名称时,signature请填null
        String body = buildRequestBody(HuaweiSmsConstant.notifySender, receiver, templateId, templateParas, statusCallBack, HuaweiSmsConstant.notifySignature);
        if (null == body || body.isEmpty()) {
            System.out.println("body is null.");
            return;
        }

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(HuaweiSmsConstant.appKey, HuaweiSmsConstant.appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            System.out.println("wsse header is null.");
            return;
        }

        //如果JDK版本是1.8,可使用如下代码
        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                        (x509CertChain, authType) -> true).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpResponse response = client.execute(RequestBuilder.create(HuaweiSmsConstant.requestMethod) // 请求方法POST
                .setUri(url)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .addHeader(HttpHeaders.AUTHORIZATION, HuaweiSmsConstant.AUTH_HEADER_VALUE)
                .addHeader("X-WSSE", wsseHeader)
                .setEntity(new StringEntity(body)).build());

        System.out.println("===========================================");
        System.out.println(response.toString()); //打印响应头域信息
        System.out.println("===========================================");
        System.out.println(EntityUtils.toString(response.getEntity())); //打印响应消息实体
    }

    /**
     * 获得报警联系人电话
     */
    static String getPhones(int size, List<Linkman> linkmenList) {
        // 短信接收人号码
        StringBuilder telephoneStr = new StringBuilder();
        String receiver = null;
        for (int i = 0; i < size; i++) {
            // 该步即不会第一位有逗号，也防止最后一位拼接逗号！
            if (telephoneStr.length() > 0) {
                telephoneStr.append(",+86");
            }
            telephoneStr.append(linkmenList.get(i).getTelephone());
        }
        receiver = "+86" + telephoneStr.toString();
        return receiver;
    }

    /**
     * 构造请求Body体
     * @param sender            签名通道号
     * @param receiver          短信接收人号码
     * @param templateId        模板ID
     * @param templateParas     模板变量
     * @param statusCallbackUrl 短信状态报告接收地址
     * @param signature         签名名称,使用国内短信通用模板时填写
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallbackUrl, String signature) {
        if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
                || templateId.isEmpty()) {
            System.out.println("buildRequestBody() = sender, receiver or templateId is null.");
            return null;
        }
        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();

        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        if (null != templateParas && !templateParas.isEmpty()) {
            keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        }
        if (null != statusCallbackUrl && !statusCallbackUrl.isEmpty()) {
            keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));
        }
        if (null != signature && !signature.isEmpty()) {
            keyValues.add(new BasicNameValuePair("signature", signature));
        }

        return URLEncodedUtils.format(keyValues, Charset.forName("UTF-8"));
    }

    /**
     * 构造X-WSSE参数值
     * @param appKey        APP_Key
     * @param appSecret     APP_Secret
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            System.out.println("buildWsseHeader() = appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date()); //Created
        String nonce = UUID.randomUUID().toString().replace("-", ""); //Nonce

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);

        //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes()); //PasswordDigest

        return String.format(HuaweiSmsConstant.WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }
}

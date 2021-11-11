package com.firecontrol.framework.cloud.voice.huawei;

import com.firecontrol.framework.cloud.HuaweiVoiceConstant;
import com.firecontrol.framework.util.HttpsUtil;
import com.firecontrol.framework.util.JsonUtil;
import com.firecontrol.framework.util.StreamClosedHttpResponse;

import javax.net.ssl.HttpsURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author soldier
 * @Date 20-6-18 上午9:01
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:语音通知业务类实体
 */
public class CallNotify {

    // 语音通知API的调用地址
    private String urlCallNotify;
    // 接口响应的消息体
    private Map<String, String> Responsebody;
    // Https实体
    private HttpsUtil httpsUtil;

    public CallNotify() {
        Responsebody = new HashMap<>();
    }


    /**
     *
     * @param access_token  访问令牌，登录后自动获取
     * @param bindNbr       与号码管理页面的“号显号码”保持一致
     * @param displayNbr    与号码管理页面的“号显号码”保持一致
     * @param calleeNbrList 被叫号码集合
     * @param playInfoList  播放信息列表，最大支持5个，每个播放信息携带的参数都可以不相同
     */
    @SuppressWarnings("unchecked")
    public String callNotifyAPI(String access_token, String bindNbr, String displayNbr, List<String> calleeNbrList, List<Map<String, Object>> playInfoList) throws Exception {

        httpsUtil = new HttpsUtil();

        // 忽略证书信任问题
        httpsUtil.trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(httpsUtil.hv);

        // 语音通知API有v1.0和v2.0两个版本,请提前和管理员确认使用API的版本,并修改以下的值.
        // 注意：v要求小写.
        String apiVersion = "v2.0";

        // 构造URL,
        urlCallNotify = HuaweiVoiceConstant.CALL_NOTIFY_COMERCIAL + apiVersion + "?app_key=" + HuaweiVoiceConstant.CALLNOTIFYVERIFY_APPID + "&access_token=" + access_token;

        // 构造消息体
        Map<String, Object> bodys = new HashMap<>();
        bodys.put("bindNbr", bindNbr);
        bodys.put("displayNbr", displayNbr);
        bodys.put("playInfoList", playInfoList);
        String jsonRequest = null;
        StreamClosedHttpResponse responseCallNotify = null;

        for (String calleeNbr : calleeNbrList) {
            bodys.put("calleeNbr", calleeNbr);
            jsonRequest = JsonUtil.jsonObj2Sting(bodys);

            /*
             * Content-Type为application/json且请求方法为post时, 使用doPostJsonGetStatusLine方法构造http
             * request并获取响应.
             */
            responseCallNotify = httpsUtil.doPostJsonGetStatusLine(urlCallNotify, jsonRequest);
        }

        // 响应的消息体写入Responsebody.
        Responsebody = JsonUtil.jsonString2SimpleObj(responseCallNotify.getContent(), Responsebody.getClass());

        // 返回响应的status.
        return responseCallNotify.getStatusLine().toString();
    }

    /*
     * 构造playInfoList中携带的放音内容参数 使用v2.0版本接口的TTS模板作为放音内容 重构getplayInfo方法
     */
    public Map<String, Object> getplayInfo(String templateId, List<String> templateParas) {
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("templateId", templateId);
        bodys.put("templateParas", templateParas);
        return bodys;
    }

    // 获取整个响应消息体
    public Map<String, String> getResponsebody() {
        return this.Responsebody;
    }

    // 获取响应消息体中的单个参数
    public String getResponsePara(String ParaName) {
        return this.Responsebody.get(ParaName);
    }
}
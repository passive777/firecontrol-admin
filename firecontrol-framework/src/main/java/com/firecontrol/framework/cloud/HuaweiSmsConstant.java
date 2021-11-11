package com.firecontrol.framework.cloud;

/**
 * @Author soldier
 * @Date 20-6-18 上午8:57
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:华为云短信服务常量配置
 */
public class HuaweiSmsConstant {

    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    public static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    public static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    /**
     * 华为云配置信息
     */
    // APP接入地址
    public static final String accessAddress = "https://rtcsms.cn-north-1.myhuaweicloud.com:10743";
    // APP_Key
    public static final String appKey = "Y4P29zo6Mi27J15Hf94zFU9nKuTg";
    // APP_Secret
    public static final String appSecret = "31sJ8Cv0T8OiXKEOXa16u1jvJxPl";
    // 验证码类签名名称
    public static final String verifyCodeSignature = "华为云短信测试";
    // 验证码类签名通道号
    public static final String verifyCodeSender = "10690400999305242";
    // 验证码类模板ID
    public static final String verifyCodeTemplateId = "b8dab1a6197048329488084737538f4d";
    // 通知类签名名称
    public static final String notifySignature = "聚芯信息科技";
    // 通知类签名通道号
    public static final String notifySender = "8820061110307";
    // 通知类模板ID-消防报警通知
    public static final String notifyAlarmTemplateId = "bef1b5eb17d14c33ac398355a133c4bd";
    // 通知类模板ID-消防报警解除通知
    public static final String notifyRelieveAlarmTemplateId = "9d9369d713064556b9f3ca847d172257";
    // 发送短信API：该接口用于客户请求短信业务平台向指定用户发送短信，最多允许携带1000个号码；示例:+8615123456789,多个号码之间用英文逗号分隔
    public static final String batchSendSmsApi = "/sms/batchSendSms/v1";
    // 发送分批短信API：该接口用于客户请求短信平台向不同用户发送不同的短信，单条短信最多允许携带1000个号码，号码之间以英文逗号分隔
    public static final String batchSendDiffSmsApi = "/sms/batchSendDiffSms/v1";
    // 请求方法
    public static final String requestMethod = "POST";
    // 通信协议
    public static final String communicationProtocol = "HTTPS";
}

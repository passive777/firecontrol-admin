package com.firecontrol.framework.cloud;

/**
 * @Author soldier
 * @Date 20-6-18 上午9:05
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:华为云语音通话服务常量配置
 */
public class HuaweiVoiceConstant {

    /**
     * 语音通话的开发者帐号和密码
     * 在这个地址查看==》https://console.huaweicloud.com/voicecall/?region=cn-south-1#/commercial/overview
     */
    public static final String username = "hw26849153_vc";
    public static final String password = "Admin123!";

    // 使用演示时，请更换IP和端口
    public static final String BASE_URL = "https://rtccall.cn-north-1.myhuaweicloud.cn:443";

    // 语音通知APP_Key
    public static final String CALLNOTIFYVERIFY_APPID = "H7f9A2Cq8kkeJNa7CHSS4jq0r57u";
    // 语音通知APP_Secret
    public static final String CALLNOTIFYVERIFY_SECRET = "rHahFLxglX5jIS89JP12SiDw2sst";
    // 语音模板id
    public static final String callNotifyTemplateId = "f7f131ce72d34f34a519926ccdbae89c";
    /**
     * bindNbr      此字段定义CallEnabler业务号码，无需申请，会和号显号码一起下发;与号码管理页面的“绑定号码”保持一致
     * displayNbr   号显号码，被叫终端上显示的主叫号码，需要提前在添加号码订购页面申请该号码;与号码管理页面的“号显号码”保持一致
     */
    public static final String bindNbr = "+8607742500460";
    public static final String displayNbr = "+8607742500460";

    /*
     * 回调url的IP和端口
     * 使用演示时，请替换应用程序部署环境地址的IP和端口
     */
    public static final String CALLBACK_BASE_URL = "http://121.37.21.80:80";

    /*
     * 完整回调url
     * 使用演示时，请替换uri
     */
    public static final String STATUS_CALLBACK_URL = CALLBACK_BASE_URL + "/status";
    public static final String FEE_CALLBACK_URL = CALLBACK_BASE_URL + "/fee";

    //*************************** 以下常量不需要修改 *********************************//

    /*
     * request header
     * 1. HEADER_APP_AUTH
     */
    public static final String HEADER_APP_AUTH = "Authorization";

    /*
     * Application Access Security:
     * 1. APP_AUTH
     * 2. REFRESH_TOKEN
     */
    public static final String APP_AUTH = BASE_URL + "/rest/fastlogin/v1.0";
    public static final String REFRESH_TOKEN = BASE_URL + "/omp/oauth/refresh";
    public static final String DELETE_AUTH = BASE_URL + "/rest/logout/v1.0";
    public static final String REFRESH_OCEANSTOR = BASE_URL + "/rest/refreshkey/v2.0";

    /*
     * Voice Call:
     * 1. VOICE_CALL_COMERCIAL
     * 2. VOICE_VERIFICATION_COMERCIAL
     * 3. CALL_NOTIFY_COMERCIAL
     * 4. VOICE_FILE_DOWNLOAD
     */
    public static final String VOICE_CALL_COMERCIAL = BASE_URL + "/rest/httpsessions/click2Call/v2.0";
    public static final String VOICE_VERIFICATION_COMERCIAL = BASE_URL + "/rest/httpsessions/callVerify/v1.0";
    public static final String CALL_NOTIFY_COMERCIAL = BASE_URL + "/rest/httpsessions/callnotify/";
    public static final String VOICE_FILE_DOWNLOAD = BASE_URL + "/rest/provision/voice/record/v1.0";
}

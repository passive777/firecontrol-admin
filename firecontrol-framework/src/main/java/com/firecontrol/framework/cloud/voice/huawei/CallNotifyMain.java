package com.firecontrol.framework.cloud.voice.huawei;

import com.firecontrol.framework.cloud.HuaweiVoiceConstant;
import com.firecontrol.framework.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CallNotifyMain {
    private static final Logger logger = LoggerFactory.getLogger(CallNotifyMain.class);

    // 接口返回值
    private static String status = "";
    private static String resultcode = "";
    private static String resultdesc = "";
    // 鉴权接口
    public static String access_token = "";
    public static String refresh_token = "";
    public static int expires_in = 0;
    // 点击呼叫接口
    private static String sessionId = "";

    // 有效期计数器
    private static Timer timer = null;
    private static MyTask myTask = null;
    private static int expires_count = 0;

    // 鉴权类实体
    public static Auth callNotifyAuth = new Auth(HuaweiVoiceConstant.CALLNOTIFYVERIFY_APPID, HuaweiVoiceConstant.CALLNOTIFYVERIFY_SECRET);
    // 语音通知业务类实体
    public static CallNotify callNotifyAPI = new CallNotify();

    // 退出标识
    private static Boolean quit = false;
    // 调用接口成功标示
    private static final String success = "200";

    public static void main(String[] args) throws Exception {
        CallNotifyMain callNotifyMain = new CallNotifyMain();

//        String phone = "+8618277404022";
        String phone = "+8618776006499";
        List<String> phoneList= new ArrayList<>();
        phoneList.add(phone);
//        phone = "+8618776006499";
//        phoneList.add(phone);
//        phone = "+8618776676765";
//        phoneList.add(phone);


        List<String> templateParas = new ArrayList<>();
        templateParas.add("梧州学院下的软件实验室");
        templateParas.add("拆卸报警");
        templateParas.add("梧州学院下的软件实验室");
        templateParas.add("拆卸报警");

        callNotifyMain.executeCallNotify(phoneList, templateParas);
    }

    /**
     * 打电话
     * @param calleeNbrList 报警联系人电话List
     * @param templateParas 模板参数
     */
    public boolean executeCallNotify(List<String> calleeNbrList, List<String> templateParas) throws Exception {

        // 首先调用大客户SP简单认证API进行登录鉴权
        if (!CallNotifyMain.login()) {
            logger.error("语音通话服务出现错误："+ status + ",resultcode = " + resultcode + ",resultdesc = " + resultdesc);
            return false;
        }

        // 创建一个计时器对access_token的有效期（expires_in）进行倒计时,单位为秒.
        expires_count = expires_in;
        timer = new Timer();
        myTask = new MyTask(expires_count);
        timer.schedule(myTask, 1000);
//        timer.schedule(new TimerTask() {
//            public void run() {
//                --expires_count;
//            }
//        }, 1000);

        /*
         * 当有效期还剩下1/4时,建议调用刷新授权API刷新token. 循环刷新token并等待调用点击呼叫接口直至退出
         */
        while (!quit) {
            if (expires_count < (expires_in / 4)) {
                // 调用刷新授权API刷新token
                if (!CallNotifyMain.refresh()) {
                    logger.error("语音通话服务出现错误："+ status + ",resultcode = " + resultcode + ",resultdesc = " + resultdesc);
                    return false;
                }
                // 刷新倒计时
                expires_count = expires_in;
            }

            // 构造playInfoList参数
            List<Map<String, Object>> playInfoList = new ArrayList<Map<String, Object>>();
            // 使用v2.0版本接口的TTS模板作为第二段放音内容
            playInfoList.add(callNotifyAPI.getplayInfo(HuaweiVoiceConstant.callNotifyTemplateId, templateParas));

            // 调用doCallNotify方法
            boolean res = CallNotifyMain.doCallNotify(HuaweiVoiceConstant.bindNbr, HuaweiVoiceConstant.displayNbr, calleeNbrList, playInfoList);
            if (!res) {
                logger.error("语音通话服务出现错误："+ status + ",resultcode = " + resultcode + ",resultdesc = " + resultdesc);
                return false;
            }

            // 退出登录
            status = callNotifyAuth.logout(access_token);

            // 退出循环
            quit = true;
        }
        // 销毁timer
        timer.cancel();
        timer.purge();
        timer = null;
        // 取消任务
        myTask.cancel();
        myTask = null;
        return status.indexOf(success) != -1;
    }

    // 登录鉴权
    private static boolean login() throws Exception {

        // 调用登录鉴权接口
        status = callNotifyAuth.fastlogin(HuaweiVoiceConstant.username, HuaweiVoiceConstant.password);
        if (status.indexOf(success) != -1) {
            // 调用成功,记录token和有效期
            access_token = callNotifyAuth.getResponsePara("access_token");
            refresh_token = callNotifyAuth.getResponsePara("refresh_token");
            expires_in = Integer.parseInt(callNotifyAuth.getResponsePara("expires_in"));
            return true;
        } else {
            // 调用失败,获取错误码和错误描述
            resultcode = callNotifyAuth.getResponsePara("resultcode");
            resultdesc = callNotifyAuth.getResponsePara("resultdesc");
            // 处理错误
            CallNotifyMain.processError();
            // 每次重试之前等待70秒,避免接口返回登录太频繁的错误
            TimeUnit.SECONDS.sleep(70);
            return false;
        }
    }

    // 刷新token
    private static boolean refresh() throws Exception {

        // 调用刷新token接口
        status = callNotifyAuth.refresh(refresh_token);
        if (status.indexOf(success) != -1) {
            // 调用成功,刷新token和有效期
            access_token = callNotifyAuth.getResponsePara("access_token");
            refresh_token = callNotifyAuth.getResponsePara("refresh_token");
            expires_in = Integer.parseInt(callNotifyAuth.getResponsePara("expires_in"));
            return true;
        } else {
            // 调用失败,获取错误码和错误描述
            resultcode = callNotifyAuth.getResponsePara("resultcode");
            resultdesc = callNotifyAuth.getResponsePara("resultdesc");
            // 处理错误
            CallNotifyMain.processError();
            return false;
        }
    }

    /*
     * 发起语音通知呼叫
     */
    public static boolean doCallNotify(String bindNbr, String displayNbr, List<String> calleeNbrList, List<Map<String, Object>> playInfoList) {

        // 调用语音通知接口
        try {
            status = callNotifyAPI.callNotifyAPI(access_token, bindNbr, displayNbr, calleeNbrList, playInfoList);
            if (status.indexOf(success) != -1) {
                // 调用成功,记录返回的信息.
                resultcode = callNotifyAPI.getResponsePara("resultcode");
                resultdesc = callNotifyAPI.getResponsePara("resultdesc");
                sessionId = callNotifyAPI.getResponsePara("sessionId");
                return true;
            } else {
                // 调用失败,获取错误码和错误描述.
                resultcode = callNotifyAPI.getResponsePara("resultcode");
                resultdesc = callNotifyAPI.getResponsePara("resultdesc");
                // 处理错误
                CallNotifyMain.processError();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 当API的返回值不是200时,处理错误.
    private static void processError() throws InterruptedException {

        // 以下代码仅供调试使用,实际开发时请删除
        System.out.println(status);
        System.out.println(resultcode + " " + resultdesc);
//        LogUtils.logError("语音通话服务出现错误："+ status + ",resultcode = " + resultcode + ",resultdesc = " + resultdesc, new Exception("语音通话服务出现错误："+ status + ",resultcode = " + resultcode + ",resultdesc = " + resultdesc));
        System.exit(-1);
    }
}
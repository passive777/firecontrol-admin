package com.firecontrol.framework.cloud.voice.alibaba;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsRequest;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.firecontrol.framework.cloud.AliConstant;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author spring
 * email: 4298293220@qq.com
 * site: https://springbless.xin
 * @description 阿里巴巴语音服务工具类
 * @date 2020/2/21
 */
public class AliVoice {

    protected final Logger logger = LoggerFactory.getLogger(AliVoice.class);

    /**
     * 设备警报--单个多个报警人
     * @param number
     * @param msg "{\"address\":\"广西梧州万秀区\"}"
     */
    public void callAlarm(String number, String msg){
        this.callNumber(number, AliConstant.JINGBAO_VOICE_TEMPLATE, msg);
    }

    /**
     * 设备警报--多个报警人
     * @param numbers
     * @param msg "{\"address\":\"广西梧州万秀区\"}"
     */
    public void callAlarm(List<String> numbers, String msg){
        this.callNumber(numbers, AliConstant.JINGBAO_VOICE_TEMPLATE, msg);
    }

    /**
     * 拨打单个语音电话
     * @param number 指定拨打的电话号码
     * @param template 语音模板
     * @param msg 语音信息
     */
    public void callNumber(String number, String template, String msg){
        this.cloudCallNumber(number, template, msg);
    }

    /**
     * 拨打多个语音电话
     * @param numbers 指定拨打的电话号码
     * @param template 语音模板
     * @param msg 语音信息
     */
    public void callNumber(List<String> numbers, String template, String msg){
        if(numbers != null && numbers.size() > 0){
            for(int i = 0; i < numbers.size(); i++){
                this.cloudCallNumber(numbers.get(i), template, msg);
            }
        }
    }

    /**
     * 向云端发送语音拨打指令
     * @param number
     * @param template
     * @param msg
     */
    private void cloudCallNumber(String number, String template, String msg){
         try {
            //设置访问超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //云通信产品-语音API服务产品名称（产品名固定，无需修改）
            final String product = "Dyvmsapi";
            //产品域名（接口地址固定，无需修改）
            final String domain = "dyvmsapi.aliyuncs.com";
            //AK信息
            final String accessKeyId = AliConstant.ACCESS_KEY_ID;
            final String accessKeySecret = AliConstant.ACCESS_SECRET;
            //初始化acsClient 暂时不支持多region
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            SingleCallByTtsRequest request = new SingleCallByTtsRequest();
            //必填-被叫显号,可在语音控制台中找到所购买的显号
            request.setCalledShowNumber(AliConstant.VOICE_NUMBER);
            //必填-被叫号码
            request.setCalledNumber(number);
            //必填-Tts模板ID
            request.setTtsCode(template);
            //可选-当模板中存在变量时需要设置此值
            request.setTtsParam(msg);
            //可选-音量 取值范围 0--200
            //request.setVolume(100);
            //可选-播放次数
            //request.setPlayTimes(3);
            //可选-外部扩展字段,此ID将在回执消息中带回给调用方
            //request.setOutId("yourOutId");
            //hint 此处可能会抛出异常，注意catch
            SingleCallByTtsResponse singleCallByTtsResponse = acsClient.getAcsResponse(request);
             System.out.println(new Gson().toJson(singleCallByTtsResponse));
            if (singleCallByTtsResponse.getCode() != null && singleCallByTtsResponse.getCode().equals("OK")) {
                //请求成功
                logger.info("语音文本外呼---------------");
                logger.info("RequestId=" + singleCallByTtsResponse.getRequestId());
                logger.info("Code=" + singleCallByTtsResponse.getCode());
                logger.info("Message=" + singleCallByTtsResponse.getMessage());
                logger.info("CallId=" + singleCallByTtsResponse.getCallId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AliVoice aliVoice = new AliVoice();
        aliVoice.callAlarm("15777404603", "{\"address\":\"" + "广西梧州" + "\"}");
    }
}

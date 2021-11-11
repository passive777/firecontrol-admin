package com.firecontrol.framework.cloud.voice.huawei;

import java.util.TimerTask;

/**
 * @Author soldier
 * @Date 20-6-25 上午10:12
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:定时器工具，解决错误==》java.lang.IllegalStateException: Timer already cancelled
 */
public class MyTask extends TimerTask {
    private int expiresCount;

    public MyTask(int expires_count) {
        expiresCount = expires_count;
    }

    @Override
    public void run(){
        --expiresCount;
    }
}

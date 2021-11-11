package com.firecontrol.framework.config;

import com.firecontrol.common.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @date 2019-11-03 22:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class RedisTest {
   @Autowired
    private RedisUtil redisUtil;
    @Test
    public void test(){
        redisUtil.set("sample","redis测试");
        System.out.println("reids测试返回值："+redisUtil.get("sample"));
        redisUtil.del("0");
        redisUtil.del("1");
        redisUtil.del("110-dept-websocket");
        redisUtil.del("6-websocket");
    }
}

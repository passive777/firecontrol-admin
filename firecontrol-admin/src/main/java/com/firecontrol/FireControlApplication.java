package com.firecontrol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author firecontrol
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class FireControlApplication
{ 
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(FireControlApplication.class, args);
        System.out.println("智能消防设备监控预警平台启动成功！");
    }
}
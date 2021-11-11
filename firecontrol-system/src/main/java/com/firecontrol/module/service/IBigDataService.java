package com.firecontrol.module.service;

import java.util.Map;

/**
 * 数据可视化 服务层
 * 
 * @author Javan911
 * @date 2020-03-24
 */
public interface IBigDataService
{
    /**
     * 查询设备在线/离线数量
     * @return
     */
    public String eqOnOff();

    /**
     *
     * @return
     */
    String installs();
}

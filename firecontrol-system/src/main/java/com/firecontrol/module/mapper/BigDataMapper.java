package com.firecontrol.module.mapper;

/**
 * 大数据可视化 数据层
 * 
 * @author Fire
 * @date 2020-03-24
 */
public interface BigDataMapper
{
    /**
     * 查询移动设备在线数量
     * @return
     */
    public int zxYidong();

    /**
     * 查询移动设备离线数量
     * @return
     */
    public int lxYidong();
}
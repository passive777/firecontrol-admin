package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.EquipmentLog;
import java.util.List;	

/**
 * 操作日志记录 数据层
 * 
 * @author Fire
 * @date 2019-11-20
 */
public interface EquipmentLogMapper 
{
	/**
     * 查询操作日志记录信息
     * 
     * @param operId 操作日志记录ID
     * @return 操作日志记录信息
     */
	public EquipmentLog selectEquipmentLogById(Long operId);
	
	/**
     * 查询操作日志记录列表
     * 
     * @param equipmentLog 操作日志记录信息
     * @return 操作日志记录集合
     */
	public List<EquipmentLog> selectEquipmentLogList(EquipmentLog equipmentLog);
	
	/**
     * 新增操作日志记录
     * 
     * @param equipmentLog 操作日志记录信息
     * @return 结果
     */
	public int insertEquipmentLog(EquipmentLog equipmentLog);
	
	/**
     * 修改操作日志记录
     * 
     * @param equipmentLog 操作日志记录信息
     * @return 结果
     */
	public int updateEquipmentLog(EquipmentLog equipmentLog);
	
	/**
     * 删除操作日志记录
     * 
     * @param operId 操作日志记录ID
     * @return 结果
     */
	public int deleteEquipmentLogById(Long operId);
	
	/**
     * 批量删除操作日志记录
     * 
     * @param operIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteEquipmentLogByIds(String[] operIds);
	
}
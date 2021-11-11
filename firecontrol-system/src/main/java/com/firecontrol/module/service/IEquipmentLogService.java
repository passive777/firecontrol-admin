package com.firecontrol.module.service;

import com.firecontrol.module.domain.EquipmentLog;
import com.firecontrol.system.domain.SysOperLog;

import java.util.List;

/**
 * 操作日志记录 服务层
 * 
 * @author Fire
 * @date 2019-11-20
 */
public interface IEquipmentLogService 
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
     * 删除操作日志记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEquipmentLogByIds(String ids);

	/**
	 * 添加日志
	 * @date 2019-11-20 14:58:51
	 **/
	public void insertEquipmentLogForOperLog(SysOperLog operLog);
}

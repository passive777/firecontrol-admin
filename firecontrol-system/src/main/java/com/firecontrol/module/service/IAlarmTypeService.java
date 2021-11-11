package com.firecontrol.module.service;

import com.firecontrol.module.domain.AlarmType;
import java.util.List;

/**
 * 报警类型 服务层
 * 
 * @author Fire
 * @date 2019-12-04
 */
public interface IAlarmTypeService 
{
	/**
     * 查询报警类型信息
     * 
     * @param id 报警类型ID
     * @return 报警类型信息
     */
	public AlarmType selectAlarmTypeById(String id);
	
	/**
     * 查询报警类型列表
     * 
     * @param alarmType 报警类型信息
     * @return 报警类型集合
     */
	public List<AlarmType> selectAlarmTypeList(AlarmType alarmType);
	
	/**
     * 新增报警类型
     * 
     * @param alarmType 报警类型信息
     * @return 结果
     */
	public int insertAlarmType(AlarmType alarmType);
	
	/**
     * 修改报警类型
     * 
     * @param alarmType 报警类型信息
     * @return 结果
     */
	public int updateAlarmType(AlarmType alarmType);
		
	/**
     * 删除报警类型信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAlarmTypeByIds(String ids);
	
}

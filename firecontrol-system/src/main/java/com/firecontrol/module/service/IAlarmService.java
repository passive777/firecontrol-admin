package com.firecontrol.module.service;

import com.firecontrol.module.domain.Alarm;
import java.util.List;

/**
 * 报警 服务层
 * 
 * @author Fire
 * @date 2019-12-24
 */
public interface IAlarmService 
{
	/**
     * 查询报警信息
     * 
     * @param id 报警ID
     * @return 报警信息
     */
	public Alarm selectAlarmById(Integer id);
	
	/**
     * 查询报警列表
     * 
     * @param alarm 报警信息
     * @return 报警集合
     */
	public List<Alarm> selectAlarmList(Alarm alarm);
	
	/**
     * 新增报警
     * 
     * @param alarm 报警信息
     * @return 结果
     */
	public int insertAlarm(Alarm alarm);
	
	/**
     * 修改报警
     * 
     * @param alarm 报警信息
     * @return 结果
     */
	public int updateAlarm(Alarm alarm);
		
	/**
     * 删除报警信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAlarmByIds(String ids);
	
}

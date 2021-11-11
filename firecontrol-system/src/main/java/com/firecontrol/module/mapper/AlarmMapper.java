package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.Alarm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报警 数据层
 * 
 * @author Fire
 * @date 2019-11-24
 */
public interface AlarmMapper 
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
     * 删除报警
     * 
     * @param id 报警ID
     * @return 结果
     */
	public int deleteAlarmById(Integer id);
	
	/**
     * 批量删除报警
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAlarmByIds(String[] ids);

	/**
	 * IMEI查询设备状态信息
	 * @param imei
	 * @return
	 */
	Alarm queryAlarmByImei(@Param("imei") String imei);

	/**
	 * 统计设备报警状态
	 * @return
	 */
	List<Alarm> statisticsOfEquipmentWarnStatus();
}
package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.AlarmDispose;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

import java.util.List;

/**
 * 报警处理 数据层
 *
 * @author Fire
 * @date 2019-11-24
 */
public interface AlarmDisposeMapper
{
	/**
	 * 查询报警处理信息
	 *
	 * @param id 报警处理ID
	 * @return 报警处理信息
	 */
	public AlarmDispose selectAlarmDisposeById(Long id);

	/**
	 * 查询报警处理列表
	 *
	 * @param alarmDispose 报警处理信息
	 * @return 报警处理集合
	 */
	public List<AlarmDispose> selectAlarmDisposeList(AlarmDispose alarmDispose);

	/**
	 * 新增报警处理
	 *
	 * @param alarmDispose 报警处理信息
	 * @return 结果
	 */
	public int insertAlarmDispose(AlarmDispose alarmDispose);

	/**
	 * 修改报警处理
	 *
	 * @param alarmDispose 报警处理信息
	 * @return 结果
	 */
	public int updateAlarmDispose(AlarmDispose alarmDispose);

	/**
	 * 删除报警处理
	 *
	 * @param id 报警处理ID
	 * @return 结果
	 */
	public int deleteAlarmDisposeById(Long id);

	/**
	 * 批量删除报警处理
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteAlarmDisposeByIds(String[] ids);

	/**
	 * 查询指定IMEI号设备的报警处理列表
	 */
	List<AlarmDispose> selectAlarmDisposeListByImei(String imei);

	/**
	 * 修改报警处理 -- 微信
	 *
	 * @param params 报警处理信息
	 * @return 结果
	 */
	int updateAlarmDisposeWx( Map<String,Object> params);

	/**
	 * 查询指定IMEI号设备的报警处理列表
	 */
	List<AlarmDispose> queryAlarmDisposeListByImeis(@Param("array") List<String> imeis, @Param("status") String status);

    int alarmByEachDay(@Param("day") String day, @Param("deptId") Long deptId);

	/**
	 * 查询未处理的警报信息
	 * @return
	 */
	List<AlarmDispose> selectNotResolveWarn();
}
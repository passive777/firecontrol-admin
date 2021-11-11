package com.firecontrol.module.service;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.domain.AlarmDispose;
import java.util.Map;
import java.util.List;

/**
 * 报警处理 服务层
 *
 * @author Fire
 * @date 2019-12-24
 */
public interface IAlarmDisposeService
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
	 * 删除报警处理信息
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteAlarmDisposeByIds(String ids);

	/**
	 * 查询指定IMEI号设备的报警处理列表
	 */
	public List<AlarmDispose> selectAlarmDisposeListByImei(String imei);

	/**
	 * 报警处理信息列表 --- 待处理的
	 * @param params
	 * @return
	 * 作者 ： spring
	 */
	R queryAlarmDispose(Map<String, Object> params);

	/**
	 * 修改报警处理 -- 微信
	 *
	 * @param params 报警处理信息
	 * @return 结果
	 */
	R updateAlarmDisposeWx(Map<String, Object> params);

    int alarmByEachDay(String day, Long deptId);
}
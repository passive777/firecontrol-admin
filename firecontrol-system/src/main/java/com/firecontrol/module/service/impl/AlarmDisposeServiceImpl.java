package com.firecontrol.module.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.service.IGroupService;
import com.firecontrol.module.service.IInstallService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.AlarmDisposeMapper;
import com.firecontrol.module.domain.AlarmDispose;
import com.firecontrol.module.service.IAlarmDisposeService;
import com.firecontrol.common.core.text.Convert;

/**
 * 报警处理 服务层实现
 *
 * @author Fire
 * @date 2019-12-24
 */
@Service
public class AlarmDisposeServiceImpl implements IAlarmDisposeService
{
	@Autowired
	private AlarmDisposeMapper alarmDisposeMapper;

	@Autowired
	private IInstallService iInstallService;

	@Autowired
	private IGroupService iGroupService;

	/**
	 * 查询报警处理信息
	 *
	 * @param id 报警处理ID
	 * @return 报警处理信息
	 */
	@Override
	public AlarmDispose selectAlarmDisposeById(Long id)
	{
		return alarmDisposeMapper.selectAlarmDisposeById(id);
	}

	/**
	 * 查询报警处理列表
	 *
	 * @param alarmDispose 报警处理信息
	 * @return 报警处理集合
	 */
	@Override
	public List<AlarmDispose> selectAlarmDisposeList(AlarmDispose alarmDispose)
	{
		return alarmDisposeMapper.selectAlarmDisposeList(alarmDispose);
	}

	/**
	 * 新增报警处理
	 *
	 * @param alarmDispose 报警处理信息
	 * @return 结果
	 */
	@Override
	public int insertAlarmDispose(AlarmDispose alarmDispose)
	{
		return alarmDisposeMapper.insertAlarmDispose(alarmDispose);
	}

	/**
	 * 修改报警处理
	 *
	 * @param alarmDispose 报警处理信息
	 * @return 结果
	 */
	@Override
	public int updateAlarmDispose(AlarmDispose alarmDispose)
	{
		return alarmDisposeMapper.updateAlarmDispose(alarmDispose);
	}

	/**
	 * 删除报警处理对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteAlarmDisposeByIds(String ids)
	{
		return alarmDisposeMapper.deleteAlarmDisposeByIds(Convert.toStrArray(ids));
	}

	/**
	 * 查询指定IMEI号设备的报警处理列表
	 */
	@Override
	public List<AlarmDispose> selectAlarmDisposeListByImei(String imei) {
		return alarmDisposeMapper.selectAlarmDisposeListByImei(imei);
	}

	//*******************  更新后的微信 api  **********************

	@Override
	public R queryAlarmDispose(Map<String, Object> params) {
		Page<?> objects = null;
		Integer start = 1;
		Integer size = 10;
		Integer deptId = (Integer) params.get("deptId");
		List<Integer> groupIds = iGroupService.selectGroupIdsByDeptId(Long.valueOf(deptId));
		List<String> imeis = iInstallService.queryImeiByGroupIds(groupIds);
		String status = (String)params.get("status");

		try{
			if("1".equals(status)) {
				start = (Integer) params.get("start");
				size = (Integer) params.get("size");
				objects = PageHelper.startPage(start, size);
			}
		}catch (Exception e){
			objects = PageHelper.startPage(1,10);
		}
		List<AlarmDispose> alarmDisposes = alarmDisposeMapper.queryAlarmDisposeListByImeis(imeis, status);
		R r = R.ok();
		if(objects != null) r.put("hasMore", objects.getTotal() > (size * start));
		return r.put("data",alarmDisposes);
	}

	@Override
	public R updateAlarmDisposeWx(Map<String, Object> params) {
		params.put("disposeTime", new Date());
		params.put("status", "1");
		int i = alarmDisposeMapper.updateAlarmDisposeWx(params);
		return R.ok().put("data",i);
	}

	@Override
	public int alarmByEachDay(String day, Long deptId) {
		return alarmDisposeMapper.alarmByEachDay(day, deptId);
	}

}
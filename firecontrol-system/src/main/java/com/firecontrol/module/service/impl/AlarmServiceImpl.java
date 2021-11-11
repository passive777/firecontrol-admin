package com.firecontrol.module.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.AlarmMapper;
import com.firecontrol.module.domain.Alarm;
import com.firecontrol.module.service.IAlarmService;
import com.firecontrol.common.core.text.Convert;

/**
 * 报警 服务层实现
 * 
 * @author Fire
 * @date 2019-12-24
 */
@Service
public class AlarmServiceImpl implements IAlarmService 
{
	@Autowired
	private AlarmMapper alarmMapper;

	/**
     * 查询报警信息
     * 
     * @param id 报警ID
     * @return 报警信息
     */
    @Override
	public Alarm selectAlarmById(Integer id)
	{
	    return alarmMapper.selectAlarmById(id);
	}
	
	/**
     * 查询报警列表
     * 
     * @param alarm 报警信息
     * @return 报警集合
     */
	@Override
	public List<Alarm> selectAlarmList(Alarm alarm)
	{
	    return alarmMapper.selectAlarmList(alarm);
	}
	
    /**
     * 新增报警
     * 
     * @param alarm 报警信息
     * @return 结果
     */
	@Override
	public int insertAlarm(Alarm alarm)
	{
	    return alarmMapper.insertAlarm(alarm);
	}
	
	/**
     * 修改报警
     * 
     * @param alarm 报警信息
     * @return 结果
     */
	@Override
	public int updateAlarm(Alarm alarm)
	{
	    return alarmMapper.updateAlarm(alarm);
	}

	/**
     * 删除报警对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAlarmByIds(String ids)
	{
		return alarmMapper.deleteAlarmByIds(Convert.toStrArray(ids));
	}
	
}

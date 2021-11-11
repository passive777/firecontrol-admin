package com.firecontrol.module.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.AlarmTypeMapper;
import com.firecontrol.module.domain.AlarmType;
import com.firecontrol.module.service.IAlarmTypeService;
import com.firecontrol.common.core.text.Convert;

/**
 * 报警类型 服务层实现
 * 
 * @author Fire
 * @date 2019-12-04
 */
@Service
public class AlarmTypeServiceImpl implements IAlarmTypeService 
{
	@Autowired
	private AlarmTypeMapper alarmTypeMapper;

	/**
     * 查询报警类型信息
     * 
     * @param id 报警类型ID
     * @return 报警类型信息
     */
    @Override
	public AlarmType selectAlarmTypeById(String id)
	{
	    return alarmTypeMapper.selectAlarmTypeById(id);
	}
	
	/**
     * 查询报警类型列表
     * 
     * @param alarmType 报警类型信息
     * @return 报警类型集合
     */
	@Override
	public List<AlarmType> selectAlarmTypeList(AlarmType alarmType)
	{
	    return alarmTypeMapper.selectAlarmTypeList(alarmType);
	}
	
    /**
     * 新增报警类型
     * 
     * @param alarmType 报警类型信息
     * @return 结果
     */
	@Override
	public int insertAlarmType(AlarmType alarmType)
	{
	    return alarmTypeMapper.insertAlarmType(alarmType);
	}
	
	/**
     * 修改报警类型
     * 
     * @param alarmType 报警类型信息
     * @return 结果
     */
	@Override
	public int updateAlarmType(AlarmType alarmType)
	{
	    return alarmTypeMapper.updateAlarmType(alarmType);
	}

	/**
     * 删除报警类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAlarmTypeByIds(String ids)
	{
		return alarmTypeMapper.deleteAlarmTypeByIds(Convert.toStrArray(ids));
	}
	
}

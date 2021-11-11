package com.firecontrol.module.service.impl;

import com.firecontrol.module.domain.Alarm;
import com.firecontrol.module.domain.AlarmDispose;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Install;
import com.firecontrol.module.mapper.*;
import com.firecontrol.module.service.IBigDataService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据可视化 服务层实现
 * 
 * @author Javan911
 * @date 2020-05-24
 */
@Service
public class BigDataServiceImpl implements IBigDataService
{
	@Autowired
	private BigDataMapper bigDataMapper;
	@Autowired
	private InstallMapper installMapper;
	@Autowired
	private EquipmentMapper equipmentMapper;
	@Autowired
	private AlarmMapper alarmMapper;
	@Autowired
	private AlarmDisposeMapper alarmDisposeMapper;

	/**
	 * 查询设备在线/离线数量
	 * @author Javan911
	 * @return
	 */
	@Override
	public String eqOnOff() {
		Map<String,Object> map = new HashMap<>();
		//统计厂商设备
		List<Equipment> equipment = equipmentMapper.statisticsOfManufacturerEquipment();
		//统计设备报警状态
		List<Alarm> alarms = alarmMapper.statisticsOfEquipmentWarnStatus();
		//预警列表
		List<AlarmDispose> alarmDisposes = alarmDisposeMapper.selectNotResolveWarn();
		//所有安装设备
		List<Install> installs = installMapper.selectInstallList(new Install());

		map.put("equment",equipment);
		map.put("alarms",alarms);
		map.put("alarmDisposes",alarmDisposes);
		map.put("installs",installs);

		String json = new Gson().toJson(map);
		return json;
	}

	/**
	 * 地图秒点
	 * @return
	 */
	@Override
	public String installs() {
		Map<String,Object> map = new HashMap<>();
		//所有安装设备
		List<Install> installs = installMapper.selectInstallList(new Install());

		map.put("installs",installs);

		String json = new Gson().toJson(map);
		return json;
	}
}

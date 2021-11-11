package com.firecontrol.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.firecontrol.module.domain.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.EquipmentTypeMapper;
import com.firecontrol.module.domain.EquipmentType;
import com.firecontrol.module.service.IEquipmentTypeService;
import com.firecontrol.common.core.text.Convert;

/**
 * 设备类型 服务层实现
 * 
 * @author Fire
 * @date 2019-11-04
 */
@Service
public class EquipmentTypeServiceImpl implements IEquipmentTypeService 
{
	@Autowired
	private EquipmentTypeMapper equipmentTypeMapper;

	/**
     * 查询设备类型信息
     * 
     * @param id 设备类型ID
     * @return 设备类型信息
     */
    @Override
	public EquipmentType selectEquipmentTypeById(Integer id)
	{
	    return equipmentTypeMapper.selectEquipmentTypeById(id);
	}
	
	/**
     * 查询设备类型列表
     * 
     * @param equipmentType 设备类型信息
     * @return 设备类型集合
     */
	@Override
	public List<EquipmentType> selectEquipmentTypeList(EquipmentType equipmentType)
	{
	    return equipmentTypeMapper.selectEquipmentTypeList(equipmentType);
	}
	
    /**
     * 新增设备类型
     * 
     * @param equipmentType 设备类型信息
     * @return 结果
     */
	@Override
	public int insertEquipmentType(EquipmentType equipmentType)
	{
	    return equipmentTypeMapper.insertEquipmentType(equipmentType);
	}
	
	/**
     * 修改设备类型
     * 
     * @param equipmentType 设备类型信息
     * @return 结果
     */
	@Override
	public int updateEquipmentType(EquipmentType equipmentType)
	{
	    return equipmentTypeMapper.updateEquipmentType(equipmentType);
	}

	/**
     * 删除设备类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEquipmentTypeByIds(String ids)
	{
		return equipmentTypeMapper.deleteEquipmentTypeByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<EquipmentType> queryEquipmentTypeAndEquipmentBydeptId(Integer deptId) {
		List<EquipmentType> list = equipmentTypeMapper.queryEquipmentTypeAndEquipmentBydeptId(deptId);
		if(list == null)list = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			EquipmentType type = list.get(i);
			List<Equipment> equipments = type.getEquipments();
			if(equipments != null){
				int zc = 0,bj = 0,dlzc = 0,dlpd = 0,dlgd = 0;
				for (int j = 0; j < equipments.size(); j++){
					Equipment equipment = equipments.get(j);
					if(equipment != null && equipment.getAlarm() != null){
						switch (equipment.getAlarm().getAlarmStatus()){
							case "2":{
								bj++;
								break;
							}
							case "5":{
								dlgd++;
								break;
							}
							case "8":{
								if(equipment.getAlarm().getBatteryLevel() >= 70 ){
									zc++;
									dlzc++;
								}else if(equipment.getAlarm().getBatteryLevel() >= 60 ){
									dlpd++;
								}else{
									dlgd++;
								}
								break;
							}
							default:{
								zc++;
								break;
							}
						}
					}
				}
				Map<String, Object> params = type.getParams();
				params.put("zc",zc);
				params.put("bj",bj);
				params.put("dlzc",dlzc);
				params.put("dlpd",dlpd);
				params.put("dlgd",dlgd);
			}
		}
		return list;
	}

}

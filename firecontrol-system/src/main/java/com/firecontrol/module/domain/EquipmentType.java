package com.firecontrol.module.domain;


import com.firecontrol.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 设备类型表 sys_equipment_type
 * 
 * @author Fire
 * @date 2019-11-04
 */
public class EquipmentType extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 设备类型编号 */
	private Integer id;
	/** 类型名称 */
	private String name;

	/**
	 * 设备
	 */
	private List<Equipment> equipments;

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

	@Override
	public String toString() {
		return "EquipmentType{" +
				"id=" + id +
				", name='" + name + '\'' +
				", equipments=" + equipments +
				'}';
	}

}

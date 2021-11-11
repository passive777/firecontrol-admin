package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.EquipmentType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备类型 数据层
 * 
 * @author Fire
 * @date 2019-11-04
 */
public interface EquipmentTypeMapper 
{
	/**
     * 查询设备类型信息
     * 
     * @param id 设备类型ID
     * @return 设备类型信息
     */
	public EquipmentType selectEquipmentTypeById(Integer id);
	
	/**
     * 查询设备类型列表
     * 
     * @param equipmentType 设备类型信息
     * @return 设备类型集合
     */
	public List<EquipmentType> selectEquipmentTypeList(EquipmentType equipmentType);
	
	/**
     * 新增设备类型
     * 
     * @param equipmentType 设备类型信息
     * @return 结果
     */
	public int insertEquipmentType(EquipmentType equipmentType);
	
	/**
     * 修改设备类型
     * 
     * @param equipmentType 设备类型信息
     * @return 结果
     */
	public int updateEquipmentType(EquipmentType equipmentType);
	
	/**
     * 删除设备类型
     * 
     * @param id 设备类型ID
     * @return 结果
     */
	public int deleteEquipmentTypeById(Integer id);
	
	/**
     * 批量删除设备类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEquipmentTypeByIds(String[] ids);

	/**
	 * 根据适用房部门id按设备类型查询设备
	 * @param deptId
	 * @return
	 */
	List<EquipmentType> queryEquipmentTypeAndEquipmentBydeptId(@Param("deptId") Integer deptId);
	
}
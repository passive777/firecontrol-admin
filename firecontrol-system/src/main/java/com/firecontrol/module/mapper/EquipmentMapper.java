package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 设备 数据层
 * 
 * @author Fire
 * @date 2019-11-04
 */
@Repository(value="EquipmentMapper")
public interface EquipmentMapper
{
	/**
     * 查询设备信息
     * 
     * @param id 设备ID
     * @return 设备信息
     */
	public Equipment selectEquipmentById(Long id);
	
	/**
     * 查询设备列表
     * 
     * @param equipment 设备信息
     * @return 设备集合
     */
	public List<Equipment> selectEquipmentList(Equipment equipment);
	
	/**
     * 新增设备
     * 
     * @param equipment 设备信息
     * @return 结果
     */
	public int insertEquipment(Equipment equipment);
	
	/**
     * 修改设备
     * 
     * @param equipment 设备信息
     * @return 结果
     */
	public int updateEquipment(Equipment equipment);
	
	/**
     * 删除设备
     * 
     * @param id 设备ID
     * @return 结果
     */
	public int deleteEquipmentById(Long id);
	
	/**
     * 批量删除设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEquipmentByIds(String[] ids);

	/**
	 * 查询设备列表根据管辖域
	 */
    List<Equipment> groupEqumentList(@Param("groupId") Integer id, @Param("equipment") Equipment equipment);

	/**
	 * 查询指定IMEI号的设备信息
	 * @param imei
	 * @return
	 */
    public Equipment selectEquipmentByImei(String imei);

    List<Equipment> forPlat(Group group);

	/**
	 * 微信小程序新增安装点时向对应imei设备置入deptId + status
	 * @param equipment 设备信息
	 * @return 结果
	 * @version 2019.12.29
	 */
	public int updateEquipmentDeptIdByImei(Equipment equipment);

	/**
	 * 统计厂商设备
	 * @return
	 */
	List<Equipment>  statisticsOfManufacturerEquipment();
}
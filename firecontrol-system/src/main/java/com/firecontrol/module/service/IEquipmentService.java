package com.firecontrol.module.service;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.domain.Equipment;
import java.util.Map;

import com.firecontrol.module.domain.Group;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 设备 服务层
 * 
 * @author Fire
 * @date 2019-12-04
 */
public interface IEquipmentService 
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
     * 删除设备信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEquipmentByIds(String ids);

	/**
	 * 查询设备列表根据管辖域
	 */
    List<Equipment> groupEqumentList(Integer id, Equipment equipment);

	/**
	 * 查询指定IMEI号的设备安装信息
	 * @param imei 设备IMEI号
	 * @return 设备安装信息
	 */
	public Equipment selectEquipmentByImei(String imei);

	/**
	 *批量导入设备
	 * @date 2019-12-01 20:52:23
	 **/
    List<Equipment> importEquipment(MultipartFile equipmentList, boolean updateSupport, Long deptId);

	/**
	 *
	 * 查询指定使用方 的所有设备 分页查询
	 * @param params
	 * @return
	 * 作者：spring
	 */
	R queryEquipmentByDeptId(Map<String, Object> params);

	List<Equipment> forPlat(Group group);

	/**
	 * 微信小程序新增安装点时向对应imei设备置入deptId + status
	 * @param equipment 设备信息
	 * @return 结果
	 * @version 2019.12.29
	 */
	public int updateEquipmentDeptIdByImei(Equipment equipment);
}

package com.firecontrol.module.service;

import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Group;
import java.util.List;
import java.util.Map;

/**
 * 管辖域 服务层
 * 
 * @author Fire
 * @date 2019-12-08
 */
public interface IGroupService 
{
	/**
     * 查询管辖域信息
     * 
     * @param id 管辖域ID
     * @return 管辖域信息
     */
	public Group selectGroupById(Integer id);
	
	/**
     * 查询管辖域列表
     * 
     * @param group 管辖域信息
     * @return 管辖域集合
     */
	public List<Group> selectGroupList(Group group);
	
	/**
     * 新增管辖域
     * 
     * @param group 管辖域信息
     * @return 结果
     */
	public int insertGroup(Group group);
	
	/**
     * 修改管辖域
     * 
     * @param group 管辖域信息
     * @return 结果
     */
	public int updateGroup(Group group);
		
	/**
     * 删除管辖域信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGroupByIds(String ids);

	/**
	 * 获取层级管辖域
	 */
	public List<Group> selectTierGroupList();

	/**
	 * 查询父管辖域下的所有子管辖域
	 */
	public List<Group> selectGroupListByParent(Integer parentId);

	/**
	 * 保存管辖域责任人
	 * @param userId
	 * @return
	 */
    boolean saveDuty(Long userId, Integer groupId);

	/**
	 * 保存管辖域管辖员
	 * @param userId
	 * @return
	 */
	boolean savePatrol(Long userId, Integer groupId);

	/**
	 * 移除管辖域责任人
	 * @param userId
	 * @return
	 */
	boolean removeDuty(Long userId, Integer groupId);

    /**
     * 移除管辖域管辖员
     * @param userId
     * @return
     */
    boolean removePatrol(Long userId, Integer groupId);

	public List<Group> listByDept(Group group);

    /**
     * 根据管辖域编号查询管辖域下所有设备信息及烟感信息
     * @date 2019-12-21 12:35:54
     **/
	public Group selectEquipmentInfoList(String groupId);

	/**
	 * 查询子管辖域
	 */
    List<Group> childGroupList(Group group);

	/**
	 * 根据管辖域id查询当前管辖信息，及其子管辖域
	 * @param params
	 * @return Map<String,Object>
	 * @author spring
	 */
	Map<String,Object> queryGroupByIdOfChilds(Map<String,Object> params);

	/**
	 * 获取使用方部门下的所有管辖域id
	 * @return Long deptId
	 * @author spring
	 */
	List<Integer> selectGroupIdsByDeptId(Long deptId);

	/**
	 * 删除指定编号的管辖域
	 * @param id 管辖域编号
	 * @return 结果
	 */
	int deleteGroupById(Integer id);

    List<Equipment> alarmEquipment(Group group);
}

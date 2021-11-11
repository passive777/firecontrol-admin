package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Group;
import java.util.List;

/**
 * 管辖域 数据层
 *
 * @author Fire
 * @date 2019-11-08
 */
public interface GroupMapper
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
	 * 删除管辖域
	 *
	 * @param id 管辖域ID
	 * @return 结果
	 */
	public int deleteGroupById(Integer id);

	/**
	 * 批量删除管辖域
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteGroupByIds(String[] ids);

	/**
	 * 查询第一级管辖域
	 * @return
	 */
	List<Group> selectFirstGroupList();

	/**
	 * 查询父管辖域下的所有子管辖域
	 * @param parentId
	 * @return
	 */
	List<Group> selectGroupListByParent(Integer parentId);

	/**
	 * 根据部门查询管辖域列表
	 */
    List<Group> listByDept(Group group);

    Group selectEquipmentInfoList(String groupId);

	/**
	 * 查询子管辖域
	 */
    List<Group> childGroupList(Group group);

    List<Equipment> alarmEquipment(Group group);
}
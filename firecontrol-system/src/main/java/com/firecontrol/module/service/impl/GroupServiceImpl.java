package com.firecontrol.module.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firecontrol.module.domain.*;
import com.firecontrol.module.service.*;
import com.firecontrol.system.domain.SysUser;
import com.firecontrol.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.GroupMapper;
import com.firecontrol.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管辖域 服务层实现
 *
 * @author Fire
 * @date 2020-02-08
 */
@Service
public class GroupServiceImpl implements IGroupService
{
	@Autowired
	private GroupMapper groupMapper;
	@Autowired
	private IGroupDutyService groupDutyService;
	@Autowired
	private ISysUserService userService;
	@Autowired
	private IGroupPatrolService groupPatrolService;
	@Autowired
	private IInstallService installService;
	@Autowired
	private IGroupLinkmanService groupLinkmanService;

	/**
	 * 查询管辖域信息
	 *
	 * @param id 管辖域ID
	 * @return 管辖域信息
	 */
	@Override
	public Group selectGroupById(Integer id)
	{
		return groupMapper.selectGroupById(id);
	}

	/**
	 * 查询管辖域列表
	 *
	 * @param group 管辖域信息
	 * @return 管辖域集合
	 */
	@Override
	public List<Group> selectGroupList(Group group)
	{
		return groupMapper.selectGroupList(group);
	}

	/**
	 * 新增管辖域
	 *
	 * @param group 管辖域信息
	 * @return 结果
	 */
	@Override
	public int insertGroup(Group group)
	{
		int i = groupMapper.insertGroup(group);

		//为新管辖员添加报警联系人记录
		GroupLinkman groupLinkman = new GroupLinkman();
		groupLinkman.setLinkman("[]");
		groupLinkman.setDeptId(String.valueOf(group.getDeptId()));
		groupLinkman.setGroupId(String.valueOf(group.getId()));
		groupLinkmanService.insertGroupLinkman(groupLinkman);
		return i;
	}

	/**
	 * 修改管辖域
	 *
	 * @param group 管辖域信息
	 * @return 结果
	 */
	@Override
	public int updateGroup(Group group)
	{
		return groupMapper.updateGroup(group);
	}

	/**
	 * 删除管辖域对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteGroupByIds(String ids)
	{
		System.out.println("批量");
		return groupMapper.deleteGroupByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据层级关系查询管辖域列表
	 */
	@Override
	public List<Group> selectTierGroupList() {
		List<Group> firstList = groupMapper.selectFirstGroupList();
		for (int i = 0; i < firstList.size(); i++) {
			List<Group> secondList = selectGroupListByParent(firstList.get(i).getId());
			if (secondList != null) {
				firstList.get(i).setChildGroupList(secondList);
			}
		}
		return firstList;
	}

	@Override
	public List<Group> selectGroupListByParent(Integer parentId) {
		return groupMapper.selectGroupListByParent(parentId);
	}

	/**
	 * 保存管辖域责任人
	 * @param userId
	 * @return
	 */
	@Override
	public boolean saveDuty(Long userId, Integer groupId) {
		//保存管辖域-责任
		GroupDuty groupDuty = new GroupDuty();
		groupDuty.setGroupId(groupId);
		groupDuty.setUserId(Integer.parseInt(userId.toString()));
		groupDutyService.insertGroupDuty(groupDuty);
		//修改用户管辖域号
		SysUser user = new SysUser();
		user.setUserId(userId);
		user.setGroupId(groupId);
		userService.update(user);
		return true;
	}

	/**
	 * 保存管辖域管辖员
	 * @param userId
	 * @return
	 */
	@Override
	public boolean savePatrol(Long userId, Integer groupId) {
		// 保存管辖员关联关系到管辖域-管辖员表
		GroupPatrol groupPatrol = new GroupPatrol();
		groupPatrol.setGroupId(groupId);
		groupPatrol.setUserId(Integer.parseInt(userId.toString()));
		groupPatrolService.insertGroupPatrol(groupPatrol);
		// 更新管辖域-用户关联关系到用户表
		SysUser user = new SysUser();
		user.setGroupId(groupId);
		user.setUserId(userId);
		userService.update(user);
		return true;
	}

	/**
	 * 移除管辖域责任人
	 * @param userId
	 * @return
	 */
	@Override
	public boolean removeDuty(Long userId, Integer groupId) {
		//移除管辖域-责任
		groupDutyService.deleteGroupDutyByUser(userId);
		//移除用户管辖域号
		userService.updateGroup(userId);
		return true;
	}

	/**
	 * 移除管辖域管辖员
	 * @param userId
	 * @return
	 */
	@Override
	public boolean removePatrol(Long userId, Integer groupId) {
		// 1：解除管辖域-管辖员关联管辖
		groupPatrolService.deleteGroupPatrolByUser(userId);
		// 2：移除用户管辖域号
		userService.updateGroup(userId);
		return true;
	}

	/**
	 * 根据部门查询管辖域列表
	 */
	@Override
	public List<Group> listByDept(Group group) {
		return groupMapper.listByDept(group);
	}

	/**
	 * 根据管辖域编号查询管辖域下所有设备信息及烟感信息
	 * @date 2020-02-21 12:36:38
	 **/
	@Override
	public Group selectEquipmentInfoList(String groupId) {
		return groupMapper.selectEquipmentInfoList(groupId);
	}

	/**
	 * 查询子管辖域
	 */
	@Override
	public List<Group> childGroupList(Group group) {
		List<Group> list =  groupMapper.childGroupList(group);
		//查询管辖域的责任人和管辖员
		for(Group g : list){
			List<SysUser> dutyList = groupDutyService.selectGroupDutyListByGroupId(g.getId());
			List<SysUser> patrolList = groupPatrolService.selectGroupPatrolListByGroupId(g.getId());
			g.setDutyList(dutyList);
			g.setPatrolList(patrolList);
		}
		return list;
	}

	@Override
	public Map<String, Object> queryGroupByIdOfChilds(Map<String, Object> params) {
		Map<String,Object> result = new HashMap<String, Object>();
		Integer id = Integer.valueOf((String)params.get("id"));
		Group group = new Group();
		group.setId(id);
		List<Group> groups = groupMapper.childGroupList(group);
		group = groupMapper.selectGroupById(id);
		result.put("code",200);
		result.put("group", group);
		result.put("groups", groups);
		return result;
	}

	@Override
	public List<Integer> selectGroupIdsByDeptId(Long deptId) {
		Group g = new Group();
		g.setDeptId(Math.toIntExact(deptId));
		List<Group> groups = listByDept(g);
		List<Integer> groupIds = new ArrayList<Integer>();
		for(Group group : groups){
			groupIds.add(group.getId());
		}
		return groupIds;
	}

	/**
	 * 删除管辖域及其关联的信息
	 */
	@Override
	@Transactional
	public int deleteGroupById(Integer id) {
		System.out.println("单删除");
		// 1：删除当前管辖域
		int i = groupMapper.deleteGroupById(id);
		// 2：遍历删除与管辖域关联的所有二级管辖域
		List<Group> groupList =  groupMapper.selectGroupListByParent(id);
		if (groupList != null && !"".equals(groupList)) {
			for (Group group : groupList) {
				groupMapper.deleteGroupById(group.getId());
			}
		}
		// 3：删除与管辖域关联的安装点信息
		installService.deleteInstallByGroupId(id);
		// 4：移除与管辖域关联的责任人信息
		groupDutyService.deleteGroupDutyByGroupId(id);
		// 5：移除与管辖域关联的管辖员信息
		groupPatrolService.deleteGroupPatrolByGroupId(id);
		// 6：移除与管辖域关联的报警联系人信息
		// 6.1：查询该管辖域下的报警联系人信息
		List<Linkman> linkmanList = groupLinkmanService.selectByGroup(id);
		if (linkmanList != null && !"".equals(linkmanList)) {
			groupLinkmanService.deleteGroupLinkmanByGroupId(id);
		}
		return i;
	}

	@Override
	public List<Equipment> alarmEquipment(Group group) {
		return groupMapper.alarmEquipment(group);
	}

}

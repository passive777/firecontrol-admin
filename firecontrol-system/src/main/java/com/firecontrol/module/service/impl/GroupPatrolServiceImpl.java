package com.firecontrol.module.service.impl;

import java.util.List;
import java.util.Map;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.service.IGroupService;
import com.firecontrol.system.domain.SysUser;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.GroupPatrolMapper;
import com.firecontrol.module.domain.GroupPatrol;
import com.firecontrol.module.service.IGroupPatrolService;
import com.firecontrol.common.core.text.Convert;

/**
 * 管辖域管辖员 服务层实现
 * 
 * @author Fire
 * @date 2019-12-04
 */
@Service
public class GroupPatrolServiceImpl implements IGroupPatrolService 
{
	@Autowired
	private GroupPatrolMapper groupPatrolMapper;

	@Autowired
	private IGroupService iGroupService;

	/**
	 * 根据管辖域号查询
	 * @param id
	 * @return
	 */
	@Override
	public List<GroupPatrol> selectGroupPatrolByGroupId(Integer id) {
		return groupPatrolMapper.selectGroupPatrolByGroupId(id);
	}

	/**
     * 查询管辖域管辖员信息
     * 
     * @param id 管辖域管辖员ID
     * @return 管辖域管辖员信息
     */
    @Override
	public GroupPatrol selectGroupPatrolById(Integer id)
	{
	    return groupPatrolMapper.selectGroupPatrolById(id);
	}
	
	/**
     * 查询管辖域管辖员列表
     * 
     * @param groupPatrol 管辖域管辖员信息
     * @return 管辖域管辖员集合
     */
	@Override
	public List<GroupPatrol> selectGroupPatrolList(GroupPatrol groupPatrol)
	{
	    return groupPatrolMapper.selectGroupPatrolList(groupPatrol);
	}
	
    /**
     * 新增管辖域管辖员
     * 
     * @param groupPatrol 管辖域管辖员信息
     * @return 结果
     */
	@Override
	public int insertGroupPatrol(GroupPatrol groupPatrol)
	{
	    return groupPatrolMapper.insertGroupPatrol(groupPatrol);
	}
	
	/**
     * 修改管辖域管辖员
     * 
     * @param groupPatrol 管辖域管辖员信息
     * @return 结果
     */
	@Override
	public int updateGroupPatrol(GroupPatrol groupPatrol)
	{
	    return groupPatrolMapper.updateGroupPatrol(groupPatrol);
	}

	/**
     * 删除管辖域管辖员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGroupPatrolByIds(String ids)
	{
		return groupPatrolMapper.deleteGroupPatrolByIds(Convert.toStrArray(ids));
	}

	/**
	 * 移除管辖域管辖员
	 * @param userId
	 */
	@Override
	public void deleteGroupPatrolByUser(Long userId) {
		groupPatrolMapper.deleteGroupPatrolByUserId(userId);
	}


	/**
	 * 查询指定管辖域的管辖员列表
	 * @date 2019-12-18 11:35:56
	 **/
	@Override
	public List<SysUser> selectGroupPatrolListByGroupId(Integer groupId) {
		return groupPatrolMapper.selectGroupPatrolListByGroupId(groupId);
	}

	@Override
	public R queryPatrolByDeptId(Map<String, Object> params) {
		List<Integer> groupIds = iGroupService.selectGroupIdsByDeptId(Long.valueOf((Integer) params.get("deptId")));
		Integer[] array = groupIds.toArray(new Integer[groupIds.size()]);
		Page<?> objects = null;
		Integer start = 1;
		Integer size = 10;
		try{
			start = (Integer)params.get("start");
			size = (Integer) params.get("size");
			objects = PageHelper.startPage(start, size);
		}catch (Exception e){
			objects = PageHelper.startPage(1,10);
		}
		List<SysUser> sysUsers = groupPatrolMapper.queryGroupPatrolListByGroupIds(array);
		return R.ok().put("data", sysUsers).put("hasMore", objects.getTotal() > (start * size) );
	}

	/**
	 * 移除与管辖域关联的管辖员信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
	@Override
	public int deleteGroupPatrolByGroupId(Integer groupId) {
		return groupPatrolMapper.deleteGroupPatrolByGroupId(groupId);
	}

}

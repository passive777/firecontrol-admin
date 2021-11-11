package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.GroupPatrol;
import com.firecontrol.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管辖域管辖员 数据层
 * 
 * @author Fire
 * @date 2019-11-016
 */
public interface GroupPatrolMapper 
{

	/**
	 * 移除管辖域管辖员
	 * @param userId
	 */
	public void deleteGroupPatrolByUserId(Long userId);

	/**
	 * 根据管辖域号查询
	 * @param id
	 * @return
	 */
	public List<GroupPatrol> selectGroupPatrolByGroupId(Integer id);

	/**
     * 查询管辖域管辖员信息
     * 
     * @param id 管辖域管辖员ID
     * @return 管辖域管辖员信息
     */
	public GroupPatrol selectGroupPatrolById(Integer id);
	
	/**
     * 查询管辖域管辖员列表
     * 
     * @param groupPatrol 管辖域管辖员信息
     * @return 管辖域管辖员集合
     */
	public List<GroupPatrol> selectGroupPatrolList(GroupPatrol groupPatrol);
	
	/**
     * 新增管辖域管辖员
     * 
     * @param groupPatrol 管辖域管辖员信息
     * @return 结果
     */
	public int insertGroupPatrol(GroupPatrol groupPatrol);
	
	/**
     * 修改管辖域管辖员
     * 
     * @param groupPatrol 管辖域管辖员信息
     * @return 结果
     */
	public int updateGroupPatrol(GroupPatrol groupPatrol);
	
	/**
     * 删除管辖域管辖员
     * 
     * @param id 管辖域管辖员ID
     * @return 结果
     */
	public int deleteGroupPatrolById(Integer id);
	
	/**
     * 批量删除管辖域管辖员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGroupPatrolByIds(String[] ids);

	/**
	 * 查询指定管辖域的管辖员列表
	 * @date 2019-11-18 11:36:38
	 **/
	public List<SysUser> selectGroupPatrolListByGroupId(Integer groupId);

	/**
	 * 使用方所有管辖人员 分页数据
	 * @author spring
	 **/
	public List<SysUser> queryGroupPatrolListByGroupIds(@Param("array") Integer[] array);

	/**
	 * 移除与管辖域关联的管辖员信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
    int deleteGroupPatrolByGroupId(Integer groupId);
}
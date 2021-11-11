package com.firecontrol.module.service.impl;

import java.util.List;

import com.firecontrol.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.GroupDutyMapper;
import com.firecontrol.module.domain.GroupDuty;
import com.firecontrol.module.service.IGroupDutyService;
import com.firecontrol.common.core.text.Convert;

/**
 * 管辖域责任人 服务层实现
 * 
 * @author Fire
 * @date 2019-12-04
 */
@Service
public class GroupDutyServiceImpl implements IGroupDutyService 
{
	@Autowired
	private GroupDutyMapper groupDutyMapper;

	/**
     * 查询管辖域责任人信息
     * 
     * @param id 管辖域责任人ID
     * @return 管辖域责任人信息
     */
    @Override
	public GroupDuty selectGroupDutyById(Integer id)
	{
	    return groupDutyMapper.selectGroupDutyById(id);
	}
	
	/**
     * 查询管辖域责任人列表
     * 
     * @param groupDuty 管辖域责任人信息
     * @return 管辖域责任人集合
     */
	@Override
	public List<GroupDuty> selectGroupDutyList(GroupDuty groupDuty)
	{
	    return groupDutyMapper.selectGroupDutyList(groupDuty);
	}
	
    /**
     * 新增管辖域责任人
     * 
     * @param groupDuty 管辖域责任人信息
     * @return 结果
     */
	@Override
	public int insertGroupDuty(GroupDuty groupDuty)
	{
	    return groupDutyMapper.insertGroupDuty(groupDuty);
	}
	
	/**
     * 修改管辖域责任人
     * 
     * @param groupDuty 管辖域责任人信息
     * @return 结果
     */
	@Override
	public int updateGroupDuty(GroupDuty groupDuty)
	{
	    return groupDutyMapper.updateGroupDuty(groupDuty);
	}

	/**
     * 删除管辖域责任人对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGroupDutyByIds(String ids)
	{
		return groupDutyMapper.deleteGroupDutyByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据管辖域号查询
	 * @param id
	 * @return
	 */
	@Override
	public List<GroupDuty> selectGroupDutyByGroupId(Integer id) {
		return groupDutyMapper.selectGroupDutyByGroupId(id);
	}

	/**
	 * 移除管辖域责任人
	 * @param userId
	 */
	@Override
	public void deleteGroupDutyByUser(Long userId) {
		groupDutyMapper.deleteGroupDutyByUser(userId);
	}

	/**
	 * 查询指定管辖域责任人列表
	 * @date 2019-12-18 11:54:39
	 **/
	@Override
	public List<SysUser> selectGroupDutyListByGroupId(Integer groupId) {
		return groupDutyMapper.selectGroupDutyListByGroupId(groupId);
	}

	/**
	 * 移除与管辖域关联的责任人信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
	@Override
	public int deleteGroupDutyByGroupId(Integer groupId) {
		return groupDutyMapper.deleteGroupDutyByGroupId(groupId);
	}

}

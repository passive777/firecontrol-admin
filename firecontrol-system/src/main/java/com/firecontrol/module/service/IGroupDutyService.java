package com.firecontrol.module.service;

import com.firecontrol.module.domain.GroupDuty;
import com.firecontrol.system.domain.SysUser;

import java.util.List;

/**
 * 管辖域责任人 服务层
 * 
 * @author Fire
 * @date 2019-12-04
 */
public interface IGroupDutyService 
{
	/**
     * 查询管辖域责任人信息
     * 
     * @param id 管辖域责任人ID
     * @return 管辖域责任人信息
     */
	public GroupDuty selectGroupDutyById(Integer id);
	
	/**
     * 查询管辖域责任人列表
     * 
     * @param groupDuty 管辖域责任人信息
     * @return 管辖域责任人集合
     */
	public List<GroupDuty> selectGroupDutyList(GroupDuty groupDuty);
	
	/**
     * 新增管辖域责任人
     * 
     * @param groupDuty 管辖域责任人信息
     * @return 结果
     */
	public int insertGroupDuty(GroupDuty groupDuty);
	
	/**
     * 修改管辖域责任人
     * 
     * @param groupDuty 管辖域责任人信息
     * @return 结果
     */
	public int updateGroupDuty(GroupDuty groupDuty);
		
	/**
     * 删除管辖域责任人信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGroupDutyByIds(String ids);

	/**
	 * 根据管辖域号查询
	 * @param id
	 * @return
	 */
    List<GroupDuty> selectGroupDutyByGroupId(Integer id);

	/**
	 * 移除管辖域责任人
	 * @param userId
	 */
	void deleteGroupDutyByUser(Long userId);

	/**
	 * 查询指定管辖域的责任人列表
	 * @date 2019-12-18 11:54:11
	 **/
	List<SysUser> selectGroupDutyListByGroupId(Integer groupId);

	/**
	 * 移除与管辖域关联的责任人信息
	 * @param id 管辖域编号
	 * @return 结果
	 */
    int deleteGroupDutyByGroupId(Integer groupId);
}

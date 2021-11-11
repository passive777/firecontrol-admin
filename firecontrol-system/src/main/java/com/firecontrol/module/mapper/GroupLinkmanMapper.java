package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.GroupLinkman;

import java.util.List;

/**
 * 设备报警联系人 数据层
 * 
 * @author Fire
 * @date 2019-11-19
 */
public interface GroupLinkmanMapper 
{
	/**
     * 查询设备报警联系人信息
     * 
     * @param id 设备报警联系人ID
     * @return 设备报警联系人信息
     */
	public GroupLinkman selectGroupLinkmanById(Integer id);
	
	/**
     * 查询设备报警联系人列表
     * 
     * @param groupLinkman 设备报警联系人信息
     * @return 设备报警联系人集合
     */
	public List<GroupLinkman> selectGroupLinkmanList(GroupLinkman groupLinkman);
	
	/**
     * 新增设备报警联系人
     * 
     * @param groupLinkman 设备报警联系人信息
     * @return 结果
     */
	public int insertGroupLinkman(GroupLinkman groupLinkman);

	/**
     * 修改设备报警联系人
     * 
     * @param groupLinkman 设备报警联系人信息
     * @return 结果
     */
	public int updateGroupLinkman(GroupLinkman groupLinkman);
	
	/**
     * 删除设备报警联系人
     * 
     * @param id 设备报警联系人ID
     * @return 结果
     */
	public int deleteGroupLinkmanById(Integer id);
	
	/**
     * 批量删除设备报警联系人
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGroupLinkmanByIds(String[] ids);

	/**
	 * 获取管辖域报警联系人
	 */
    List<GroupLinkman> selectByGroup(Integer id);

	/**
	 * 移除与管辖域关联的报警联系人信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
    int deleteGroupLinkmanByGroupId(Integer groupId);
}
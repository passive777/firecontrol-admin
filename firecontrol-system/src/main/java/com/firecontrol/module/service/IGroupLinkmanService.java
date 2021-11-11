package com.firecontrol.module.service;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.domain.GroupLinkman;
import com.firecontrol.module.domain.Linkman;

import java.util.List;
import java.util.Map;

/**
 * 设备报警联系人 服务层
 * 
 * @author Fire
 * @date 2019-12-19
 */
public interface IGroupLinkmanService 
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
     * @return 结果
     */
	public int insertGroupLinkman(GroupLinkman groupLinkman);

	public int insertLinkman(Linkman linkman);

	public int update(GroupLinkman groupLinkman);

	/**
     * 修改设备报警联系人
     *
     * @return 结果
     */
	public int updateGroupLinkman(Linkman linkman);
		
	/**
     * 删除设备报警联系人信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGroupLinkmanByIds(String ids);

	/**
 	* 获取管辖域报警联系人
 	*/
    List<Linkman> selectByGroup(Integer id);

	/**
	 * 获取管辖域备用报警联系人
	 */
	List<Linkman> selectBackupByGroup(Integer id);

	/**
	 * 查询指定 使用方下的所有报警联系人列表
	 * @param params 设备报警联系人信息
	 * @return 设备报警联系人集合
	 * 作者：spring
	 */
	R queryGroupLinkmanListByDeptId(Map<String, Object> params);

	/**
	 * 移除与管辖域关联的报警联系人信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
    int deleteGroupLinkmanByGroupId(Integer groupId);
}

package com.firecontrol.module.service.impl;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.domain.Linkman;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.GroupLinkmanMapper;
import com.firecontrol.module.domain.GroupLinkman;
import com.firecontrol.module.service.IGroupLinkmanService;
import com.firecontrol.common.core.text.Convert;
import java.util.Map;

/**
 * 设备报警联系人 服务层实现
 * 
 * @author Fire
 * @date 2019-12-19
 */
@Service
public class GroupLinkmanServiceImpl implements IGroupLinkmanService 
{
	@Autowired
	private GroupLinkmanMapper groupLinkmanMapper;
	/**
     * 查询设备报警联系人信息
     * 
     * @param id 设备报警联系人ID
     * @return 设备报警联系人信息
     */
    @Override
	public GroupLinkman selectGroupLinkmanById(Integer id)
	{
		System.out.println("id"+id);
	    return groupLinkmanMapper.selectGroupLinkmanById(id);
	}
	
	/**
     * 查询设备报警联系人列表
     * 
     * @param groupLinkman 设备报警联系人信息
     * @return 设备报警联系人集合
     */
	@Override
	public List<GroupLinkman> selectGroupLinkmanList(GroupLinkman groupLinkman)
	{
	    return groupLinkmanMapper.selectGroupLinkmanList(groupLinkman);
	}
	//（往右放看长T） A左 3上 A右 3下 A右 面逆 A左 面顺
	//（往左放看短T） A右 面逆 A左 面顺 A左 3上 A右 3下
    /**
     * 新增设备报警联系人
     * 
     * @param groupLinkman 设备报警联系人信息
     * @return 结果
     */
	@Override
	public int insertGroupLinkman(GroupLinkman groupLinkman)
	{
	    return groupLinkmanMapper.insertGroupLinkman(groupLinkman);
	}

	@Override
	public int insertLinkman(Linkman linkman)
	{
		GroupLinkman groupLinkman = selectGroupLinkmanById(linkman.getId());
		String linkmanStr = groupLinkman.getLinkman();
		//json字符串转对象集合
		List<Linkman> linkmanList = JSONArray.parseArray(linkmanStr, Linkman.class);
		linkmanList.add(linkman);
		groupLinkman.setLinkman(JSON.toJSONString(linkmanList));
		return groupLinkmanMapper.updateGroupLinkman(groupLinkman);
	}

	@Override
	public int update(GroupLinkman groupLinkman){
		return groupLinkmanMapper.updateGroupLinkman(groupLinkman);
	}

	/**
     * 修改设备报警联系人
     *
     * @return 结果阻止
     */
	@Override
	public int updateGroupLinkman(Linkman linkman)
	{
		GroupLinkman groupLinkman = selectGroupLinkmanById(linkman.getId());
		String linkmanStr = groupLinkman.getLinkman();
		//json字符串转对象集合
		List<Linkman> linkmanList = JSONArray.parseArray(linkmanStr, Linkman.class);
		int count = linkmanList.size();
		while(count > 0){
			count--;
			if(linkmanList.get(count).getName().equals(linkman.getName())){
				linkmanList.get(count).setTelephone(linkman.getTelephone());
			}
		}
		groupLinkman.setLinkman(JSON.toJSONString(linkmanList));
		return groupLinkmanMapper.updateGroupLinkman(groupLinkman);
	}

	/**
     * 删除设备报警联系人对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGroupLinkmanByIds(String ids)
	{
		return groupLinkmanMapper.deleteGroupLinkmanByIds(Convert.toStrArray(ids));
	}

	/**
	 * 获取管辖域报警联系人
	 */
	@Override
	public List<Linkman> selectByGroup(Integer id) {
		List<GroupLinkman> list = groupLinkmanMapper.selectByGroup(id);
		if(list.size() != 0){
			GroupLinkman groupLinkman = list.get(0);
			//json字符串转对象集合
			List<Linkman> linkmanList = JSONArray.parseArray(groupLinkman.getLinkman(), Linkman.class);
			int count = linkmanList.size();
			while(count > 0){
				count--;
				if(linkmanList.get(count).getType() == 1){
					linkmanList.get(count).setId(groupLinkman.getId());
				}else {
					linkmanList.remove(linkmanList.get(count));
				}
			}
			return linkmanList;
		}else{
			return null;
		}
	}

	/**
	 * 获取管辖域备用报警联系人
	 */
	@Override
	public List<Linkman> selectBackupByGroup(Integer id) {
		List<GroupLinkman> list = groupLinkmanMapper.selectByGroup(id);
		if(list.size() != 0){
			GroupLinkman groupLinkman = list.get(0);
			List<Linkman> linkmanList = JSONArray.parseArray(groupLinkman.getLinkman(), Linkman.class);
			int count = linkmanList.size();
			while(count > 0){
				count--;
				if(linkmanList.get(count).getType() == 0){
					linkmanList.get(count).setId(groupLinkman.getId());
				}else {
					linkmanList.remove(linkmanList.get(count));
				}
			}
			return linkmanList;
		}else{
			return null;
		}
	}

	@Override
	public R queryGroupLinkmanListByDeptId(Map<String, Object> params) {
		Gson gson = new Gson();
		String json = gson.toJson(params);
		GroupLinkman groupLinkman = gson.fromJson(json, GroupLinkman.class);
		List<GroupLinkman> linkmens = groupLinkmanMapper.selectGroupLinkmanList(groupLinkman);
		return R.ok().put("data", linkmens);
	}

	/**
	 * 移除与管辖域关联的报警联系人信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
	@Override
	public int deleteGroupLinkmanByGroupId(Integer groupId) {
		return groupLinkmanMapper.deleteGroupLinkmanByGroupId(groupId);
	}

}

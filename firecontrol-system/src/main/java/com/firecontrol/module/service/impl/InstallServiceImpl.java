package com.firecontrol.module.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.firecontrol.module.domain.GroupLinkman;
import com.firecontrol.module.domain.Linkman;
import com.firecontrol.module.service.IGroupLinkmanService;
import com.firecontrol.module.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.InstallMapper;
import com.firecontrol.module.domain.Install;
import com.firecontrol.module.service.IInstallService;
import com.firecontrol.common.core.text.Convert;

/**
 * 设备安装 服务层实现
 * 
 * @author Fire
 * @date 2019-12-04
 */
@Service
public class InstallServiceImpl implements IInstallService
{

	@Autowired
	private IGroupService iGroupService;

	@Autowired
	private InstallMapper installMapper;

	@Autowired
	private IGroupLinkmanService grouplinkmanService;

	/**
     * 查询设备安装信息
     * 
     * @param id 设备安装ID
     * @return 设备安装信息
     */
    @Override
	public Install selectInstallById(Integer id)
	{
	    return installMapper.selectInstallById(id);
	}
	
	/**
     * 查询设备安装列表
     * 
     * @param install 设备安装信息
     * @return 设备安装集合
     */
	@Override
	public List<Install> selectInstallList(Install install)
	{
	    return installMapper.selectInstallList(install);
	}
	
    /**
     * 新增设备安装
     * 
     * @param install 设备安装信息
     * @return 结果
     */
	@Override
	public int insertInstall(Install install)
	{
	    return installMapper.insertInstall(install);
	}
	
	/**
     * 修改设备安装
     * 
     * @param install 设备安装信息
     * @return 结果
     */
	@Override
	public int updateInstall(Install install)
	{
	    return installMapper.updateInstall(install);
	}

	@Override
	public int updateInstallByMap(Map<String, Object> map) {
		return installMapper.updateInstallByMap(map);
	}

	/**
     * 删除设备安装对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteInstallByIds(String ids)
	{
		return installMapper.deleteInstallByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据imei移除设备与安装点的绑定
	 * @date 2019-12-18 13:32:03
	 **/
	@Override
	public int deleteInstallByImei(String imei) {
		return installMapper.deleteInstallByImei(imei);
	}

	/**
	 * 删除报警联系人
	 * @param linkman
	 * @return
	 */
	@Override
	public int removeLinkman(Linkman linkman) {
		GroupLinkman groupLinkman = grouplinkmanService.selectGroupLinkmanById(linkman.getId());
		String linkmanStr = groupLinkman.getLinkman();
		//json字符串转对象集合
		List<Linkman> linkmanList = JSONArray.parseArray(linkmanStr, Linkman.class);
		int count = linkmanList.size();
		while(count > 0){
			count--;
			if(linkmanList.get(count).getTelephone().equals(linkman.getTelephone())){
				linkmanList.remove(linkmanList.get(count));
			}
		}
		groupLinkman.setLinkman(JSON.toJSONString(linkmanList));
		return grouplinkmanService.update(groupLinkman);
	}

	@Override
	public Map<String, Object> deleteInstallById(Long id) {
		int i = deleteInstallByImei(String.valueOf(id));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", i);
		return  map;
	}

	@Override
	public Map<String, Object> queryInstallsByGroupId(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer deptId = (Integer) map.get("deptId");
		String keywords = (String) map.get("keywords");
		List<Integer> groupIds = iGroupService.selectGroupIdsByDeptId(Long.valueOf(deptId));
		Integer[] array = groupIds.toArray(new Integer[groupIds.size()]);
		List<Install> installs = installMapper.queryInstallByIds(array, keywords);
		result.put("installs", installs);
		return result;
	}

	@Override
	public List<String> queryImeiByGroupIds(List<Integer> array) {
		return installMapper.queryImeiByGroupIds(array);
	}

	/**
	 * 删除指定管辖域编号的安装点信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
	@Override
	public int deleteInstallByGroupId(Integer groupId) {
		return installMapper.deleteInstallByGroupId(groupId);
	}

	/**
	 * 修改设备安装 + id ==> 微信端
	 *
	 * @param install 设备安装信息
	 * @return 结果
	 */
	@Override
	public int updateInstallById(Install install)
	{
		return installMapper.updateInstallById(install);
	}

}

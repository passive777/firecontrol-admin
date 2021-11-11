package com.firecontrol.module.service;

import com.firecontrol.module.domain.Install;
import com.firecontrol.module.domain.Linkman;
import java.util.List;
import java.util.Map;

/**
 * 设备安装 服务层
 * 
 * @author Fire
 * @date 2019-12-04
 */
public interface IInstallService 
{


	/**
     * 查询设备安装信息
     * 
     * @param id 设备安装ID
     * @return 设备安装信息
     */
	public Install selectInstallById(Integer id);
	
	/**
     * 查询设备安装列表
     * 
     * @param install 设备安装信息
     * @return 设备安装集合
     */
	public List<Install> selectInstallList(Install install);
	
	/**
     * 新增设备安装
     * 
     * @param install 设备安装信息
     * @return 结果
     */
	public int insertInstall(Install install);
	
	/**
     * 修改设备安装
     * 
     * @param install 设备安装信息
     * @return 结果
     */
	public int updateInstall(Install install);

	/**
	 * 修改设备安装, 微信
	 * @param map 设备安装信息
	 * @return 结果
	 */
	int updateInstallByMap(Map<String ,Object> map);
		
	/**
     * 删除设备安装信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteInstallByIds(String ids);

	/**
	 * 根据imei解绑设备与安装点的绑定
	 * @date 2019-12-18 13:31:34
	 **/
    int deleteInstallByImei(String imei);

	/**
	 * 删除报警联系人
	 * @param linkman
	 * @return
	 */
    int removeLinkman(Linkman linkman);

	/**
	 *  删除指定id的安装点
	 * @param id
	 * @return
	 * @author spring
	 */
	Map<String,Object> deleteInstallById(Long id);

	/**
	 *  微信小程序查询 使用方所有的设备信息
	 * @param map
	 * @return
	 * @author spring
	 */
	Map<String,Object> queryInstallsByGroupId(Map<String,Object>  map);

	/**
	 * 查询使用方 下的所有使用中的设备imei
	 * @param array 设备安装信息
	 * @return 设备安装集合
	 */
	List<String> queryImeiByGroupIds(List<Integer> array);

	/**
	 * 删除指定管辖域编号的安装点信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
	int deleteInstallByGroupId(Integer groupId);

	/**
	 * 修改设备安装 + id ==> 微信端
	 *
	 * @param install 设备安装信息
	 * @return 结果
	 */
	int updateInstallById(Install install);

}

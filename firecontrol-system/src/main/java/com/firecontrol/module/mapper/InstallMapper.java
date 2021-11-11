package com.firecontrol.module.mapper;

import com.firecontrol.module.domain.Install;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

/**
 * 设备安装 数据层
 * 
 * @author Fire
 * @date 2019-11-04
 */
public interface InstallMapper 
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
     * 删除设备安装
     * 
     * @param id 设备安装ID
     * @return 结果
     */
	public int deleteInstallById(Integer id);
	
	/**
     * 批量删除设备安装
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteInstallByIds(String[] ids);

    /**
     * 根据imei移除设备与安装点的绑定
     * @date 2019-11-18 13:32:51
     **/
    public int deleteInstallByImei(String imei);

	/**
	 * 批量查找设备安装信息
	 *
	 * @param groupIds 需要批量查找的数据 管辖域编码：groupIds
	 * @return 结果
	 */
	List<Install> queryInstallByIds(@Param("groupIds") Integer[] groupIds, @Param("keywords") String keywords);

	/**
	 * 查询使用方 下的所有使用中的设备imei
	 * @param array 设备安装信息
	 * @return 设备安装集合
	 */
	List<String> queryImeiByGroupIds(@Param("array") List<Integer> array);

	/**
	 * 删除指定管辖域编号的安装点信息
	 * @param groupId 管辖域编号
	 * @return 结果
	 */
	int deleteInstallByGroupId(Integer groupId);


	/**
	 * 修改设备安装 + id
	 *
	 * @param install 设备安装信息
	 * @return 结果
	 */
	int updateInstallById(Install install);

	/**
	 * 修改设备安装, 微信
	 * @param map 设备安装信息
	 * @return 结果
	 */
	int updateInstallByMap(Map<String ,Object> map);

}
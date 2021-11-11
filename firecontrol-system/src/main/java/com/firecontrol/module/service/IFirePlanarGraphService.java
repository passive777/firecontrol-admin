package com.firecontrol.module.service;

import com.firecontrol.module.domain.FirePlanarGraph;
import java.util.List;

/**
 * 文件 服务层
 * 
 * @author Fire
 * @date 2020-06-18
 */
public interface IFirePlanarGraphService 
{
	/**
     * 查询文件信息
     * 
     * @param fpgId 文件ID
     * @return 文件信息
     */
	public FirePlanarGraph selectFirePlanarGraphById(Integer fpgId);
	
	/**
     * 查询文件列表
     * 
     * @param firePlanarGraph 文件信息
     * @return 文件集合
     */
	public List<FirePlanarGraph> selectFirePlanarGraphList(FirePlanarGraph firePlanarGraph);
	
	/**
     * 新增文件
     * 
     * @param firePlanarGraph 文件信息
     * @return 结果
     */
	public int insertFirePlanarGraph(FirePlanarGraph firePlanarGraph);
	
	/**
     * 修改文件
     * 
     * @param firePlanarGraph 文件信息
     * @return 结果
     */
	public int updateFirePlanarGraph(FirePlanarGraph firePlanarGraph);
		
	/**
     * 删除文件信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFirePlanarGraphByIds(String ids);

	/**
	 * 批量插入
	 * @param firePlanarGraph
	 * @return
	 */
	int insertFirePlanarGraph(FirePlanarGraph[] firePlanarGraph);

	/**
	 * 通过组id查找对应的标记
	 * @param groupId
	 * @return
	 */
	List<FirePlanarGraph> getTaggingByGroupId(Integer groupId);

	/**
	 * 通过设备好删除标记
	 * @param id
	 * @return
	 */
	int deleteFirePlanarGraphByEqId(Integer id);

	/**
	 * 通过集群id删除标记
	 * @param id
	 * @return
	 */
	int deleteFirePlanarGraphByGroupId(Integer id);
}

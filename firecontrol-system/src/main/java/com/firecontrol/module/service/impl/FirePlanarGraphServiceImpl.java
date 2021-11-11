package com.firecontrol.module.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.FirePlanarGraphMapper;
import com.firecontrol.module.domain.FirePlanarGraph;
import com.firecontrol.module.service.IFirePlanarGraphService;
import com.firecontrol.common.core.text.Convert;

/**
 * 文件 服务层实现
 * 
 * @author Fire
 * @date 2020-06-18
 */
@Service
public class FirePlanarGraphServiceImpl implements IFirePlanarGraphService 
{
	@Autowired
	private FirePlanarGraphMapper firePlanarGraphMapper;

	/**
     * 查询文件信息
     * 
     * @param fpgId 文件ID
     * @return 文件信息
     */
    @Override
	public FirePlanarGraph selectFirePlanarGraphById(Integer fpgId)
	{
	    return firePlanarGraphMapper.selectFirePlanarGraphById(fpgId);
	}
	
	/**
     * 查询文件列表
     * 
     * @param firePlanarGraph 文件信息
     * @return 文件集合
     */
	@Override
	public List<FirePlanarGraph> selectFirePlanarGraphList(FirePlanarGraph firePlanarGraph)
	{
	    return firePlanarGraphMapper.selectFirePlanarGraphList(firePlanarGraph);
	}
	
    /**
     * 新增文件
     * 
     * @param firePlanarGraph 文件信息
     * @return 结果
     */
	@Override
	public int insertFirePlanarGraph(FirePlanarGraph firePlanarGraph)
	{
	    return firePlanarGraphMapper.insertFirePlanarGraph(firePlanarGraph);
	}
	
	/**
     * 修改文件
     * 
     * @param firePlanarGraph 文件信息
     * @return 结果
     */
	@Override
	public int updateFirePlanarGraph(FirePlanarGraph firePlanarGraph)
	{
	    return firePlanarGraphMapper.updateFirePlanarGraph(firePlanarGraph);
	}

	/**
     * 删除文件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteFirePlanarGraphByIds(String ids)
	{
		return firePlanarGraphMapper.deleteFirePlanarGraphByIds(Convert.toStrArray(ids));
	}

	/**
	 * 批量插入
	 * @param firePlanarGraph
	 * @return
	 */
	@Override
	public int insertFirePlanarGraph(FirePlanarGraph[] firePlanarGraph) {
		Arrays.stream(firePlanarGraph).forEach(v->{
			insertFirePlanarGraph(v);
		});
		return 0;
	}

	/**
	 * 获取集群下所有的设备标记
	 * @param groupId
	 * @return
	 */
	@Override
	public List<FirePlanarGraph> getTaggingByGroupId(Integer groupId) {
		return firePlanarGraphMapper.getTaggingByGroupId(groupId);
	}

	/**
	 * 通过设备id删除标记
	 * @param id
	 * @return
	 */
	@Override
	public int deleteFirePlanarGraphByEqId(Integer id) {

	    return firePlanarGraphMapper.deleteFirePlanarGraphByEqId(id);
	}

	/**
	 * 通过集群id删除标记
	 * @param id
	 * @return
	 */
	@Override
	public int deleteFirePlanarGraphByGroupId(Integer id) {
		return firePlanarGraphMapper.deleteFirePlanarGraphByGroupId(id);
	}

}

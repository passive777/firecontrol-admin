package com.firecontrol.web.controller.module;

import java.util.List;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.framework.util.JsonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.firecontrol.common.annotation.Log;
import com.firecontrol.common.enums.BusinessType;
import com.firecontrol.module.domain.FirePlanarGraph;
import com.firecontrol.module.service.IFirePlanarGraphService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 文件 信息操作处理
 * 
 * @author Fire
 * @date 2020-06-18
 */
@Controller
@RequestMapping("/module/firePlanarGraph")
public class FirePlanarGraphController extends BaseController
{
    private String prefix = "module/firePlanarGraph";
	
	@Autowired
	private IFirePlanarGraphService firePlanarGraphService;
	
	@RequiresPermissions("module:firePlanarGraph:view")
	@GetMapping()
	public String firePlanarGraph()
	{
	    return prefix + "/firePlanarGraph";
	}
	
	/**
	 * 查询文件列表
	 */
	@RequiresPermissions("module:firePlanarGraph:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(FirePlanarGraph firePlanarGraph)
	{
		startPage();
        List<FirePlanarGraph> list = firePlanarGraphService.selectFirePlanarGraphList(firePlanarGraph);
		return getDataTable(list);
	}
	@RequestMapping("/tagging")
	@ResponseBody
	public R getTaggingByGroupId(@RequestParam Integer groupId){

		List<FirePlanarGraph> firePlanarGraphList = firePlanarGraphService.getTaggingByGroupId(groupId);

		return R.ok().put("data",firePlanarGraphList);
	}
	
	/**
	 * 导出文件列表
	 */
	@RequiresPermissions("module:firePlanarGraph:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FirePlanarGraph firePlanarGraph)
    {
    	List<FirePlanarGraph> list = firePlanarGraphService.selectFirePlanarGraphList(firePlanarGraph);
        ExcelUtil<FirePlanarGraph> util = new ExcelUtil<FirePlanarGraph>(FirePlanarGraph.class);
        return util.exportExcel(list, "firePlanarGraph");
    }
	
	/**
	 * 新增文件
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存文件
	 */
//	@RequiresPermissions("module:firePlanarGraph:add")
	@Log(title = "文件", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestParam String firePlanarGraph) throws Exception {
		FirePlanarGraph fpg = JsonUtil.convertJsonStringToObject(firePlanarGraph, FirePlanarGraph.class);
		System.out.println(fpg);
		return toAjax(firePlanarGraphService.insertFirePlanarGraph(fpg));
	}

	/**
	 * 修改文件
	 */
	@GetMapping("/edit/{fpgId}")
	public String edit(@PathVariable("fpgId") Integer fpgId, ModelMap mmap)
	{
		FirePlanarGraph firePlanarGraph = firePlanarGraphService.selectFirePlanarGraphById(fpgId);
		mmap.put("firePlanarGraph", firePlanarGraph);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存文件
	 */
	@RequiresPermissions("module:firePlanarGraph:edit")
	@Log(title = "文件", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(FirePlanarGraph firePlanarGraph)
	{		
		return toAjax(firePlanarGraphService.updateFirePlanarGraph(firePlanarGraph));
	}
	
	/**
	 * 删除文件
	 */
//	@RequiresPermissions("module:firePlanarGraph:remove")
	@Log(title = "文件", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(firePlanarGraphService.deleteFirePlanarGraphByIds(ids));
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Log(title = "文件", businessType = BusinessType.DELETE)
	@PostMapping( "/delete")
	@ResponseBody
	public AjaxResult delete(Integer id)
	{
		return toAjax(firePlanarGraphService.deleteFirePlanarGraphByEqId(id));
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Log(title = "文件", businessType = BusinessType.DELETE)
	@PostMapping( "/delete_by_group_id")
	@ResponseBody
	public AjaxResult deleteByGroupId(Integer id)
	{
		return toAjax(firePlanarGraphService.deleteFirePlanarGraphByGroupId(id));
	}
}

package com.firecontrol.web.controller.module;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.firecontrol.common.annotation.Log;
import com.firecontrol.common.enums.BusinessType;
import com.firecontrol.module.domain.GroupPatrol;
import com.firecontrol.module.service.IGroupPatrolService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 管辖域管辖员 信息操作处理
 * 
 * @author Fire
 * @date 2020-02-23
 */
@Controller
@RequestMapping("/module/groupPatrol")
public class GroupPatrolController extends BaseController
{
    private String prefix = "module/groupPatrol";
	
	@Autowired
	private IGroupPatrolService groupPatrolService;
	
	@RequiresPermissions("module:groupPatrol:view")
	@GetMapping()
	public String groupPatrol()
	{
	    return prefix + "/groupPatrol";
	}
	
	/**
	 * 查询管辖域管辖员列表
	 */
	@RequiresPermissions("module:groupPatrol:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GroupPatrol groupPatrol)
	{
		startPage();
        List<GroupPatrol> list = groupPatrolService.selectGroupPatrolList(groupPatrol);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出管辖域管辖员列表
	 */
	@RequiresPermissions("module:groupPatrol:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroupPatrol groupPatrol)
    {
    	List<GroupPatrol> list = groupPatrolService.selectGroupPatrolList(groupPatrol);
        ExcelUtil<GroupPatrol> util = new ExcelUtil<GroupPatrol>(GroupPatrol.class);
        return util.exportExcel(list, "groupPatrol");
    }
	
	/**
	 * 新增管辖域管辖员
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存管辖域管辖员
	 */
	@RequiresPermissions("module:groupPatrol:add")
	@Log(title = "管辖域管辖员", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GroupPatrol groupPatrol)
	{		
		return toAjax(groupPatrolService.insertGroupPatrol(groupPatrol));
	}

	/**
	 * 修改管辖域管辖员
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		GroupPatrol groupPatrol = groupPatrolService.selectGroupPatrolById(id);
		mmap.put("groupPatrol", groupPatrol);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存管辖域管辖员
	 */
	@RequiresPermissions("module:groupPatrol:edit")
	@Log(title = "管辖域管辖员", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GroupPatrol groupPatrol)
	{		
		return toAjax(groupPatrolService.updateGroupPatrol(groupPatrol));
	}
	
	/**
	 * 删除管辖域管辖员
	 */
	@RequiresPermissions("module:groupPatrol:remove")
	@Log(title = "管辖域管辖员", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(groupPatrolService.deleteGroupPatrolByIds(ids));
	}
	
}

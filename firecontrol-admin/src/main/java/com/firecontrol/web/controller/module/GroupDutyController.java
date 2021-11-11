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
import com.firecontrol.module.domain.GroupDuty;
import com.firecontrol.module.service.IGroupDutyService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 管辖域责任人 信息操作处理
 * 
 * @author Fire
 * @date 2020-01-23
 */
@Controller
@RequestMapping("/module/groupDuty")
public class GroupDutyController extends BaseController
{
    private String prefix = "module/groupDuty";
	
	@Autowired
	private IGroupDutyService groupDutyService;
	
	@RequiresPermissions("module:groupDuty:view")
	@GetMapping()
	public String groupDuty()
	{
	    return prefix + "/groupDuty";
	}
	
	/**
	 * 查询管辖域责任人列表
	 */
	@RequiresPermissions("module:groupDuty:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GroupDuty groupDuty)
	{
		startPage();
        List<GroupDuty> list = groupDutyService.selectGroupDutyList(groupDuty);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出管辖域责任人列表
	 */
	@RequiresPermissions("module:groupDuty:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroupDuty groupDuty)
    {
    	List<GroupDuty> list = groupDutyService.selectGroupDutyList(groupDuty);
        ExcelUtil<GroupDuty> util = new ExcelUtil<GroupDuty>(GroupDuty.class);
        return util.exportExcel(list, "groupDuty");
    }
	
	/**
	 * 新增管辖域责任人
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存管辖域责任人
	 */
	@RequiresPermissions("module:groupDuty:add")
	@Log(title = "管辖域责任人", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GroupDuty groupDuty)
	{		
		return toAjax(groupDutyService.insertGroupDuty(groupDuty));
	}

	/**
	 * 修改管辖域责任人
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		GroupDuty groupDuty = groupDutyService.selectGroupDutyById(id);
		mmap.put("groupDuty", groupDuty);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存管辖域责任人
	 */
	@RequiresPermissions("module:groupDuty:edit")
	@Log(title = "管辖域责任人", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GroupDuty groupDuty)
	{		
		return toAjax(groupDutyService.updateGroupDuty(groupDuty));
	}
	
	/**
	 * 删除管辖域责任人
	 */
	@RequiresPermissions("module:groupDuty:remove")
	@Log(title = "管辖域责任人", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(groupDutyService.deleteGroupDutyByIds(ids));
	}
	
}

package com.firecontrol.web.controller.module;

import java.util.List;

import com.firecontrol.module.domain.Linkman;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.firecontrol.common.annotation.Log;
import com.firecontrol.common.enums.BusinessType;
import com.firecontrol.module.domain.GroupLinkman;
import com.firecontrol.module.service.IGroupLinkmanService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 设备报警联系人 信息操作处理
 * 
 * @author Fire
 * @date 2020-01-23
 */
@Controller
@RequestMapping("/module/groupLinkman")
public class GroupLinkmanController extends BaseController
{
    private String prefix = "module/groupLinkman";
	
	@Autowired
	private IGroupLinkmanService groupLinkmanService;
	
	@RequiresPermissions("module:equipmentLinkman:view")
	@GetMapping()
	public String groupLinkman()
	{
	    return prefix + "/groupLinkman";
	}
	
	/**
	 * 查询设备报警联系人列表
	 */
	@RequiresPermissions("module:equipmentLinkman:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GroupLinkman groupLinkman)
	{
		startPage();
        List<GroupLinkman> list = groupLinkmanService.selectGroupLinkmanList(groupLinkman);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出设备报警联系人列表
	 */
	@RequiresPermissions("module:equipmentLinkman:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroupLinkman groupLinkman)
    {
    	List<GroupLinkman> list = groupLinkmanService.selectGroupLinkmanList(groupLinkman);
        ExcelUtil<GroupLinkman> util = new ExcelUtil<GroupLinkman>(GroupLinkman.class);
        return util.exportExcel(list, "groupLinkman");
    }
	
	/**
	 * 新增设备报警联系人
	 */
	@GetMapping("/add")
	public String add(Linkman linkman, ModelMap map)
	{
		map.put("linkman", linkman);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存设备报警联系人
	 */
	@RequiresPermissions("module:equipmentLinkman:add")
	@Log(title = "设备报警联系人", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Linkman linkman)
	{
		return toAjax(groupLinkmanService.insertLinkman(linkman));
	}

	/**
	 * 修改设备报警联系人
	 */
	@GetMapping("/edit")
	public String edit(Linkman linkman, ModelMap mmap)
	{
		mmap.put("linkman", linkman);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存设备报警联系人
	 */
	@RequiresPermissions("module:equipmentLinkman:edit")
	@Log(title = "设备报警联系人", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Linkman linkman)
	{
		return toAjax(groupLinkmanService.updateGroupLinkman(linkman));
	}
	
	/**
	 * 删除设备报警联系人
	 */
	@RequiresPermissions("module:equipmentLinkman:remove")
	@Log(title = "设备报警联系人", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(groupLinkmanService.deleteGroupLinkmanByIds(ids));
	}
	
}

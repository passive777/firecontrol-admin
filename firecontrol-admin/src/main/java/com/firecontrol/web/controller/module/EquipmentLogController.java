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
import com.firecontrol.module.domain.EquipmentLog;
import com.firecontrol.module.service.IEquipmentLogService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 操作日志记录 信息操作处理
 * 
 * @author Fire
 * @date 2019-11-15
 */
@Controller
@RequestMapping("/module/equipmentLog")
public class EquipmentLogController extends BaseController
{
    private String prefix = "module/equipmentLog";
	
	@Autowired
	private IEquipmentLogService equipmentLogService;
	
	@RequiresPermissions("module:equipmentLog:view")
	@GetMapping()
	public String equipmentLog()
	{
	    return prefix + "/equipmentLog";
	}
	
	/**
	 * 查询操作日志记录列表
	 */
	@RequiresPermissions("module:equipmentLog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(EquipmentLog equipmentLog)
	{
		startPage();
        List<EquipmentLog> list = equipmentLogService.selectEquipmentLogList(equipmentLog);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出操作日志记录列表
	 */
	@RequiresPermissions("module:equipmentLog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EquipmentLog equipmentLog)
    {
    	List<EquipmentLog> list = equipmentLogService.selectEquipmentLogList(equipmentLog);
        ExcelUtil<EquipmentLog> util = new ExcelUtil<EquipmentLog>(EquipmentLog.class);
        return util.exportExcel(list, "equipmentLog");
    }
	
	/**
	 * 新增操作日志记录
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存操作日志记录
	 */
	@RequiresPermissions("module:equipmentLog:add")
	@Log(title = "操作日志记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(EquipmentLog equipmentLog)
	{		
		return toAjax(equipmentLogService.insertEquipmentLog(equipmentLog));
	}

	/**
	 * 修改操作日志记录
	 */
	@GetMapping("/edit/{operId}")
	public String edit(@PathVariable("operId") Long operId, ModelMap mmap)
	{
		EquipmentLog equipmentLog = equipmentLogService.selectEquipmentLogById(operId);
		mmap.put("equipmentLog", equipmentLog);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存操作日志记录
	 */
	@RequiresPermissions("module:equipmentLog:edit")
	@Log(title = "操作日志记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(EquipmentLog equipmentLog)
	{		
		return toAjax(equipmentLogService.updateEquipmentLog(equipmentLog));
	}
	
	/**
	 * 删除操作日志记录
	 */
	@RequiresPermissions("module:equipmentLog:remove")
	@Log(title = "操作日志记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(equipmentLogService.deleteEquipmentLogByIds(ids));
	}
	
}

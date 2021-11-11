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
import com.firecontrol.module.domain.AlarmType;
import com.firecontrol.module.service.IAlarmTypeService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 报警类型 信息操作处理
 * 
 * @author Fire
 * @date 2020-01-07
 */
@Controller
@RequestMapping("/module/alarmType")
public class AlarmTypeController extends BaseController
{
    private String prefix = "module/alarmType";
	
	@Autowired
	private IAlarmTypeService alarmTypeService;
	
	@RequiresPermissions("module:alarmType:view")
	@GetMapping()
	public String alarmType()
	{
	    return prefix + "/alarmType";
	}
	
	/**
	 * 查询报警类型列表
	 */
	@RequiresPermissions("module:alarmType:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AlarmType alarmType)
	{
		startPage();
        List<AlarmType> list = alarmTypeService.selectAlarmTypeList(alarmType);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出报警类型列表
	 */
	@RequiresPermissions("module:alarmType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AlarmType alarmType)
    {
    	List<AlarmType> list = alarmTypeService.selectAlarmTypeList(alarmType);
        ExcelUtil<AlarmType> util = new ExcelUtil<AlarmType>(AlarmType.class);
        return util.exportExcel(list, "alarmType");
    }
	
	/**
	 * 新增报警类型
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存报警类型
	 */
	@RequiresPermissions("module:alarmType:add")
	@Log(title = "报警类型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AlarmType alarmType)
	{		
		return toAjax(alarmTypeService.insertAlarmType(alarmType));
	}

	/**
	 * 修改报警类型
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		AlarmType alarmType = alarmTypeService.selectAlarmTypeById(id);
		mmap.put("alarmType", alarmType);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存报警类型
	 */
	@RequiresPermissions("module:alarmType:edit")
	@Log(title = "报警类型", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AlarmType alarmType)
	{		
		return toAjax(alarmTypeService.updateAlarmType(alarmType));
	}
	
	/**
	 * 删除报警类型
	 */
	@RequiresPermissions("module:alarmType:remove")
	@Log(title = "报警类型", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(alarmTypeService.deleteAlarmTypeByIds(ids));
	}
	
}

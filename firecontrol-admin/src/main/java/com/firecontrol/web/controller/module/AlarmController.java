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
import com.firecontrol.module.domain.Alarm;
import com.firecontrol.module.service.IAlarmService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 报警 信息操作处理
 *
 * @author Fire
 * @date 2019-11-07
 */
@Controller
@RequestMapping("/module/alarm")
public class  AlarmController extends BaseController
{
    private String prefix = "module/alarm";

	@Autowired
	private IAlarmService alarmService;

	@RequiresPermissions("module:alarm:view")
	@GetMapping()
	public String alarm()
	{
	    return prefix + "/alarm";
	}

	/**
	 * 查询报警列表
	 */
	@RequiresPermissions("module:alarm:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Alarm alarm)
	{
		startPage();
        List<Alarm> list = alarmService.selectAlarmList(alarm);
		return getDataTable(list);
	}


	/**
	 * 导出报警列表
	 */
	@RequiresPermissions("module:alarm:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Alarm alarm)
    {
    	List<Alarm> list = alarmService.selectAlarmList(alarm);
        ExcelUtil<Alarm> util = new ExcelUtil<Alarm>(Alarm.class);
        return util.exportExcel(list, "alarm");
    }

	/**
	 * 新增报警
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存报警
	 */
	@RequiresPermissions("module:alarm:add")
	@Log(title = "报警", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Alarm alarm)
	{
		return toAjax(alarmService.insertAlarm(alarm));
	}

	/**
	 * 修改报警
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Alarm alarm = alarmService.selectAlarmById(id);
		mmap.put("alarm", alarm);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存报警
	 */
	@RequiresPermissions("module:alarm:edit")
	@Log(title = "报警", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Alarm alarm)
	{
		return toAjax(alarmService.updateAlarm(alarm));
	}

	/**
	 * 删除报警
	 */
	@RequiresPermissions("module:alarm:remove")
	@Log(title = "报警", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(alarmService.deleteAlarmByIds(ids));
	}

}

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
import com.firecontrol.module.domain.AlarmDispose;
import com.firecontrol.module.service.IAlarmDisposeService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 报警处理 信息操作处理
 *
 * @author Fire
 * @date 2020-01-07
 */
@Controller
@RequestMapping("/module/alarmDispose")
public class AlarmDisposeController extends BaseController
{
	private String prefix = "module/alarmDispose";

	@Autowired
	private IAlarmDisposeService alarmDisposeService;

	@RequiresPermissions("module:alarmDispose:view")
	@GetMapping()
	public String alarmDispose()
	{
		return prefix + "/alarmDispose";
	}

	/**
	 * 查询报警处理列表
	 */
	@RequiresPermissions("module:alarmDispose:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AlarmDispose alarmDispose)
	{
		startPage();
		List<AlarmDispose> list = alarmDisposeService.selectAlarmDisposeList(alarmDispose);
		return getDataTable(list);
	}


	/**
	 * 导出报警处理列表
	 */
	@RequiresPermissions("module:alarmDispose:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(AlarmDispose alarmDispose)
	{
		List<AlarmDispose> list = alarmDisposeService.selectAlarmDisposeList(alarmDispose);
		ExcelUtil<AlarmDispose> util = new ExcelUtil<AlarmDispose>(AlarmDispose.class);
		return util.exportExcel(list, "alarmDispose");
	}

	/**
	 * 新增报警处理
	 */
	@GetMapping("/add")
	public String add()
	{
		return prefix + "/add";
	}

	/**
	 * 新增保存报警处理
	 */
	@RequiresPermissions("module:alarmDispose:add")
	@Log(title = "报警处理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AlarmDispose alarmDispose)
	{
		return toAjax(alarmDisposeService.insertAlarmDispose(alarmDispose));
	}

	/**
	 * 修改报警处理
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		AlarmDispose alarmDispose = alarmDisposeService.selectAlarmDisposeById(id);
		mmap.put("alarmDispose", alarmDispose);
		return prefix + "/edit";
	}

	/**
	 * 修改保存报警处理
	 */
	@RequiresPermissions("module:alarmDispose:edit")
	@Log(title = "报警处理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AlarmDispose alarmDispose)
	{
		return toAjax(alarmDisposeService.updateAlarmDispose(alarmDispose));
	}

	/**
	 * 删除报警处理
	 */
	@RequiresPermissions("module:alarmDispose:remove")
	@Log(title = "报警处理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(alarmDisposeService.deleteAlarmDisposeByIds(ids));
	}

	/**
	 * 查询指定IMEI号设备的报警处理列表
	 */
	@RequiresPermissions("module:alarmDispose:list")
	@PostMapping("/list/{imei}")
	@ResponseBody
	public TableDataInfo selectAlarmDisposeListByImei(String imei)
	{
		startPage();
		List<AlarmDispose> list = alarmDisposeService.selectAlarmDisposeListByImei(imei);
		return getDataTable(list);
	}

}
package com.firecontrol.web.controller.module;

import java.util.List;

import com.google.gson.Gson;
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
import com.firecontrol.module.domain.EquipmentType;
import com.firecontrol.module.service.IEquipmentTypeService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 设备类型 信息操作处理
 * 
 * @author Fire
 * @date 2020-01-15
 */
@Controller
@RequestMapping("/module/equipmentType")
public class EquipmentTypeController extends BaseController
{
    private String prefix = "module/equipmentType";
	
	@Autowired
	private IEquipmentTypeService equipmentTypeService;
	
	@RequiresPermissions("module:equipmentType:view")
	@GetMapping()
	public String equipmentType()
	{
	    return prefix + "/equipmentType";
	}

	/**
	 * 处理地图中获取设备的所有类型
	 * @date 2020-02-23 18:14:27
	 **/
	@ResponseBody
	@PostMapping("/equipmentTypeData")
	public String equipmentTypeData(){
		List<EquipmentType> equipmentTypeList = equipmentTypeService.selectEquipmentTypeList(new EquipmentType());
	    return new Gson().toJson(equipmentTypeList);
	}

	/**
	 * 查询设备类型列表
	 */
	@RequiresPermissions("module:equipmentType:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(EquipmentType equipmentType)
	{
		startPage();
        List<EquipmentType> list = equipmentTypeService.selectEquipmentTypeList(equipmentType);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出设备类型列表
	 */
	@RequiresPermissions("module:equipmentType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EquipmentType equipmentType)
    {
    	List<EquipmentType> list = equipmentTypeService.selectEquipmentTypeList(equipmentType);
        ExcelUtil<EquipmentType> util = new ExcelUtil<EquipmentType>(EquipmentType.class);
        return util.exportExcel(list, "equipmentType");
    }
	
	/**
	 * 新增设备类型
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存设备类型
	 */
	@RequiresPermissions("module:equipmentType:add")
	@Log(title = "设备类型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(EquipmentType equipmentType)
	{		
		return toAjax(equipmentTypeService.insertEquipmentType(equipmentType));
	}

	/**
	 * 修改设备类型
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		EquipmentType equipmentType = equipmentTypeService.selectEquipmentTypeById(id);
		mmap.put("equipmentType", equipmentType);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存设备类型
	 */
	@RequiresPermissions("module:equipmentType:edit")
	@Log(title = "设备类型", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(EquipmentType equipmentType)
	{		
		return toAjax(equipmentTypeService.updateEquipmentType(equipmentType));
	}
	
	/**
	 * 删除设备类型
	 */
	@RequiresPermissions("module:equipmentType:remove")
	@Log(title = "设备类型", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(equipmentTypeService.deleteEquipmentTypeByIds(ids));
	}
	
}

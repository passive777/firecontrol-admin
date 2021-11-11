package com.firecontrol.web.controller.module;

import java.util.ArrayList;
import java.util.List;

import com.firecontrol.framework.util.ShiroUtils;
import com.firecontrol.module.domain.Group;
import com.firecontrol.module.service.IEquipmentTypeService;
import com.firecontrol.module.service.IInstallService;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.firecontrol.common.annotation.Log;
import com.firecontrol.common.enums.BusinessType;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.service.IEquipmentService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备 信息操作处理
 * 
 * @author Fire
 * @date 2019-11-15
 */
@Controller
@RequestMapping("/module/equipment")
public class EquipmentController extends BaseController
{
    private String prefix = "module/equipment";
	
	@Autowired
	private IEquipmentService equipmentService;
	@Autowired
	private IInstallService installService;
	@Autowired
	private IEquipmentTypeService equipmentTypeService;

	@RequiresPermissions("module:equipment:list")
	@GetMapping()
	public String equipment()
	{
	    return prefix + "/equipment";
	}

	/**
	 * 地图 根据集群以及本单位查询设备情况
	 */
	@GetMapping("/forPlat")
	@ResponseBody
	public List<Equipment> forPlat(Group group, ModelMap model){
		if (Math.toIntExact(ShiroUtils.getSysUser().getDeptId()) != 100 ){
			group.setDeptId(Math.toIntExact(ShiroUtils.getSysUser().getDeptId()));
		}
		List<Equipment> list = equipmentService.forPlat(group);
		model.addAttribute("equipmentList", list);
		return list;
	}

	/**
	 * 查询设备列表根据管辖域
	 */
	@RequiresPermissions("module:equipment:list")
	@PostMapping("/groupEqumentList/{id}")
	@ResponseBody
	public TableDataInfo groupEqumentList(@PathVariable("id") Integer id,Equipment equipment)
	{
		startPage();
		List<Equipment> list = equipmentService.groupEqumentList(id,equipment);
		return getDataTable(list);
	}


	/**
	 * 查询设备列表
	 */
	@RequiresPermissions("module:equipment:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Equipment equipment)
	{
		startPage();
		if (Math.toIntExact(ShiroUtils.getSysUser().getDeptId()) != 100 ){
			equipment.setDeptId(Math.toIntExact(ShiroUtils.getSysUser().getDeptId()));
		}
        List<Equipment> list = equipmentService.selectEquipmentList(equipment);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出设备列表
	 */
	@RequiresPermissions("module:equipment:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Equipment equipment)
    {
    	List<Equipment> list = equipmentService.selectEquipmentList(equipment);
        ExcelUtil<Equipment> util = new ExcelUtil<Equipment>(Equipment.class);
        return util.exportExcel(list, "equipment");
    }
	
	/**
	 * 新增设备
	 */
	@GetMapping("/add")
	public String add(ModelMap map)
	{
		map.put("type", equipmentTypeService.selectEquipmentTypeList(null));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存设备
	 */
	@RequiresPermissions("module:equipment:add")
	@Log(title = "设备", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Equipment equipment)
	{
		int deptId = Math.toIntExact(ShiroUtils.getSysUser().getDeptId());
		equipment.setDeptId(deptId);
		equipment.setStatus(0);
		return toAjax(equipmentService.insertEquipment(equipment));
	}

	/**
	 * 修改设备
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Equipment equipment = equipmentService.selectEquipmentById(id);
		mmap.put("equipment", equipment);
	    return prefix + "/edit";
	}

	/**
	 * 修改设备安装点信息
	 *//*
	@GetMapping("/edit/{imei}")
	public String editInstall(@PathVariable("imei") String imei, ModelMap mmap)
	{
		Install install = installService.selectInstallByImei();
		mmap.put("install", install);
		System.out.println(install + "///");
		return prefix + "/edit";
	}*/
	
	/**
	 * 修改保存设备
	 */
	@RequiresPermissions("module:equipment:edit")
	@Log(title = "设备", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Equipment equipment)
	{
		System.out.println(equipment);
		equipment.getInstall().setImei(equipment.getImei());
		installService.updateInstall(equipment.getInstall());
		return toAjax(equipmentService.updateEquipment(equipment));
	}
	
	/**
	 * 删除设备
	 */
	@RequiresPermissions("module:equipment:remove")
	@Log(title = "设备删除", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		// 1：根据Id获取设备IMEI
		Equipment equipment = equipmentService.selectEquipmentById(Long.parseLong(ids));
		// 2：删除与设备关联的安装点信息
		int result = installService.deleteInstallByImei(equipment.getImei());
		// 3：删除设备表中的信息
		return toAjax(equipmentService.deleteEquipmentByIds(ids));
	}


    /**
     * 查询设备详情（设备的安装点信息）
     */
    @GetMapping("/equipmentDetail/{imei}")
    public String detail(@PathVariable("imei") String imei, ModelMap modelMap)
    {
        startPage();
        Equipment equipment = equipmentService.selectEquipmentByImei(imei);
        modelMap.put("equipment", equipment);
        return prefix + "/equipmentDetail";
    }

    /**
     * 查询设备详情（设备的安装点信息）
     */
    @RequiresPermissions("module:equipment:list")
    @PostMapping("/equipmentDetail}")
    @ResponseBody
    public Equipment detail(Equipment equipment)
    {
        startPage();
        return equipment;
    }

    /**
     * 批量导入设备
     * @date 2011-11-07 11:06:50
     **/
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(@RequestParam("file") MultipartFile file, boolean updateSupport) throws Exception
	{
		//获取当前用户部门
		Long deptId = ShiroUtils.getSysUser().getDeptId();
		List<Equipment> equipmentList = equipmentService.importEquipment(file, updateSupport,deptId);
		ExcelUtil<Equipment> util = new ExcelUtil<Equipment>(Equipment.class);
		System.out.println(new Gson().toJson(equipmentList));
		return util.exportExcel(equipmentList, "设备导入结果数据");
	}
	/**
	 * 下载批量导入设备模板表格
	 * @date 2019-11-07 11:06:58
	 **/
    @GetMapping("/equipmentImportTemplate")
    @ResponseBody
    public AjaxResult equipmentImportTemplate()
    {
		ExcelUtil<Equipment> util = new ExcelUtil<Equipment>(Equipment.class);
		Equipment equipment=new Equipment();
		equipment.setType(1);
		equipment.setImei("123456789101112");
		equipment.setHardwareVersion("V3.0");
		equipment.setFirmwareVersion("2.0");
		equipment.setImsi("123456789101112");
		equipment.setManufacturer("我的工厂名称");
		equipment.setCommunication("15177436102");
		equipment.setEquipmentName("我的设备名称");
		List<Equipment> equipmentList=new ArrayList<Equipment>(1);
		equipmentList.add(equipment);
		return util.exportExcel(equipmentList,"equipmentImportTemplate");
    }

}

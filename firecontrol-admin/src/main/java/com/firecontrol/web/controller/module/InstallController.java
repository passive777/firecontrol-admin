package com.firecontrol.web.controller.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.firecontrol.module.domain.Linkman;
import com.firecontrol.module.service.IEquipmentService;
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
import com.firecontrol.module.domain.Install;
import com.firecontrol.module.service.IInstallService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;

/**
 * 设备安装 信息操作处理
 *
 * @author Fire
 * @date 2019-11-28
 */
@Controller
@RequestMapping("/module/install")
public class InstallController extends BaseController
{
    private String prefix = "module/install";

	@Autowired
	private IInstallService installService;
	@Autowired
	private IEquipmentService iEquipmentService;

	@RequiresPermissions("module:install:list")
	@GetMapping()
	public String install()
	{
	    return prefix + "/install";
	}

	/**
	 * 从管辖域移除设备
	 * @return
	 */
	@RequiresPermissions("module:install:edit")
	@Log(title = "设备解绑", businessType = BusinessType.DELETE)
	@PostMapping("/removeEquipment")
	@ResponseBody
	public AjaxResult removeEquipment(Install install){
		return toAjax(installService.deleteInstallByImei(install.getImei()));
	}

	/**
	 * 删除报警联系人
	 * @return
	 */
	@RequiresPermissions("module:install:edit")
	@PostMapping("/removeLinkman")
	@ResponseBody
	public AjaxResult removeLinkman(Linkman linkman){
		System.out.println(linkman);
		return toAjax(installService.removeLinkman(linkman));
	}

	/**
	 * 地图请求安装点信息数据（暂不使用）
	 * @date 2019-11-21 10:47:57
	 **/
	@ResponseBody
	@PostMapping("/platData")
	public  String platData(){
		Map<String ,Object> respMap=new HashMap<>(3);
		List<Float> xList =new ArrayList<>();
		List<Float> yList =new ArrayList<>();
		List<Install> installList = installService.selectInstallList(new Install());
		for (int i = 0; i < installList.size(); i++) {
			Install install = installList.get(i);
			xList.add(install.getLongitude());
			yList.add(install.getLatitude());
		}
		respMap.put("xList",xList);
		respMap.put("yList",yList);
		String json = new Gson().toJson(respMap);
		return json;
	}

	/**
	 * 查询设备安装列表
	 */
	@RequiresPermissions("module:install:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Install install)
	{
		startPage();
        List<Install> list = installService.selectInstallList(install);
		return getDataTable(list);
	}


	/**
	 * 导出设备安装列表
	 */
	@RequiresPermissions("module:install:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Install install)
    {
    	List<Install> list = installService.selectInstallList(install);
        ExcelUtil<Install> util = new ExcelUtil<Install>(Install.class);
        return util.exportExcel(list, "install");
    }

	/**
	 * 新增设备安装
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存设备安装
	 */
	@RequiresPermissions("module:install:add")
	@Log(title = "设备安装", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Install install)
	{
		return toAjax(installService.insertInstall(install));
	}

	/**
	 * 修改设备安装
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Install install = installService.selectInstallById(id);
		mmap.put("install", install);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存设备安装
	 */
	@RequiresPermissions("module:install:edit")
	@Log(title = "设备安装", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Install install)
	{
		return toAjax(installService.updateInstall(install));
	}

	/**
	 * 删除设备安装
	 */
	@RequiresPermissions("module:install:remove")
	@Log(title = "设备安装", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(installService.deleteInstallByIds(ids));
	}

}

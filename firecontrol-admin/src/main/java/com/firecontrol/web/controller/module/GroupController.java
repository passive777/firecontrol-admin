package com.firecontrol.web.controller.module;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.common.utils.file.FileUtils;
import com.firecontrol.framework.util.ShiroUtils;
import com.firecontrol.module.domain.*;
import com.firecontrol.module.service.IGroupDutyService;
import com.firecontrol.module.service.IGroupLinkmanService;
import com.firecontrol.module.service.IGroupPatrolService;
import com.firecontrol.system.domain.SysUser;
import com.firecontrol.system.service.ISysDeptService;
import com.firecontrol.system.service.ISysUserService;
import com.firecontrol.web.controller.util.OBSUtils;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.firecontrol.common.annotation.Log;
import com.firecontrol.common.enums.BusinessType;
import com.firecontrol.module.service.IGroupService;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 管辖域 信息操作处理
 * 
 * @author Fire
 * @date 2020-01-23
 */
@Controller
@RequestMapping("/module/group")
public class GroupController extends BaseController
{
    private String prefix = "module/group";
	
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IGroupDutyService groupDutyService;
	@Autowired
	private ISysUserService userService;
	@Autowired
	private IGroupPatrolService groupPatrolService;
	@Autowired
	private IGroupLinkmanService groupLinkmanService;
    @Autowired
	private ISysDeptService iSysDeptService;

	/**
	 * 查询子管辖域
	 */
	@RequiresPermissions("module:group:list")
	@PostMapping("/childGroupList/{id}")
	@ResponseBody
	public TableDataInfo childGroupList(@PathVariable("id") Integer id, Group group)
	{
		startPage();
		group.setId(id);
		List<Group> groupList = groupService.childGroupList(group);
		if (groupList != null) {
			return getDataTable(groupList);
		}else{
			return getDataTable(new Page());
		}
	}

	/**
	 * 地图请求管辖域点坐标信息
	 * @date 2020-02-21 10:47:57
	 **/
	@ResponseBody
	@PostMapping("/groupPlatData")
	public String platData(){
		Map<String,Object> respMap=new HashMap<>(2);
		List<Double> xList =new ArrayList<>();
		List<Double> yList =new ArrayList<>();
		List<Integer> idList =new ArrayList<>();
		Long deptId = ShiroUtils.getSysUser().getDeptId();
		Long parentId = iSysDeptService.selectDeptById(deptId).getParentId();
		Group group=new Group();
		if (parentId!=0) {
			group.setDeptId(Math.toIntExact(deptId));
		}
		List<Group> groupList = groupService.selectGroupList(group);
		System.out.println("管辖域集合"+new Gson().toJson(groupList));
		for (int i = 0; i < groupList.size(); i++) {
			group = groupList.get(i);
			xList.add(group.getLongitude());
			yList.add(group.getLatitude());
			idList.add(group.getId());
		}
		respMap.put("xList",xList);
		respMap.put("yList",yList);
		respMap.put("idList",idList);
		String json = new Gson().toJson(respMap);
		return json;

	}

	/**
	 * 查询集群下报警设备
	 */
	@ResponseBody
	@PostMapping("/alarmEquipment")
	public List<Equipment> alarmEquipment(Group group){
		List<Equipment> list = groupService.alarmEquipment(group);
		System.out.println(group.getId()+"//"+list);
		return list;
	}

	/**
	 * 地图请求指定管辖域的设备信息
	 * @date 2020-02-21 10:47:57
	 **/
	@ResponseBody
	@PostMapping("/alarmPlatData")
	public  String alarmPlatData(@Param("groupId") String groupId){
		Map<String ,Object> respMap=new HashMap<>(2);
		int flag=1;
        try{
        	Group group=groupService.selectEquipmentInfoList(groupId);
        	respMap.put("group",group);
		}catch (Exception e){
        	flag=0;
        	e.printStackTrace();
		}
        respMap.put("flag",flag);
		String json = new Gson().toJson(respMap);
		System.out.println(json);
		return json;
	}

	/**
	 * 打开管辖域信息界面
	 * @return
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping()
	public String group()
	{
	    return prefix + "/group";
	}

	/**
	 * 获取管辖域报警联系人
	 */
	@RequiresPermissions("module:group:list")
	@PostMapping("/groupLinkmanList/{id}")
	@ResponseBody
	public TableDataInfo groupLinkmanList(@PathVariable("id") Integer id){
		startPage();
		List<Linkman> list = groupLinkmanService.selectByGroup(id);
		if(list != null){
			return getDataTable(list);
		}else{
			//管辖域没有报警联系人
			return  getDataTable(new Page());
		}

	}

	/**
	 * 获取管辖域备用报警联系人
	 */
	@RequiresPermissions("module:group:list")
	@PostMapping("/groupBackupLinkmanList/{id}")
	@ResponseBody
	public TableDataInfo groupBackupLinkmanList(@PathVariable("id") Integer id){
		startPage();
		List<Linkman> list = groupLinkmanService.selectBackupByGroup(id);
		if(list != null){
			return getDataTable(list);
		}else{
			//管辖域没有备用报警联系人
			return  getDataTable(new Page());
		}
	}

	/**
	 * 打开安装点信息界面
	 * @return
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("/installManage")
	public String installManage()
	{
		return prefix + "/installManage";
	}

	/**
	 * 打开管辖域-管辖员
	 * @return
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("openList")
	public String openList()
	{
		return prefix + "/groupPatrol";
	}

	/**
	 * 打开管辖域-责任人
	 * @return
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("openLists")
	public String openLists()
	{
		return prefix + "/groupDuty";
	}

	/**
	 * 打开管辖域-责任人管理
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("groupDuty/{id}")
	public String groupDuty(@PathVariable("id") Integer id, ModelMap map)
	{
		map.put("id", id);
		return prefix + "/groupDutyManage";
	}

	/**
	 * 打开管辖域-管辖员管理
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("groupPatrol/{id}")
	public String groupPatrol(@PathVariable("id") Integer id, ModelMap map)
	{
		map.put("id", id);
		return prefix + "/groupPatrolManage";
	}

	/**
	 * 打开管辖域-管辖员指派
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("openPatrolAdd")
	public String openPatrolAdd(ModelMap map)
	{
		return prefix + "/patrolAdd";
	}

	/**
	 * 查询管辖域下拉框
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("/jsonList")
	@ResponseBody
	public Object json(Group group)
	{
		String s = ShiroUtils.getSysUser().getLoginName();
		if(!s.equals("superAdmin")){
			group.setDeptId(ShiroUtils.getSysUser().getDeptId().intValue());
		}
		List<Group> list = groupService.selectGroupList(group);
		return getSuggestList(list);
	}

	/**
	 * 移除管辖域责任人
	 */
	@RequiresPermissions("module:group:edit")
	@PostMapping("/removeDuty")
	@ResponseBody
	public AjaxResult removeDuty(Long userId, Integer groupId)
	{
		if(groupService.removeDuty(userId, groupId)){
			return success();
		}else{
			return error();
		}

	}

	/**
	 * 移除管辖域管辖员
	 */
	@RequiresPermissions("module:group:edit")
	@PostMapping("/removePatrol")
	@ResponseBody
	public AjaxResult removePatrol(Long userId, Integer groupId)
	{
		if(groupService.removePatrol(userId, groupId)){
			return success();
		}else{
			return error();
		}

	}

	/**
	 * 保存管辖域责任人
	 */
	@RequiresPermissions("module:group:edit")
	@PostMapping("/saveDuty")
	@ResponseBody
	public AjaxResult saveDuty(Long userId, Integer groupId)
	{
		if(groupService.saveDuty(userId, groupId)){
			return success();
		}else{
			return error();
		}

	}

	/**
	 * 保存管辖域管辖员
	 */
	@RequiresPermissions("module:group:edit")
	@PostMapping("/savePatrol")
	@ResponseBody
	public AjaxResult savePatrol(Long userId, Integer groupId)
	{
		if(groupService.savePatrol(userId, groupId)){
			return success();
		}else{
			return error();
		}

	}

	/**
	 * 保存指派管辖域管辖员
	 */
	@RequiresPermissions("module:group:edit")
	@PostMapping("/saveGroupPatrol")
	@ResponseBody
	public AjaxResult saveGroupPatrol(Long userId, Integer groupId)
	{
		if(groupService.savePatrol(userId, groupId)){
			return success();
		}else{
			return error();
		}

	}

	/**
	 * 根据层级关系查询管辖域列表
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("/lists")
	@ResponseBody
	public TableDataInfo getList(Group group){
		startPage();
		List<Group> list = groupService.selectTierGroupList();
		return getDataTable(list);
	}

	/**
	 * 查询管辖域列表
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("/list")
	@ResponseBody
	public List<Group> list(Group group)
	{
		String s = ShiroUtils.getSysUser().getLoginName();
		if(!s.equals("superAdmin") && !s.equals("admin")){
			group.setDeptId(ShiroUtils.getSysUser().getDeptId().intValue());
		}
        List<Group> groupList = groupService.selectGroupList(group);
        return groupList;
	}

	/**
	 * 根据部门查询管辖域列表
	 */
	@RequiresPermissions("module:group:list")
	@GetMapping("/listByDept")
	@ResponseBody
	public List<Group> listByDept(Group group)
	{
		group.setDeptId(Math.toIntExact(ShiroUtils.getSysUser().getDeptId()));
		List<Group> groupList = groupService.listByDept(group);
		return groupList;
	}

	/**
	 * 下拉列表查询本单位集群
	 * @param
	 * @return
	 */
	@GetMapping("/listByDeptSuggest")
	@ResponseBody
	public Object listByDeptSuggest()
	{
		Group g = new Group();
		g.setDeptId(Math.toIntExact(ShiroUtils.getSysUser().getDeptId()));
		List<Group> groupList = groupService.listByDept(g);
		return getSuggestList(groupList);
	}

	/**
	 * 导出管辖域列表
	 */
	@RequiresPermissions("module:group:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Group group)
    {
    	List<Group> list = groupService.selectGroupList(group);
        ExcelUtil<Group> util = new ExcelUtil<Group>(Group.class);
        return util.exportExcel(list, "group");
    }

	/**
	 * 新增管辖域
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 查看设备
	 * @param id
	 * @return
	 */
	@GetMapping("/groupDetail/{id}")
	public String groupDetail(@PathVariable("id") Integer id, ModelMap modelMap){
		Group group = groupService.selectGroupById(id);
		modelMap.put("group", group);
		return prefix + "/groupDetail";
	}

	/**
	 * @param filename
	 * @return
	 */
	@GetMapping(value = "/groupDetail/{filename}",produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity getObsImg(@PathVariable("filename") String filename){

		InputStream inputStream = OBSUtils.downloadFromOBS(filename);

		final HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<>(FileUtils.toByteArray(inputStream), headers, HttpStatus.OK);
	}

	/**
	 * 新增保存管辖域
	 */
	@RequiresPermissions("module:group:add")
	@Log(title = "管辖域", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Group group)
	{
		//添加第一级管辖域
		group.setDeptId(Math.toIntExact(ShiroUtils.getSysUser().getDeptId()));
		return toAjax(groupService.insertGroup(group));
	}

	/**
	 * 获取管辖域责任人列表
	 */
	@PostMapping("/groupDutyList/{id}")
	@ResponseBody
	public TableDataInfo dutyList(@PathVariable("id") Integer id){
		startPage();
		List<GroupDuty> groupDutyList = groupDutyService.selectGroupDutyByGroupId(id);
		List<SysUser> userList = userService.selectUserByGroup(groupDutyList);
		return getDataTable(userList);
	}

	/**
	 * 获取管辖域管辖员列表
	 */
	@PostMapping("/groupPatrolList/{id}")
	@ResponseBody
	public TableDataInfo patrolList(@PathVariable("id") Integer id){
		startPage();
		List<GroupPatrol> groupPatrolList = groupPatrolService.selectGroupPatrolByGroupId(id);
		List<SysUser> userList = userService.selectUserByGroupPatrol(groupPatrolList);
        System.out.println(userList + "///////////");
		return getDataTable(userList);
	}

	/**
	 * 修改管辖域
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Group group = groupService.selectGroupById(id);
		mmap.put("group", group);
	    return prefix + "/edit";
	}

	/**
	 * 管辖域详情
	 */
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Group group = groupService.selectGroupById(id);
		mmap.put("group", group);
		return prefix + "/detail";
	}


	/**
	 * 修改保存管辖域
	 */
	@RequiresPermissions("module:group:edit")
	@Log(title = "管辖域", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Group group)
	{
		return toAjax(groupService.updateGroup(group));
	}

	/**
	 * 批量删除管辖域
	 */
	@RequiresPermissions("module:group:remove")
	@Log(title = "管辖域", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(groupService.deleteGroupByIds(ids));
	}

	/**
	 * 删除管辖域
	 */
	@RequiresPermissions("module:group:remove")
	@Log(title = "管辖域", businessType = BusinessType.DELETE)
	@GetMapping( "/remove/{id}")
	@ResponseBody
	public AjaxResult remove(@PathVariable("id") Integer id)
	{
		return toAjax(groupService.deleteGroupById(id));
	}

	/**
	 * 平面图上传
	 * @param multipartFile 上传文件file
	 * @param groupId     传递的位置id参数
	 * @return　Map
	 *
	 * @Author 麦奇
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public R saveUploadPicture(@RequestParam(value="file") MultipartFile multipartFile, Integer groupId) {

		String obejctName = OBSUtils.uploadToOBS(multipartFile);

		Group group = groupService.selectGroupById(groupId);

		group.setObsObjectName(obejctName);

		groupService.updateGroup(group);

		return 	R.ok().put("data",obejctName);
	}
}

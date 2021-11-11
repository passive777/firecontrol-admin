package com.firecontrol.web.controller.system;

import java.util.List;

import com.firecontrol.system.service.ISysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.firecontrol.common.annotation.Log;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.domain.AjaxResult;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.enums.BusinessType;
import com.firecontrol.common.utils.StringUtils;
import com.firecontrol.common.utils.poi.ExcelUtil;
import com.firecontrol.framework.shiro.service.SysPasswordService;
import com.firecontrol.framework.util.ShiroUtils;
import com.firecontrol.system.domain.SysUser;
import com.firecontrol.system.service.ISysPostService;
import com.firecontrol.system.service.ISysUserService;

/**
 * 用户信息
 * 
 * @author firecontrol
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController
{
    private String prefix = "system/user";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysPasswordService passwordService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/userByGroup")
    public String userByGroup()
    {
        return prefix + "/userByGroup";
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/patrol")
    public String partrol()
    {
        return prefix + "/patrol";
    }


    /**
     * 查询指定部门的所有管辖员
     * @param user
     * @return
     */
    @RequiresPermissions("system:user:list")
    @PostMapping("/patrolList")
    @ResponseBody
    public TableDataInfo patrolList(SysUser user)
    {
        System.out.println("查询指定部门的所有管辖员");
        startPage();
        user.setDeptId(ShiroUtils.getSysUser().getDeptId());
        List<SysUser> list = userService.patrolList(user);
        return getDataTable(list);
    }

    /**
     * 查询指定部门的所有用户
     * @param user
     * @return
     */
    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user)
    {
        startPage();
        System.out.println(ShiroUtils.getSysUser().getLoginName());
        user.setDeptId(ShiroUtils.getSysUser().getDeptId());
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 查询管辖域责任人
     * @param
     * @return
     */
    @RequiresPermissions(("module:group:list"))
    @PostMapping("/dutyListByGroup/{id}")
    @ResponseBody
    public TableDataInfo dutyListByGroup(@PathVariable("id") Integer id){
        startPage();
        List<SysUser> userList = userService.dutyListByGroup(id);
        return getDataTable(userList);
    }

    /**
     * 查询管辖域管辖员
     * @param
     * @return
     */
    @RequiresPermissions(("module:group:list"))
    @PostMapping("/patrolListByGroup/{id}")
    @ResponseBody
    public TableDataInfo patrolListByGroup(@PathVariable("id") Integer id){
        startPage();
        List<SysUser> userList = userService.patrolListByGroup(id);
        return getDataTable(userList);
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/listByGroup")
    @ResponseBody
    public TableDataInfo listByGroup(SysUser user)
    {
        startPage();
        String s = ShiroUtils.getSysUser().getLoginName();
        if(!s.equals("superAdmin") && !s.equals("admin")){
            user.setDeptId(ShiroUtils.getSysUser().getDeptId());
        }
        List<SysUser> list = userService.selectUserListByGroup(user);
        System.out.println(list);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增管辖员用户
     */
    @GetMapping("/openPatrol")
    public String openPatrol(ModelMap mmap)
    {
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/addPatrol";
    }

    /**
     * 保存管辖员
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/addPatrol")
    @ResponseBody
    public AjaxResult addPatrol(SysUser user)
    {
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        user.setDeptId(ShiroUtils.getSysUser().getDeptId());
        user.setRoleIds(new Long[]{3l});
        user.setStatus("0");
        return toAjax(userService.insertUser(user));
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改用户
     */
    @GetMapping("/editInfo/{userId}")
    public String editInfo(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        System.out.println(userId);
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        System.out.println(userService.selectUserById(userId));
        return prefix + "/editInfo";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        user.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user)
    {
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return toAjax(userService.resetUserPwd(user));
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(userService.deleteUserByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SysUser user)
    {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysUser user)
    {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SysUser user)
    {
        return userService.checkEmailUnique(user);
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysUser user)
    {
        return toAjax(userService.changeStatus(user));
    }

    /**
     * 管辖域责任人管理下拉框获取本单位的责任人
     * @param
     * @return
     */
    @GetMapping("/dutyList")
    @ResponseBody
    public Object dutyList()
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        List<SysUser> list = userService.selectUserListByGroupIdForNull(sysUser.getDeptId());
        return getSuggestList(list);
    }

    /**
     * 管辖域管辖员管理下拉框获取本单位未指派管辖任务的管辖员
     * @param
     * @return
     */
    @GetMapping("/patrolList")
    @ResponseBody
    public Object patrolList()
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        List<SysUser> list = userService.selectUserListByDeptIdForNull(sysUser.getDeptId());
        return getSuggestList(list);
    }

}
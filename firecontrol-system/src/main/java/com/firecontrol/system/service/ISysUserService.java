package com.firecontrol.system.service;

import java.util.List;

import com.firecontrol.module.domain.GroupDuty;
import com.firecontrol.module.domain.GroupPatrol;
import com.firecontrol.system.domain.SysUser;

/**
 * 用户 业务层
 * 
 * @author firecontrol
 */
public interface ISysUserService

{

    /**
     * 更新用户基本信息
     * @param user
     * @return
     */
    public int update(SysUser user);


    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 根据条件分页查询已分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);

    /**
     * 通过手机号码查询用户
     * 
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    public SysUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    public SysUser selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteUserByIds(String ids) throws Exception;

    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserInfo(SysUser user);

    /**
     * 修改用户密码信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int resetUserPwd(SysUser user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 登录名称
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(SysUser user);

    /**
     * 根据用户ID查询用户所属角色组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserRoleGroup(Long userId);

    /**
     * 根据用户ID查询用户所属岗位组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserPostGroup(Long userId);

    /**
     * 导入用户数据
     * 
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * 用户状态修改
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int changeStatus(SysUser user);

    /**
     * 根据管辖域-责任对象查询所有责任人
     * @param groupDutyList
     * @return
     */
    List<SysUser> selectUserByGroup(List<GroupDuty> groupDutyList);

    /**
     * 根据管辖域-管辖员对象查询所有管辖员
     * @param groupDutyList
     * @return
     */
    List<SysUser> selectUserByGroupPatrol(List<GroupPatrol> groupPatrolList);

    /**
     * 管辖域责任人管理下拉框获取本单位的责任人
     * @param
     * @return
     */
    List<SysUser> selectDutyByDept(SysUser sysUser);

    /**
     * 移除管辖域号
     * @param userId
     */
    void updateGroup(Long userId);

    /**
     * 管辖域责任人管理下拉框获取本单位的责任人
     * @param dept
     * @return
     */
    List<SysUser> selectUserListByGroupIdForNull(Long dept);

    /**
     * 管辖域管辖员管理下拉框获取本单位未指派管辖任务的管辖员
     * @param
     * @return
     */
    List<SysUser> selectUserListByDeptIdForNull(Long deptId);

    /**
     * 查询当前部门的所有用户
     * @param user
     * @return
     */
    List<SysUser> selectUserListByGroup(SysUser user);

    /**
     * 查询管辖域责任人
     * @param
     * @return
     */
    List<SysUser> dutyListByGroup(Integer id);

    /**
     * 查询管辖域管辖员
     * @param
     * @return
     */
    List<SysUser> patrolListByGroup(Integer id);

    /**
     * 查询指定部门的所有管辖员
     * @param user
     * @return
     */
    List<SysUser> patrolList(SysUser user);
}

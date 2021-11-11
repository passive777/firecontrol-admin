package com.firecontrol.system.mapper;

import java.util.List;

import com.firecontrol.system.domain.SysUser;

/**
 * 用户表 数据层
 *
 * @author firecontrol
 */
public interface SysUserMapper
{
    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 根据条件分页查询未已配用户角色列表
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
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    public int checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);

    /**
     * 通过deptId查询该单位下可选的责任人
     * @date 2019-12-16 21:54:54
     **/

    List<SysUser> selectUserListByGroupIdForNull(Long dept);

    /**
     * 通过deptId查询该单位下可选的管辖员
     * @date 2019-12-16 21:54:54
     **/

    List<SysUser> selectUserListByDeptIdForNull(Long dept);

    /** 管辖域责任人管理下拉框获取本单位的责任人
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
     * 查询当前部门下的所有用户信息
     * @param sysUser
     * @return
     */
    List<SysUser> selectUserListByGroup(SysUser sysUser);

    List<SysUser> dutyListByGroup(Integer groupId);

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

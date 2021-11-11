package com.firecontrol.module.domain;


import com.firecontrol.common.core.domain.BaseEntity;
import com.firecontrol.system.domain.SysUser;

import java.util.List;

/**
 * 管辖域表 sys_group
 * 
 * @author Fire
 * @date 2019-11-08
 */
public class Group extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 管辖域号 */
	private Integer id;
	/** 管辖域名称 */
	private String groupName;
	/** 经度 */
	private Double longitude;
	/** 纬度 */
	private Double latitude;
	/** 国家 */
	private String country;
	/** 省份 */
	private String province;
	/** 市 */
	private String city;
	/** 县/区 */
	private String county;
	/** 详细地址 */
	private String locationDetail;
	/** 父管辖域号 */
	private Integer parentId;
	/** 部门ID */
	private Integer deptId;
	/** 祖级列表 */
	private String ancestors;
	/**　平面图　*/
	private String obsObjectName;
	//责任人集合
	private List<SysUser> dutyList;

	//管辖员集合
	private List<SysUser> patrolList;

	//报警设备集合
	private List<Equipment> alarmEquipment;

	public List<Equipment> getAlarmEquipment() {
		return alarmEquipment;
	}

	public void setAlarmEquipment(List<Equipment> alarmEquipment) {
		this.alarmEquipment = alarmEquipment;
	}


	@Override
	public String toString() {
		return "Group{" +
				"id=" + id +
				", groupName='" + groupName + '\'' +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", country='" + country + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", county='" + county + '\'' +
				", locationDetail='" + locationDetail + '\'' +
				", parentId=" + parentId +
				", deptId=" + deptId +
				", ancestors='" + ancestors + '\'' +
				", obsObjectName='" + obsObjectName + '\'' +
				", dutyList=" + dutyList +
				", patrolList=" + patrolList +
				", alarmEquipment=" + alarmEquipment +
				", childGroupList=" + childGroupList +
				", installList=" + installList +
				", equipmentList=" + equipmentList +
				", alarmList=" + alarmList +
				", show=" + show +
				'}';
	}

	public List<SysUser> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<SysUser> dutyList) {
		this.dutyList = dutyList;
	}

	public List<SysUser> getPatrolList() {
		return patrolList;
	}

	public void setPatrolList(List<SysUser> patrolList) {
		this.patrolList = patrolList;
	}

	/** 子管辖域集合 **/
	private List<Group> childGroupList;

	/**用于地图的集合 **/
	private List<Install> installList;
	private List<Equipment> equipmentList;
	private List<Alarm> alarmList;

	public List<Install> getInstallList() {
		return installList;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setInstallList(List<Install> installList) {
		this.installList = installList;
	}

	public List<Equipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public List<Alarm> getAlarmList() {
		return alarmList;
	}

	public void setAlarmList(List<Alarm> alarmList) {
		this.alarmList = alarmList;
	}

	private Boolean show=false;

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getAncestors() {
		return ancestors;
	}

	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setGroupName(String groupName) 
	{
		this.groupName = groupName;
	}



	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public void setCountry(String country) 
	{
		this.country = country;
	}

	public String getCountry() 
	{
		return country;
	}
	public void setProvince(String province) 
	{
		this.province = province;
	}

	public String getProvince() 
	{
		return province;
	}
	public void setCity(String city) 
	{
		this.city = city;
	}

	public String getCity() 
	{
		return city;
	}
	public void setCounty(String county) 
	{
		this.county = county;
	}

	public String getCounty() 
	{
		return county;
	}
	public void setLocationDetail(String locationDetail) 
	{
		this.locationDetail = locationDetail;
	}

	public String getLocationDetail() 
	{
		return locationDetail;
	}
	public void setParentId(Integer parentId) 
	{
		this.parentId = parentId;
	}

	public Integer getParentId() 
	{
		return parentId;
	}

	public List<Group> getChildGroupList() {
		return childGroupList;
	}

	public void setChildGroupList(List<Group> childGroupList) {
		this.childGroupList = childGroupList;
	}


	public String getObsObjectName() {
		return obsObjectName;
	}

	public void setObsObjectName(String obsObjectName) {
		this.obsObjectName = obsObjectName;
	}
}

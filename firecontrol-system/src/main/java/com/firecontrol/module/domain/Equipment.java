package com.firecontrol.module.domain;


import com.firecontrol.common.annotation.Excel;
import com.firecontrol.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 设备表 sys_equipment
 * 
 * @author Fire
 * @date 2019-11-04
 */
public class Equipment extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 序号 */
	private Long id;
	/** IMEI号 */
	@Excel(name = "IMEI")
	private String imei;
	/** 设备名称 */
	@Excel(name = "设备名称")
	private String equipmentName;
	/** 设备类型编号 */
	@Excel(name = "设备类型")
	private Integer type;
	/** 通讯方式 */
	@Excel(name = "通讯方式")
	private String communication;
	/** 厂商名称 */
	@Excel(name = "厂商名称")
	private String manufacturer;
	/** IMSI号 */
	@Excel(name = "IMSI")
	private String imsi;
	/** 设备版本号 */
	@Excel(name = "设备版本号")
	private String hardwareVersion;
	/** 固件版本号 */
	@Excel(name = "固件版本号")
	private String firmwareVersion;
	/**导入结果状态**/
	@Excel(name = "导入结果")
	private String importStatus;
	/** 部门Id */
	private Integer deptId;
    /** 设备类型 **/
    private EquipmentType equipmentType;
    /** 安装点 */
    private Install install;
    /** 设备监控 */
    private Alarm alarm;
    /** 报警类型 */
    private AlarmType alarmType;
    /** 报警处理 */
    private List<AlarmDispose> alarmDisposeList;
    private Group group;

    /** 状态 **/
    private int status;


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getImportStatus() {
		return importStatus;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}
	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public String getImei()
	{
		return imei;
	}
	public void setEquipmentName(String equipmentName)
	{
		this.equipmentName = equipmentName;
	}

	public String getEquipmentName()
	{
		return equipmentName;
	}
	public void setType(Integer type)
	{
		this.type = type;
	}

	public Integer getType()
	{
		return type;
	}
	public void setCommunication(String communication)
	{
		this.communication = communication;
	}

	public String getCommunication()
	{
		return communication;
	}
	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}

	public String getManufacturer()
	{
		return manufacturer;
	}
	public void setImsi(String imsi)
	{
		this.imsi = imsi;
	}

	public String getImsi()
	{
		return imsi;
	}
	public void setHardwareVersion(String hardwareVersion)
	{
		this.hardwareVersion = hardwareVersion;
	}

	public String getHardwareVersion()
	{
		return hardwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion)
	{
		this.firmwareVersion = firmwareVersion;
	}

	public Integer getDeptId()
	{
		return deptId;
	}

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

	public Install getInstall() {
		return install;
	}

	public void setInstall(Install install) {
		this.install = install;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	public AlarmType getAlarmType() {
		return alarmType;
	}

	@Override
	public String toString() {
		return "Equipment{" +
				"id=" + id +
				", imei='" + imei + '\'' +
				", equipmentName='" + equipmentName + '\'' +
				", type=" + type +
				", communication='" + communication + '\'' +
				", manufacturer='" + manufacturer + '\'' +
				", imsi='" + imsi + '\'' +
				", hardwareVersion='" + hardwareVersion + '\'' +
				", firmwareVersion='" + firmwareVersion + '\'' +
				", importStatus='" + importStatus + '\'' +
				", deptId=" + deptId +
				", equipmentType=" + equipmentType +
				", install=" + install +
				", alarm=" + alarm +
				", alarmType=" + alarmType +
				", alarmDisposeList=" + alarmDisposeList +
				", group=" + group +
				", status=" + status +
				'}';
	}

	public void setAlarmType(AlarmType alarmType) {
		this.alarmType = alarmType;
	}

	public List<AlarmDispose> getAlarmDisposeList() {
		return alarmDisposeList;
	}

	public void setAlarmDisposeList(List<AlarmDispose> alarmDisposeList) {
		this.alarmDisposeList = alarmDisposeList;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}


}

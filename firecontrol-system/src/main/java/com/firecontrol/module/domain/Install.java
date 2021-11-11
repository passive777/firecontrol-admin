package com.firecontrol.module.domain;


import com.firecontrol.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 设备安装表 sys_install
 *
 * @author Fire
 * @date 2019-11-15
 */
public class Install extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 序号 */
	private Integer id;
	/** 管辖域号 */
	private Integer groupId;
	/** IMEI */
	private String imei;
	/** 安装点名称 */
	private String installName;
	/** 安装位置 */
	private String installLocation;
	/** 安装时间 */
	private Date installTime;
	/** 状态：1正常；0异常 */
	private String status;
	/** 安装人员 */
	private Integer userId;
	/** 经度 */
	private Float longitude;
	/** 纬度 */
	private Float latitude;

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId()
	{
		return id;
	}
	public void setGroupId(Integer groupId)
	{
		this.groupId = groupId;
	}

	public Integer getGroupId()
	{
		return groupId;
	}
	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public String getImei()
	{
		return imei;
	}
	public void setInstallName(String installName)
	{
		this.installName = installName;
	}

	public String getInstallName()
	{
		return installName;
	}
	public void setInstallLocation(String installLocation)
	{
		this.installLocation = installLocation;
	}

	public String getInstallLocation()
	{
		return installLocation;
	}
	public void setInstallTime(Date installTime)
	{
		this.installTime = installTime;
	}

	public Date getInstallTime()
	{
		return installTime;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}
	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public Integer getUserId()
	{
		return userId;
	}
	public void setLongitude(Float longitude)
	{
		this.longitude = longitude;
	}

	public Float getLongitude()
	{
		return longitude;
	}
	public void setLatitude(Float latitude)
	{
		this.latitude = latitude;
	}

	public Float getLatitude()
	{
		return latitude;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId())
				.append("groupId", getGroupId())
				.append("imei", getImei())
				.append("installName", getInstallName())
				.append("installLocation", getInstallLocation())
				.append("installTime", getInstallTime())
				.append("status", getStatus())
				.append("userId", getUserId())
				.append("longitude", getLongitude())
				.append("latitude", getLatitude())
				.toString();
	}
}

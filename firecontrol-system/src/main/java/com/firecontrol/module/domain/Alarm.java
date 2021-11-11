package com.firecontrol.module.domain;


import com.firecontrol.common.core.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 报警表 sys_alarm
 * 
 * @author Fire
 * @date 2019-11-07
 */
public class Alarm extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 监控序号 */
	private Integer id;
	/** IMEI */
	private String imei;
	/** 电量百分比0~100 */
	private Float batteryLevel;
	/** RSRP的绝对值 */
	private Integer rsrp;
	/** CSQ:0~99 */
	private Integer csq;
	/** 单位DB */
	private Integer snr;
	/** 报警器状态
1：报警
2：静音
3：保留
4：低压
5：故障
6：保留
7：正常
8: 设备收到单次静音指令
9：设备收到连续静音指令
10：拆卸报警
11：拆卸恢复
14：模拟报警
其它保留 */
	private String alarmStatus;
	/** 离报警点的浓度百分比0~100 */
	private Integer alarmLeave;
	/** 小区基站号长度 */
	private Integer cellIdLength;
	/** 小区基站号 */
	private Integer cellId;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setImei(String imei) 
	{
		this.imei = imei;
	}

	public String getImei()
	{
		return imei;
	}


	public void setBatteryLevel(Float batteryLevel)
	{
		this.batteryLevel = batteryLevel;
	}

	public Float getBatteryLevel()
	{
		return batteryLevel;
	}

	public void setRsrp(Integer rsrp)
	{
		this.rsrp = rsrp;
	}

	public Integer getRsrp() 
	{
		return rsrp;
	}
	public void setCsq(Integer csq) 
	{
		this.csq = csq;
	}

	public Integer getCsq() 
	{
		return csq;
	}
	public void setSnr(Integer snr) 
	{
		this.snr = snr;
	}

	public Integer getSnr() 
	{
		return snr;
	}
	public void setAlarmStatus(String alarmStatus) 
	{
		this.alarmStatus = alarmStatus;
	}

	public String getAlarmStatus() 
	{
		return alarmStatus;
	}
	public void setAlarmLeave(Integer alarmLeave) 
	{
		this.alarmLeave = alarmLeave;
	}

	public Integer getAlarmLeave() 
	{
		return alarmLeave;
	}
	public void setCellIdLength(Integer cellIdLength) 
	{
		this.cellIdLength = cellIdLength;
	}

	public Integer getCellIdLength() 
	{
		return cellIdLength;
	}
	public void setCellId(Integer cellId) 
	{
		this.cellId = cellId;
	}

	public Integer getCellId() 
	{
		return cellId;
	}

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("imei", getImei())
            .append("batteryLevel", getBatteryLevel())
            .append("updateTime", getUpdateTime())
            .append("rsrp", getRsrp())
            .append("csq", getCsq())
            .append("snr", getSnr())
            .append("alarmStatus", getAlarmStatus())
            .append("alarmLeave", getAlarmLeave())
            .append("cellIdLength", getCellIdLength())
            .append("cellId", getCellId())
            .toString();
    }
}

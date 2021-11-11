package com.firecontrol.module.domain;


import com.firecontrol.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 报警处理表 sys_alarm_dispose
 *
 * @author Fire
 * @date 2019-11-24
 */
public class AlarmDispose extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 报警序号 */
	private Long id;
	/** 处理方式 */
	private String dispose;
	/** IMEI */
	private String imei;
	/** 报警时间 */
	private Date produceTime;
	/** 处理时间 */
	private Date disposeTime;
	/** 状态（1已处理：0未处理） */
	private String status;
	/** 报警等级 */
	private String rank;
	/** 电量百分比0~100 */
	private Float batteryLevel;
	/** 信号强度 */
	private Float signal;
	/** 离报警点的浓度百分比0~100 */
	private Integer alarmLevel;
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

	/** 报警类型 */
	private AlarmType alarmType;


	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}
	public void setDispose(String dispose)
	{
		this.dispose = dispose;
	}

	public String getDispose()
	{
		return dispose;
	}
	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public String getImei()
	{
		return imei;
	}
	public void setProduceTime(Date produceTime)
	{
		this.produceTime = produceTime;
	}

	public Date getProduceTime()
	{
		return produceTime;
	}
	public void setDisposeTime(Date disposeTime)
	{
		this.disposeTime = disposeTime;
	}

	public Date getDisposeTime()
	{
		return disposeTime;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}
	public void setRank(String rank)
	{
		this.rank = rank;
	}

	public String getRank()
	{
		return rank;
	}
	public void setBatteryLevel(Float batteryLevel)
	{
		this.batteryLevel = batteryLevel;
	}

	public Float getBatteryLevel()
	{
		return batteryLevel;
	}
	public void setSignal(Float signal)
	{
		this.signal = signal;
	}

	public Float getSignal()
	{
		return signal;
	}
	public void setAlarmLevel(Integer alarmLevel)
	{
		this.alarmLevel = alarmLevel;
	}

	public Integer getAlarmLevel()
	{
		return alarmLevel;
	}
	public void setAlarmStatus(String alarmStatus)
	{
		this.alarmStatus = alarmStatus;
	}

	public String getAlarmStatus()
	{
		return alarmStatus;
	}

	public AlarmType getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(AlarmType alarmType) {
		this.alarmType = alarmType;
	}

	@Override
	public String toString() {
		return "AlarmDispose{" +
				"id=" + id +
				", dispose='" + dispose + '\'' +
				", imei='" + imei + '\'' +
				", produceTime=" + produceTime +
				", disposeTime=" + disposeTime +
				", status='" + status + '\'' +
				", rank='" + rank + '\'' +
				", batteryLevel=" + batteryLevel +
				", signal=" + signal +
				", alarmLevel=" + alarmLevel +
				", alarmStatus='" + alarmStatus + '\'' +
				", alarmType=" + alarmType +
				'}';
	}
}

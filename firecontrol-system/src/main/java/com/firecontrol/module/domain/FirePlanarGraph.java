package com.firecontrol.module.domain;


import com.firecontrol.common.core.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 文件表 fire_planar_graph
 * 
 * @author Fire
 * @date 2020-06-18
 */
public class FirePlanarGraph extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 平面图标记id */
	private Integer fpgId;
	/** 对应位置 */
	private Integer groupId;
	/** x坐标 */
	private BigDecimal x;
	/** y坐标 */
	private BigDecimal y;
	/** 对应的设备id */
	private Integer equipmentId;
	/** 是否报警：0否1是 */
	private Integer warnStatus;

	public void setFpgId(Integer fpgId) 
	{
		this.fpgId = fpgId;
	}

	public Integer getFpgId() 
	{
		return fpgId;
	}
	public void setGroupId(Integer groupId) 
	{
		this.groupId = groupId;
	}

	public Integer getGroupId() 
	{
		return groupId;
	}
	public void setX(BigDecimal x) 
	{
		this.x = x;
	}

	public BigDecimal getX() 
	{
		return x;
	}
	public void setY(BigDecimal y) 
	{
		this.y = y;
	}

	public BigDecimal getY() 
	{
		return y;
	}
	public void setEquipmentId(Integer equipmentId) 
	{
		this.equipmentId = equipmentId;
	}

	public Integer getEquipmentId() 
	{
		return equipmentId;
	}
	public void setWarnStatus(Integer warnStatus) 
	{
		this.warnStatus = warnStatus;
	}

	public Integer getWarnStatus() 
	{
		return warnStatus;
	}

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("fpgId", getFpgId())
            .append("groupId", getGroupId())
            .append("x", getX())
            .append("y", getY())
            .append("equipmentId", getEquipmentId())
            .append("warnStatus", getWarnStatus())
            .toString();
    }

    public FirePlanarGraph() {

	}

	public FirePlanarGraph(Integer fpgId, Integer groupId, BigDecimal x, BigDecimal y, Integer equipmentId, Integer warnStatus) {
		this.fpgId = fpgId;
		this.groupId = groupId;
		this.x = x;
		this.y = y;
		this.equipmentId = equipmentId;
		this.warnStatus = warnStatus;
	}
}

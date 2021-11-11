package com.firecontrol.module.domain;


import com.firecontrol.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 设备报警联系人表 sys_group_linkman
 * 
 * @author Fire
 * @date 2019-11-19
 */

public class GroupLinkman extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 序号 */
	private Integer id;
	/** 管辖域号 */
	private String groupId;
	/** [{"name":张三，"telephone":4165156, "type":1主}] */
	private String linkman;
	/** 所属部门ID */
	private String deptId;

	private List<Linkman> linkManList;

	private List<Linkman> backupLinkmanList;

    public List<Linkman> getLinkmanList() {
        return linkManList;
    }

    public void setLinkmanList(List<Linkman> linkManList) {
        this.linkManList = linkManList;
    }

    public List<Linkman> getBackupLinkmanList() {
        return backupLinkmanList;
    }

    public void setBackupLinkmanList(List<Linkman> backupLinkmanList) {
        this.backupLinkmanList = backupLinkmanList;
    }

    public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setGroupId(String groupId) 
	{
		this.groupId = groupId;
	}

	public String getGroupId() 
	{
		return groupId;
	}
	public void setLinkman(String linkman) 
	{
		this.linkman = linkman;
	}

	public String getLinkman() 
	{
		return linkman;
	}
	public void setDeptId(String deptId) 
	{
		this.deptId = deptId;
	}

	public String getDeptId() 
	{
		return deptId;
	}

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupId", getGroupId())
            .append("linkman", getLinkman())
            .append("deptId", getDeptId())
            .toString();
    }
}

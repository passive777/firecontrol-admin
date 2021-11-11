package com.firecontrol.module.domain;


import com.firecontrol.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 报警类型表 sys_alarm_type
 * 
 * @author Fire
 * @date 2019-11-04
 */
public class AlarmType extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 报警类型编号 */
	private String id;
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
	private String name;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .toString();
    }
}

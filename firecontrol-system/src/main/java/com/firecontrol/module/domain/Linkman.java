package com.firecontrol.module.domain;

import com.firecontrol.common.core.domain.BaseEntity;

public class Linkman extends BaseEntity {

    private Integer id;//管辖域-联系人 id
    private String name;//名字
    private String telephone;//联系电话
    private Integer type;//1主用 0备用

    public Linkman() { }

    public Linkman(String name, String telephone) {
        this.name = name;
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Linkman{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", type=" + type +
                '}';
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

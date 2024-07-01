package org.fkit.springbootmybatistest.entity;

import java.io.Serializable;

public class Machine implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int machine_id;
    private String m_factory;
    private String m_usage;
    private String description;
    private String use_status;

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public String getM_factory() {
        return m_factory;
    }

    public void setM_factory(String m_factory) {
        this.m_factory = m_factory;
    }

    public String getM_usage() {
        return m_usage;
    }

    public void setM_usage(String m_usage) {
        this.m_usage = m_usage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUse_status() {
        return use_status;
    }

    public void setUse_status(String use_status) {
        this.use_status = use_status;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "machine_id=" + machine_id +
                ", m_factory='" + m_factory + '\'' +
                ", m_usage='" + m_usage + '\'' +
                ", description='" + description + '\'' +
                ", use_status='" + use_status + '\'' +
                '}';
    }
}

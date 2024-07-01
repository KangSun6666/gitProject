package org.fkit.springbootmybatistest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private int id;
    private int use_id;
    private String use_name;
    private int machine_id;
    private String machine_usage;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date pstart_use_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date pend_use_time;

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }

    public String getUse_name() {
        return use_name;
    }

    public void setUse_name(String use_name) {
        this.use_name = use_name;
    }

    public String getMachine_usage() {
        return machine_usage;
    }

    public void setMachine_usage(String machine_usage) {
        this.machine_usage = machine_usage;
    }

    public Date getPstart_use_time() {
        return pstart_use_time;
    }

    public void setPstart_use_time(Date pstart_use_time) {
        this.pstart_use_time = pstart_use_time;
    }

    public Date getPend_use_time() {
        return pend_use_time;
    }

    public void setPend_use_time(Date pend_use_time) {
        this.pend_use_time = pend_use_time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", use_id=" + use_id +
                ", use_name='" + use_name + '\'' +
                ", machine_id='" + machine_id + '\'' +
                ", machine_usage='" + machine_usage + '\'' +
                ", pstart_use_time=" + pstart_use_time +
                ", pend_use_time=" + pend_use_time +
                '}';
    }
}

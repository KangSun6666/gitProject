package org.fkit.springbootmybatistest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {
    private int id;
    private int use_id;
    private String use_name;
    private int machine_id;
    private String machine_usage;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date start_use_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date end_use_time;

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

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public String getMachine_usage() {
        return machine_usage;
    }

    public void setMachine_usage(String machine_usage) {
        this.machine_usage = machine_usage;
    }

    public Date getStart_use_time() {
        return start_use_time;
    }

    public void setStart_use_time(Date start_use_time) {
        this.start_use_time = start_use_time;
    }

    public Date getEnd_use_time() {
        return end_use_time;
    }

    public void setEnd_use_time(Date end_use_time) {
        this.end_use_time = end_use_time;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", use_id=" + use_id +
                ", use_name='" + use_name + '\'' +
                ", machine_id=" + machine_id +
                ", machine_usage='" + machine_usage + '\'' +
                ", start_use_time=" + start_use_time +
                ", end_use_time=" + end_use_time +
                '}';
    }
}

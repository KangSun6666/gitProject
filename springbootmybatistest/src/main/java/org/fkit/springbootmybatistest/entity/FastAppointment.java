package org.fkit.springbootmybatistest.entity;

public class FastAppointment {
    private int id;
    private int use_id;
    private String use_name;
    private int machine_id;
    private String machine_usage;

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

    @Override
    public String toString() {
        return "FastAppointment{" +
                "id=" + id +
                ", use_id=" + use_id +
                ", use_name='" + use_name + '\'' +
                ", machine_id=" + machine_id +
                ", machine_usage='" + machine_usage + '\'' +
                '}';
    }
}

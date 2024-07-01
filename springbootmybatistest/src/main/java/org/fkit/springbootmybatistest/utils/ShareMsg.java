package org.fkit.springbootmybatistest.utils;

public class ShareMsg {
    public static int machine_id = 11;

    public static int startInfoId = 0;

    public static int endInfoId = 5;

    public static int getMachine_id() {
        return machine_id;
    }

    public static void setMachine_id(int machine_id) {
        ShareMsg.machine_id = machine_id;
    }

    public static int getStartInfoId() {
        return startInfoId;
    }

    public static void setStartInfoId(int startInfoId) {
        ShareMsg.startInfoId = startInfoId;
    }

    public static int getEndInfoId() {
        return endInfoId;
    }

    public static void setEndInfoId(int endInfoId) {
        ShareMsg.endInfoId = endInfoId;
    }
}

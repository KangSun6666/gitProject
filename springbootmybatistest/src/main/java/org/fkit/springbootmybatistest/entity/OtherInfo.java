package org.fkit.springbootmybatistest.entity;

import java.io.Serializable;

public class OtherInfo implements Serializable {

    private int testAccount;
    private String language;
    private String testDate;
    private String printType;
    private String outType;
    private int stdId;
    private int lightingMinute;
    private int machineId;

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getTestAccount() {
        return testAccount;
    }

    public void setTestAccount(int testAccount) {
        this.testAccount = testAccount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public int getStdId() {
        return stdId;
    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
    }

    public int getLightingMinute() {
        return lightingMinute;
    }

    public void setLightingMinute(int lightingMinute) {
        this.lightingMinute = lightingMinute;
    }

    @Override
    public String toString() {
        return "OtherInfo{" +
                "testAccount=" + testAccount +
                ", language='" + language + '\'' +
                ", testDate='" + testDate + '\'' +
                ", printType='" + printType + '\'' +
                ", outType='" + outType + '\'' +
                ", stdId=" + stdId +
                ", lightingMinute=" + lightingMinute +
                ", machineId=" + machineId +
                '}';
    }
}

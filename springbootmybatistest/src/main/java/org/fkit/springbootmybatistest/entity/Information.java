package org.fkit.springbootmybatistest.entity;

import java.io.Serializable;

public class Information implements Serializable {

    private int infoId;
    private String readDate;
    private int productType;
    private int fd;
    private int machineId;

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    public int getFd() {
        return fd;
    }

    public void setFd(int fd) {
        this.fd = fd;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    @Override
    public String toString() {
        return "Information{" +
                "infoId=" + infoId +
                ", readDate='" + readDate + '\'' +
                ", productName='" + productType + '\'' +
                ", fd=" + fd +
                ", machineId=" + machineId +
                '}';
    }
}

package org.fkit.springbootmybatistest.utils;

import org.fkit.springbootmybatistest.entity.Information;
import org.fkit.springbootmybatistest.entity.OtherInfo;
import org.fkit.springbootmybatistest.mapper.InformationMapper;
import org.fkit.springbootmybatistest.mapper.MachineMapper;
import org.fkit.springbootmybatistest.mapper.OtherInfoMapper;

public class InstructionProcess {

    int machine_id1 = ShareMsg.machine_id;

    int machine_id;
    String machine_version = "";
    String description = "";
    String machine_status = "";
    int minute;
    int second;
    int testAccount;
    int language;
    String lanuageName = "";
    String testDate = "";
    int printType;
    String printTypeName = "";
    int outType;
    String outTypeName = "";
    int stdId;
    int lightingTime;
    String stdName = "";
    String writeMsg = "";
    int infoId;
    String infoDate = "";
    int fd;
    int infoLength;

    public void instructProcess(String str1, MachineMapper machineMapper, OtherInfoMapper otherInfoMapper, InformationMapper informationMapper) {
        String[] st = str1.split("0x");
        for (int i = 0; i < st.length; i++) {
            System.out.println("st[" + i + "]" + st[i]);
        }
        if ("01".equals(st[4])) {    //从机 返回仪器编号 的处理
            int n = Integer.parseInt(st[7], 16);
            String str = "";
            String strr1 = "";
            for (int i=8;i<(8+n);i++){
                str += st[i];
            }
            int k = Integer.parseInt(st[8+n]);
            for (int i=(9+n);i<(9+n+k);i++){
                strr1 += st[i];
            }
            machine_id = Integer.parseInt(str,16);
            machine_version = Integer.parseInt(strr1,16)+".0";   //可在machine_description中写入
            description = machine_version;
            System.out.println("machine_id:"+machine_id);
            System.out.println("machine_version:"+machine_version);
            machineMapper.updateMachineInfo(machine_id,description);   //将设备版本号更新至数据库
        } else if ("02".equals(st[4])) {  //从机 返回仪器状态 的处理
            if ("01".equals(st[6])) {
                if ("01".equals(st[7])) {
                    machine_status = "通讯正常";
                    System.out.println(machine_status);
                    machineMapper.updateMachineStatus(machine_id1,machine_status);     //将设备状态更新至数据库
                }
            } else if ("03".equals(st[6])) {
                minute = Integer.parseInt(st[8], 16);
                second = Integer.parseInt(st[9], 16);
                machine_status = "预热：" + minute + "分" + second + "秒";
                System.out.println(machine_status);
                machineMapper.updateMachineStatus(machine_id1,machine_status);     //将设备状态更新至数据库
            }
        } else if ("03".equals(st[4])) {  //从机 返回其他参数 的处理
            if ("00".equals(st[5])) {
                OtherInfo otherInfo = new OtherInfo();
                testAccount = Integer.parseInt(st[7], 16);
                otherInfo.setTestAccount(testAccount);
                language = Integer.parseInt(st[8], 16);
                if (language == 0){
                    lanuageName = "中文";
                } else if (language == 1){
                    lanuageName = "英文";
                } else if (language == 2){
                    lanuageName = "日文";
                }
                otherInfo.setLanguage(lanuageName);
                int year = Integer.parseInt(st[9], 16);
                int month = Integer.parseInt(st[10], 16);
                int day = Integer.parseInt(st[11], 16);
                testDate = "20" + year + month + day;
                otherInfo.setTestDate(testDate);
                printType = Integer.parseInt(st[12], 16);
                if (printType == 0){
                    printTypeName = "微型打印机";
                } else if (printType == 1){
                    printTypeName = "模式A";
                } else if (printType == 2){
                    printTypeName = "模式B";
                } else if (printType == 3){
                    printTypeName = "不打印";
                }
                otherInfo.setPrintType(printTypeName);
                outType = Integer.parseInt(st[13], 16);
                if (outType == 0){
                    outTypeName = "RS232C";
                } else if (outType == 1){
                    outTypeName = "U盘";
                }
                otherInfo.setOutType(outTypeName);
                stdId = Integer.parseInt(st[14], 16);
                otherInfo.setStdId(stdId);
                String light = st[15] + st[16] + st[17] + st[18];
                lightingTime = Integer.parseInt(light, 16);
                otherInfo.setLightingMinute(lightingTime);
                System.out.println("检测次数：" + testAccount + " " + "语言：" + language + " " + "检测时间：" + testDate + " " + "打印模式：" + printType + " " + "输出模式：" + outType + " " + "标线编号：" + stdId + " " + "光源时间：" + lightingTime + "分钟");
                otherInfo.setMachineId(machine_id1);

                otherInfoMapper.updateInfo(otherInfo);      //将其他参数存入数据库
            } else if ("01".equals(st[5])) {  //读取标线名称
                int i = Integer.parseInt(st[7], 16);
                int j = Integer.parseInt(st[8], 16) + i;
                for (int k = i; k <= j; k++) {
                    String s = k + ":" + StdEnum.getName(k);
                    stdName += s;
                }
                System.out.println(stdName);
            } else if ("80".equals(st[5])) {    // 从机 写入参数返回状态值 的处理
                if ("00".equals(st[7])) {
                    writeMsg = "success";
                    System.out.println(writeMsg);
                }else {
                    writeMsg = "fail";
                    System.out.println(writeMsg);
                }
            }

        }else if ("04".equals(st[4])){
            if ("01".equals(st[5])){    //从机返回数据内容的处理
                Information information = new Information();
                int sum = Integer.parseInt(st[7],16);
//                System.out.println(sum);
                String strrr = st[9]+st[10];
                ShareMsg.setStartInfoId(Integer.parseInt(strrr,16));    //返回数据的起始编号
                for (int i=0;i<sum;i++){
                    if ("01".equals(st[8+11*i])){
                        String str = st[9+11*i]+st[10+11*i];
                        infoId = Integer.parseInt(str,16);
                        ShareMsg.setEndInfoId(infoId);     //返回数据的结束编号
                        information.setInfoId(infoId);
                        int year = Integer.parseInt(st[11+11*i], 16);
                        int month = Integer.parseInt(st[12+11*i], 16);
                        int day = Integer.parseInt(st[13+11*i], 16);
                        infoDate = "20" + year + month + day;
                        information.setReadDate(infoDate);
                        stdId = Integer.parseInt(st[14+11*i],16);
                        information.setProductType(stdId);
                        stdName = StdEnum.getName(stdId);
                        String strr = st[15+11*i]+st[16+11*i]+st[17+11*i]+st[18+11*i];;
                        fd = Integer.parseInt(strr,16);
                        information.setFd(fd);
                        information.setMachineId(machine_id1);
                        System.out.println("有效数据："+"数据编号："+infoId+" "+"数据日期："+infoDate+" "+"标线名称："+stdName+" "+"fd值："+fd+" ");
                        informationMapper.addInformation(information);
                    }
                }
            } else if ("00".equals(st[5])){     //从机 返回数据长度的处理
                String str = st[7]+st[8];
                infoLength = Integer.parseInt(str,16);
                System.out.println("数据长度："+infoLength);
            }
        }

    }

}

package org.fkit.springbootmybatistest.utils;

import org.fkit.springbootmybatistest.mapper.InformationMapper;
import org.fkit.springbootmybatistest.mapper.MachineMapper;
import org.fkit.springbootmybatistest.mapper.OtherInfoMapper;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket socket = null;
    InstructionProcess instructionProcess = new InstructionProcess();
    MachineMapper machineMapper;
    OtherInfoMapper otherInfoMapper;
    InformationMapper informationMapper;

    public ServerThread(Socket socket,MachineMapper machineMapper,OtherInfoMapper otherInfoMapper,InformationMapper informationMapper) {
        this.socket = socket;
        this.machineMapper = machineMapper;
        this.otherInfoMapper = otherInfoMapper;
        this.informationMapper = informationMapper;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        BufferedWriter pw = null;
        try {
            //获取输入流读取客户端发送的请求
            is = socket.getInputStream();

            //转换为字符流并包装为高效输入流
            isr = new InputStreamReader(is, "GBK");
            br = new BufferedReader(isr);

            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("接收客户端消息：" + info);
                instructionProcess.instructProcess(info,machineMapper,otherInfoMapper,informationMapper);

                //将接收到的数据插入数据库
            }
            socket.shutdownInput();

            //获得输出流，相应客户端请求
            os = socket.getOutputStream();
            //为了在控制台显示信息，这里转换为打印流
            pw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));
            pw.write("服务器端接收到客户端请求");
            pw.flush();
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("客户端连接断开");
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
                if (pw != null) {
                    pw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

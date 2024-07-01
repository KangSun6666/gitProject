package org.fkit.springbootmybatistest;

import org.fkit.springbootmybatistest.mapper.InformationMapper;
import org.fkit.springbootmybatistest.mapper.MachineMapper;
import org.fkit.springbootmybatistest.mapper.OtherInfoMapper;
import org.fkit.springbootmybatistest.utils.ServerThread;
import org.fkit.springbootmybatistest.utils.SpringBeanUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
// 扫描数据访问层接口的包名。
@MapperScan("org.fkit.springbootmybatistest.mapper")
public class App {

    public static void main(String[] args) {
		SpringApplication.run(App.class, args);

        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = null;
            System.out.println("###服务器即将启动###");
            int count = 0;
            while (true) {
                //监听客户端请求
                socket = serverSocket.accept();

                //启动线程接收客户端请求
                MachineMapper machineMapper= SpringBeanUtil.getBean(MachineMapper.class);
                OtherInfoMapper otherInfoMapper = SpringBeanUtil.getBean(OtherInfoMapper.class);
                InformationMapper informationMapper = SpringBeanUtil.getBean(InformationMapper.class);
                ServerThread serverThread = new ServerThread(socket,machineMapper,otherInfoMapper,informationMapper);
                serverThread.start();
                count++;
                System.out.println("客户端数量：" + count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      /*  SetMapper s = new SetMapper();
        s.setMapper();*/

	}

}

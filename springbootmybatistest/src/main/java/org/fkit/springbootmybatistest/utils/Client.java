package org.fkit.springbootmybatistest.utils;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void sendMsg(String msg){
        try {
            //创建客户端Socket，指定主机名和端口号
            Socket socket = new Socket("127.0.0.1", 8000);
            //获得输出流
            OutputStream os = socket.getOutputStream();
            //转换为打印输出流
//            PrintWriter pw = new PrintWriter(os);
            BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(os, "GBK"));

            pw.write(msg);
            pw.flush();
            socket.shutdownOutput();

            //获得输入流接收服务器端相应内容
            InputStream is = socket.getInputStream();
            InputStreamReader ist = new InputStreamReader(is,"GBK");
            BufferedReader br = new BufferedReader(ist);

            String data = null;
            while ((data = br.readLine()) != null) {
                System.out.println("服务器说：" + data);
            }

            //关闭流和资源
            is.close();
            ist.close();
            br.close();
            br.close();
            pw.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

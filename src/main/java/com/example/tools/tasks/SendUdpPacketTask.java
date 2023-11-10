package com.example.tools.tasks;

import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Component
public class SendUdpPacketTask {


    public void run() {
        try {
            // 创建UDP套接字
            DatagramSocket socket = new DatagramSocket();

            // 要发送的数据和目标主机信息
            String message = "40800053661110700004AD06202304281105000100231000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F31391000579F73080000000F3139FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
            InetAddress targetAddress = InetAddress.getByName("127.0.0.1"); // 目标IP地址
            int targetPort = 35010; // 目标端口号

            // 将消息转换为字节数组
            byte[] sendData = hexToByte(message, 0);

            // 创建要发送的数据包
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, targetAddress, targetPort);

            // 发送数据包
            socket.send(packet);

            // 关闭套接字
            socket.close();

            System.out.println(sendData.length);
            System.out.println("Data packet sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] hexToByte(String response,int byteLength){
        byte[] bytes = new byte[response.length() / 2 + byteLength];
        for (int i = 0; i < response.length()-byteLength; i += 2) {
            bytes[(i / 2)] = (byte) Integer.parseInt(response.substring(i, (i + 2)), 16);
        }
        return bytes;
    }

}

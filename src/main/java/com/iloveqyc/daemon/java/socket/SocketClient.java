package com.iloveqyc.daemon.java.socket;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

public class SocketClient {

    public void startClient() {
        try {
            Socket socket = new Socket("172.23.63.18", 8007);
            System.out.println("连接上服务器");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            do {
                line = bufferedReader.readLine();
                System.out.println(new Date() + "向服务器发送第一段数据:");
                socket.getOutputStream().write(("this is the first seg, ").getBytes());
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(new Date() + "向服务器发送最后一段数据:" + line);
                socket.getOutputStream().write((line + "\n").getBytes());
                BufferedReader sReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("服务器回复:" + sReader.readLine());
            } while (!StringUtils.isEmpty(line));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient();
        socketClient.startClient();
    }

}

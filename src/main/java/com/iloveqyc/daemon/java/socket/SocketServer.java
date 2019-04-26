package com.iloveqyc.daemon.java.socket;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.*;

public class SocketServer {

    /**
     * 会阻塞在3个地方，线程状态为runnable，不断地争取CPU时间片，其中读写的耗时很可怕，长达1s中的read。
     * 1.等待新客户端连接，accept
     * 2.等待有内容可以接受，read
     * 3.等待发送内容完毕，write
     */
    public void startServer() {
        try {
            // listen, man.
            ServerSocket serverSocket = new ServerSocket(8007);
            // get a thread pool
            ExecutorService executorService = new ThreadPoolExecutor(10,
                                                                     10,
                                                                     0L,
                                                                     TimeUnit.MILLISECONDS,
                                                                     new LinkedBlockingQueue<>(10),
                                                                     Executors.defaultThreadFactory(),
                                                                     new ThreadPoolExecutor.CallerRunsPolicy());
            System.out.println("socket服务器已启动");
            while (true) {
                // block, util someone is coming...
                Socket socket = serverSocket.accept();
                System.out.println(new Date() + "监测到有客户端连接" + socket.toString());
                executorService.submit(() -> {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String line;
                        do {

                            System.out.println(new Date() + "开始从客户端处接收数据");
                            // block, util read something...
                            line = bufferedReader.readLine();
                            System.out.println(new Date() + "从客户端处接收到内容:" + line);

                            // block, util write done
                            socket.getOutputStream().write(("hi" + line + "\n").getBytes());
                        } while (!StringUtils.isEmpty(line));

                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.startServer();
    }

}

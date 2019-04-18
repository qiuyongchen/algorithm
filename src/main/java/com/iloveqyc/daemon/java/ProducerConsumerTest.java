package com.iloveqyc.daemon.java;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class ProducerConsumerTest {
    Integer lock = 5;
    List<Integer> queue = Lists.newArrayList();

    void runTest() {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        Thread a = new Thread(producer);
        Thread b = new Thread(consumer);
        a.start();
        b.start();
    }

    public static void main(String[] args) {
        ProducerConsumerTest producerConsumerTest = new ProducerConsumerTest();
        producerConsumerTest.runTest();
    }


    class Producer implements Runnable{

        public void produce() throws InterruptedException {
            while (true) {
                System.out.println(new Date() + "准备开始生产（抢锁）");
                synchronized (lock) {
                    System.out.println(new Date() + "准备开始生产（抢锁成功）");
                    if (!queue.isEmpty()) {
                        System.out.println(new Date() + "队列不为空，进行等待");
                        // 等待消费
                        lock.wait();
                        System.out.println(new Date() + "队列不为空，进行等待，等待结束，继续执行");
                    }

                    System.out.println(new Date() + "开始生产业务");
                    Thread.sleep(5000L);

                    int i = Math.toIntExact(System.currentTimeMillis() % Integer.MAX_VALUE);
                    queue.add(i);
                    System.out.println(new Date() + "生产了:" + i);
                    System.out.println(new Date() + "结束生产业务");

                    // 通知消费
                    lock.notify();
                }

            }
        }

        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable{
        public void consume() throws InterruptedException {
            while (true) {
                System.out.println(new Date() + "准备开始消费（抢锁）");
                synchronized (lock) {
                    System.out.println(new Date() + "准备开始消费（抢锁成功）");
                    if (queue.isEmpty()) {
                        System.out.println(new Date() + "队列无内容，进行等待");
                        lock.wait();
                        System.out.println(new Date() + "队列无内容，进行等待，等待结束，继续执行");
                    }

                    System.out.println(new Date() + "开始消费业务");
                    Thread.sleep(2000L);

                    int i = queue.get(0);
                    System.out.println(new Date() + "消费了:" + i);
                    queue.remove(0);
                    System.out.println(new Date() + "结束消费业务");
                    lock.notifyAll();
                }


            }
        }

        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

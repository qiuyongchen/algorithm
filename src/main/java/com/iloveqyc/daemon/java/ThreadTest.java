package com.iloveqyc.daemon.java;

public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Integer a = 5;

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a) {
//                    try {
//                        a.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    while (true) {
                        System.out.println("hi");

                    }
                }
            }
        }, "which-will-wait").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a) {
                    while (true) {
                        System.out.println("ha");

                    }
                }
            }
        }, "which-will-block").start();

        Thread.sleep(1000 * 100);

    }

}

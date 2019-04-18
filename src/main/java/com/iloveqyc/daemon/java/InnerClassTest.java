package com.iloveqyc.daemon.java;

public class InnerClassTest {

    private int value;

    class InClass {
        int vFromInClass;
        // 本质上是编译器在默认构造方法中加入InnerClassTest thisA参数，下面的value其实是thisA.value
        void getOutterField() {
            value = 2;
            vFromInClass = 3;
        }
    }

    void testLoaclInnerClass() {
        int hahn = 0;
        MyInterFace interFace = new MyInterFace() {
            @Override
            public int f(int a) {
                a = 0;
                return 1;
            }
        };
        interFace.f(hahn);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }
        );
    }


}

package com.iloveqyc.daemon.java;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import static java.lang.invoke.MethodHandles.lookup;

public class TestMethodHandle {

    public static void main(String[] args) throws Throwable {
        //获取测试类对应类名
        //        System.out.println(new ClassA().getClass());//mall.kotlin.com.myapplication.TestMethodHandle$ClassA
        for(int i = 0; i< 10;i++) {
            System.out.println("test "+i+": ");
            takeOneTest();
            System.out.println();
        }
    }

    //执行一次测试，避免测试时的多次重复代码
    private static void takeOneTest() throws Throwable {

        int repeat_time = 1000 * 100;
        ClassA classA = new ClassA();
        long methodHandleBeginTime = System.currentTimeMillis();
        MethodHandle methodHandle = getPrintlnMH(classA);
        int a = 0;
        for (int i = 0; i <= repeat_time; i++) {
            a = (int) methodHandle.invokeExact("Method Handle test.");
        }
        long methodHandleEndTime = System.currentTimeMillis();

        long reflectionBeginTime = System.currentTimeMillis();
        Class<?> aClass = Class.forName(getClassName());
        Method method = aClass.getDeclaredMethod("println", String.class);
        for (int i = 0; i <= repeat_time; i++)
            a = (int) method.invoke(classA, "Method Handle test.");
        long reflectionEndTime = System.currentTimeMillis();


        long aT = System.currentTimeMillis();
        for (int i = 0; i <= repeat_time; i++)
            a = classA.println("Method Handle test.");
        long bT = System.currentTimeMillis();
        System.out.println("直接调用耗时" + (bT - aT));

        System.out.println("method handle cost time:" + (methodHandleEndTime - methodHandleBeginTime));
        System.out.println("reflection cost time:" + (reflectionEndTime - reflectionBeginTime));
        System.out.println(a);
    }

    private static String getClassName() {
        return "com.iloveqyc.daemon.java.TestMethodHandle$ClassA";
    }


    private static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        MethodType mt = MethodType.methodType(int.class, String.class);
        Class<?> classA = Class.forName(getClassName());
        return lookup().findVirtual(classA, "println", mt).bindTo(receiver);
    }

    static class ClassA {
        public int println(String s) {
            //            System.out.println("ClassA:"+s);
            int a = 0;
            for (int i = 0; i <= 100000; i++) {
                a += i +1 * 2 * 3 * 4;
            }
            return a;
        }
    }
}
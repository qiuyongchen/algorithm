package com.iloveqyc.daemon.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;

public class Apple {

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static void main(String[] args) throws Exception{
        //正常的调用
//        Apple apple = new Apple();
//        apple.setPrice(5);
//        System.out.println("Apple Price:" + apple.getPrice());
        //使用反射调用

        Object myClassForReflection = new MyClassForReflection();
        Class clz = Class.forName("com.iloveqyc.daemon.java.MyClassForReflection");
        Method pubFun = clz.getMethod("pubFun", int.class, String.class);
//        Parameter[] parameterTypes = pubFun.getParameters();
//        clz.getField("pubField").getDeclaredAnnotations();
//        for (Parameter p :
//                parameterTypes) {
//            System.out.println(p.getName() + p.getType());
//        }
//        Constructor appleConstructor = clz.getConstructor();
//        Object myClassForReflection = appleConstructor.newInstance();
        for (int i = 0; i < 100; i++) {
            Object result = pubFun.invoke(myClassForReflection, 14, "heihei");
            System.out.println(result);
        }

//        System.out.println(result);
    }
}

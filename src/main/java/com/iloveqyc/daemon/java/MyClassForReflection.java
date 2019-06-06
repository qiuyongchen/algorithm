package com.iloveqyc.daemon.java;

import javax.annotation.Resource;
import java.util.Date;

public class MyClassForReflection implements MyInterfaceForReflection{

    private int priField;

    @Resource
    static public String pubField;

    private String priFun(int aHa, String bHi) throws RuntimeException {
        System.out.println("hello");
        return "xxx";
    }

    public Integer pubFun(int axxxx, String byyyyy) {
        return 3;
    }

    @Override
    public Date getMyDate(Date date) {
        return new Date();
    }
}

package com.iloveqyc.daemon.java;

import java.math.BigDecimal;
import java.util.Arrays;

public class BigDecimalTest {

    public static void main(String[] args) {
        // 在执行print之前，参数中的double运算已经丢失了精度，
        // 或者说0.05的本质是0.050000000000000002775557...BigDecimal倒是真实地映射出来了
        System.out.println(0.05 + 0.01);
        System.out.println(new BigDecimal(0.05 + 0.01));

        System.out.println("big");
        System.out.println(new BigDecimal(0.05));
        System.out.println(new BigDecimal(0.01));
        // Double.toString能转出我们'想要'的位数，而不是double在计算机中的真实位数
        System.out.println(new BigDecimal(String.valueOf(0.05)));
        System.out.println(new BigDecimal(Double.toString(0.05)));

        System.out.println("add");
        System.out.println(new BigDecimal(0.05).add(new BigDecimal(0.01)));
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);

    }
}

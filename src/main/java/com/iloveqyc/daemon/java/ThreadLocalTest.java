package com.iloveqyc.daemon.java;

public class ThreadLocalTest {
    ThreadLocal<Integer> local = new ThreadLocal<>();

    @Override
    public String toString() {
        local.get();
        return super.toString();
    }
}

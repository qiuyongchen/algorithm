package com.iloveqyc.daemon.algorithm;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {


        int[] num = new int[]{34,54,75,2,6,8,34,3534,7,9,0,2,3,4,5};
        m(num, 0, num.length - 1);
        System.out.println(Arrays.toString(num));
    }

    private static void m(int[] num, int sI, int eI) {
        if (sI == eI) return;
        int mI = (sI + eI) / 2;
        m(num, sI, mI);
        m(num, mI + 1, eI);
        doMergeSort(num, sI, eI);
    }

    private static void doMergeSort(int[] num, int sI, int eI) {
        // 只有一个元素不需要归并
        if (sI == eI) return;
        // 新数组的容量
        int[] r = new int[eI - sI + 1];
        // 中间值（被左半部分包含，我的风格是从0<i<=length-1，而不是0<i<length）
        int mI = (sI + eI) / 2;

        int leftI = sI, rightI = mI + 1, rI = 0;
        while (leftI <= mI && rightI <= eI) {
            if (num[leftI] <= num[rightI]) {
                r[rI++] = num[leftI++];
            } else {
                r[rI++] = num[rightI++];
            }
        }
        while (leftI <= mI) {
            r[rI++] = num[leftI++];
        }
        while (rightI <= eI) {
            r[rI++] = num[rightI++];
        }
        System.arraycopy(r, 0, num, sI, r.length);
    }
}

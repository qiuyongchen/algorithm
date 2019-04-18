package com.iloveqyc.daemon.algorithm;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] num = new int[]{5,7,6,9,8,3,1,2,32,1,0,43,23,2332,4,7,8,4,2,0,9};
        fastSort(num, 0, num.length - 1);
        System.out.println(Arrays.toString(num));
    }

    private static void fastSort(int[] num, int sIndex, int eIndex) {
        if (sIndex >= eIndex) {
            return;
        }

        int mIndex = (sIndex + eIndex) / 2;
        doFastSort(num, sIndex, eIndex);
        fastSort(num, 0, mIndex - 1);
        fastSort(num, mIndex + 1, eIndex);
    }

    private static void doFastSort(int[] num, int sIndex, int eIndex) {
        int mIndex = (sIndex + eIndex) / 2;
//        int mValue = num[mIndex];
        int i = sIndex, j = eIndex;
        while (i <= j) {
            // from end（j一旦小于mIndex，说明所有比mValue小的值都在左边了）
            while (j >= mIndex && num[j] >= num[mIndex]) {
                j--;
            }
            if (j >= mIndex && num[j] < num[mIndex]) {
                change(num, j, mIndex);
                mIndex = j;
                j--;
            }

            // from begin
            while (i <= mIndex && num[i] <= num[mIndex]) {
                i++;
            }
            if (i <= mIndex && num[i] > num[mIndex]) {
                change(num, i, mIndex);
                mIndex = i;
                i++;
            }
        }

    }

    private static void change(int[] num, int j, int mIndex) {
        num[j] = num[j] + num[mIndex]; // 前后之和
        num[mIndex] = num[j] - 2 * num[mIndex]; // 前后之差
        num[j] = (num[j] - num[mIndex]) / 2; // j得到了mIndex的值
        num[mIndex] = num[j] + num[mIndex]; // mIndex
    }
}

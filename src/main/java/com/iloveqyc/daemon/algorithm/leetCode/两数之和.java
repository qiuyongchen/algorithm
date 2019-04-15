package com.iloveqyc.daemon.algorithm.leetCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class 两数之和 {
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> valueIndex = new HashMap();
            for (int i = 0; i < nums.length; i++) {
                valueIndex.put(nums[i], i);
            }

            HashSet hashSet = null;
            // 原本的时间复杂度是O(N*N)，用HashMap来去掉其中一层N，变成最差O(N*LogN)，一般O(N)
            // 代价是空间复杂度翻倍
            for (int i = 0; i < nums.length; i++) {
                if (valueIndex.containsKey(target - nums[i]) && valueIndex.get(target - nums[i]) != i) {
                    return new int[]{i, valueIndex.get(target - nums[i])};
                }
            }
            return new int[1];

        }
    }
}

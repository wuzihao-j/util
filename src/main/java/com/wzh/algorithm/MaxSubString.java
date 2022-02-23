package com.wzh.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 最大子串
 *
 * @author wuzihao3
 * @date 2022/2/16
 */
public class MaxSubString {

    public static void main(String[] args) {
        String str = "tmmzuxt";
        Integer maxSubCount = getMaxCount(str);
        System.out.println(maxSubCount);

    }

    private static Integer getMaxCount(String s) {
        Map<Byte, Integer> map = new HashMap<>(128);
        byte[] bytes = s.getBytes();
        int maxSubCount = 0;
        int tmpMaxSubCount = 0;
        //连续子串的起始index
        int begin = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            Integer index = map.get(b);
            map.put(b, i);
            if (index == null) {
                tmpMaxSubCount++;
            } else if (begin < index) {
                tmpMaxSubCount = i - index;
                begin = index + 1;
            } else {
                tmpMaxSubCount = i - begin + 1;
            }
            maxSubCount = Math.max(maxSubCount, tmpMaxSubCount);
        }
        return maxSubCount;
    }

}

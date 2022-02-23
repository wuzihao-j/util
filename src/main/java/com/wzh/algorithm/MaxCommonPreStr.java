package com.wzh.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 最长公共前缀
 *
 * @author wuzihao3
 * @date 2022/2/16
 */
public class MaxCommonPreStr {

    public static void main(String[] args) {
        String[] strs = {"flower", "flow", "flight"};
        String maxCommonPreStr = getMaxCommonPreStr2(strs);
        System.out.println(maxCommonPreStr);
    }

    public static String getMaxCommonPreStr(String[] strs) {
        Map<String, Integer> map = new HashMap<>();
        for (int j = 0; j < strs.length; j++) {
            String s = strs[j];
            byte[] bytes = s.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                String mapStr = b + "" + i;
                Integer count = map.getOrDefault(mapStr, 0);
                if (count == 0) {
                    count = 1;
                } else {
                    count++;
                }
                map.put(mapStr, count);
            }
        }
        String firstStr = strs[0];
        byte[] bytes = firstStr.getBytes();
        byte[] tmpBytes = new byte[bytes.length];
        int length = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            String mapStr = b + "" + i;
            Integer count = map.getOrDefault(mapStr, 0);
            if (count.equals(strs.length)) {
                tmpBytes[i] = b;
                length++;
            } else {
                break;
            }
        }
        byte[] tmpBytes2 = new byte[length];
        for (int i = 0; i < length; i++) {
            tmpBytes2[i] = tmpBytes[i];
        }
        return new String(tmpBytes2);
    }

    public static String getMaxCommonPreStr2(String[] strs) {
        String minStr = strs[0];
        for (String str : strs) {
            if (minStr.length() > str.length()) {
                minStr = str;
            }
        }
        boolean flag = true;
        while (true) {
            flag = true;
            for (String str : strs) {
                if (minStr.length() == 0) {
                    return "";
                } else if (!str.startsWith(minStr)) {
                    minStr = minStr.substring(0, minStr.length() - 1);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return minStr;
            }
        }

    }
}

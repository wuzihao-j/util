package com.wzh.algorithm;

/**
 * @author wuzihao3
 * @date 2022/3/8
 */
public class 接雨水 {

    public static void main(String[] args) {
        int[] height = {4, 2, 0, 3, 2, 5};
        int trap = trap(height);
        System.out.println(trap);
    }

    public static int trap(int[] height) {
        if (height.length <= 2) {
            return 0;
        }
        int sum = 0;
        int beginHeight = height[0];
        int beginIndex = 0;
        for (int i = 1; i < height.length; i++) {
            int h = height[i];
            if (h < beginHeight) {
                sum += beginHeight - h;
            } else {
                beginHeight = h;
                beginIndex = i;
            }
        }
        return sum;
    }

}

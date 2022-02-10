package com.wzh.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wuzihao3
 * @date 2021/4/20
 */
public class VersionTest {

    public static void main(String[] args) {
        String version = "5.8.2.0";
        if (StringUtils.isNotBlank(version)) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] split = version.split("\\.");
            for (String str : split) {
                //补0
                int num = Integer.parseInt(str);
                if (num > 0) {
                    while (num < 1000) {
                        num *= 10;
                    }
                    stringBuilder.append(num);
                } else {
                    stringBuilder.append("0000");
                }
            }
            long versionNumber = Long.parseLong(stringBuilder.toString());
            if (versionNumber < 999999999999L) {
                versionNumber *= 10000;
            }
            System.out.println(versionNumber);
        }

    }
}

package com.wzh.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author wuzihao@youcheyihou.com
 * @date 2022/2/16 下午9:51
 **/
public class IpFuyuan {

    private static Integer indexGrobal = 0;

    public static void main(String[] args) {
        String str = "0000";
        //把不同数字组合列举出来
        List<String> ips = getStrings(str);
        System.out.println(ips);
    }

    private static List<String> getStrings(String str) {
        if (str.length() > 12 || str.length() < 4) {
            return new ArrayList<>();
        }
        List<List<Integer>> lists = new ArrayList<>();
        getCount(str.length(), "", lists);
        //过滤不合规的数字组合
        List<List<Integer>> filterList = new ArrayList<>();
        for (List<Integer> list : lists) {
            String tmpStr = str;
            boolean flag = true;
            for (Integer integer : list) {
                String firstStr = tmpStr.substring(0, integer);
                Integer first = Integer.parseInt(firstStr);
                if (firstStr.startsWith("0") && firstStr.length() > 1) {
                    flag = false;
                    break;
                }
                if (first > 255) {
                    flag = false;
                    break;
                }
                tmpStr = tmpStr.substring(integer);
            }
            if (flag) {
                filterList.add(list);
            }
        }
        //把数字组合应用到ip
        List<String> ips = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (List<Integer> integers : filterList) {
            String tmpStr = str;
            for (Integer integer : integers) {
                stringBuilder.append(tmpStr.substring(0, integer) + ".");
                tmpStr = tmpStr.substring(integer);
            }
            ips.add(stringBuilder.substring(0, stringBuilder.length() - 1));
            stringBuilder.setLength(0);
        }
        return ips;
    }

    public static void getCount(int c, String str, List<List<Integer>> lists) {
        if (c > 12 || c <= 0) {
            str = str.substring(1);
            if (str.length() == 7) {
                lists.add(Arrays.stream(str.split("\\.")).map(Integer::parseInt).collect(Collectors.toList()));
            }
            return;
        }
        if (c >= 3) {
            getCount(c - 3, str.concat(".3"), lists);
            getCount(c - 2, str.concat(".2"), lists);
            getCount(c - 1, str.concat(".1"), lists);
        } else if (c == 2) {
            getCount(c - 2, str.concat(".2"), lists);
            getCount(c - 1, str.concat(".1"), lists);
        } else if (c == 1) {
            getCount(c - 1, str.concat(".1"), lists);
        }

    }

}

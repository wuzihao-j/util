package com.fasterxml.jackson.dataformat.javaprop;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuzihao3
 * @date 2022/2/10
 */
public class SortProperties extends Properties {

    private LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

    @Override
    public synchronized Object setProperty(String key, String value) {
        keys.add(key);
        return super.setProperty(key, value);
    }

    @Override
    public synchronized Enumeration<Object> keys() {
        List<Object> list = keys.stream().sorted((o1, o2) -> {
            String str1 = o1.toString();
            String str2 = o2.toString();
            if (str1.startsWith("env")) {
                return -1;
            } else {
                return str1.compareTo(str2);
            }
        }).collect(Collectors.toList());
        return Collections.enumeration(list);
    }

    @Override
    public Set<Object> keySet() {
        return keys;
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<String>();
        for (Object key : this.keys) {
            set.add((String) key);
        }
        return set;
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        //排序优先的变量
        ArrayList<String> firstStr = Lists.newArrayList("env", "server", "spring", "yy");

        List<Object> list = keys.stream().sorted((o1, o2) -> {
            String str1 = o1.toString();
            String str2 = o2.toString();
            if (startwith(str1, firstStr)) {
                return order(str1, str2, firstStr);
            } else if (startwith(str2, firstStr)) {
                return order(str1, str2, firstStr);
            } else {
                return str1.compareTo(str2);
            }
        }).collect(Collectors.toList());

        Set<Map.Entry<Object, Object>> listSet = new LinkedHashSet<>();
        for (Object keyObject : list) {
            String key = keyObject.toString();
            String value = (String) get(keyObject);
            Map.Entry<Object, Object> entry = new AbstractMap.SimpleEntry<>(key, value);
            listSet.add(entry);
        }
        return listSet;
    }

    public boolean startwith(String str, List<String> list) {
        for (String s : list) {
            if (str.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

    public int order(String str1, String str2, List<String> list) {
        for (String s : list) {
            if (str1.startsWith(s)) {
                return -1;
            } else if (str2.startsWith(s)) {
                return 1;
            }
        }
        return -1;
    }
}

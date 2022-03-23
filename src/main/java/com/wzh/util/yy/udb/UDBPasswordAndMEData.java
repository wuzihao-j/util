package com.wzh.util.yy.udb;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wuzihao3
 * @date 2022/1/25
 */
public class UDBPasswordAndMEData {

    private static final String EVN = "生产";

    public static void main(String[] args) throws IOException {
        //遍历密码文件
        Map<Integer, File> fileMap = listPasswordFile();
        //把密码和me号数据组合并放到新的目录
        String suffix = "_and_ME.txt";
        for (Map.Entry<Integer, File> fileEntry : fileMap.entrySet()) {
            String newName = fileEntry.getValue().getName().replaceAll(".csv", "") + suffix;
            File writeFile = new File("F:\\密码-me号数据\\" + EVN + "\\me号和密码\\" + newName);
            writeFile.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));
            //key:userId, value:密码相关数据
            Map<Long, String> pwdMap = Files.readLines(fileEntry.getValue(), Charset.defaultCharset()).stream().map(str -> str.replaceAll("\"", "")).collect(Collectors.toMap(str -> Long.parseLong(str.split(",")[1]),
                    str -> str));
            //把me号数据放到map  key:userId  value:me号
            List<String> meIdLines = Files.readLines(new File("F:\\密码-me号数据\\" + EVN + "\\me号\\Hujiao_USER_BL_ID_" + fileEntry.getKey() + ".csv"), Charset.defaultCharset());

            for (String meIdLine : meIdLines) {
                meIdLine = meIdLine.replaceAll("\"", "");
                String[] split = meIdLine.split(",");
                String userId = split[0];
                String meId = split[1];
                String pwdLine = pwdMap.get(Long.parseLong(userId));
                if (pwdLine != null && pwdLine.split(",")[5].equals("1")) {
                    String[] pwdSplit = pwdLine.split(",");
                    bufferedWriter.write(Joiner.on(",").join(userId, meId, pwdSplit[2], pwdSplit[3]) + "\n");
                } else {
                    bufferedWriter.write(Joiner.on(",").join(userId, meId) + "\n");
                }
            }
            bufferedWriter.flush();
        }
    }

    private static Map<Integer, File> listPasswordFile() {
        Map<Integer, File> fileMap = new HashMap<>();
        String dirName = "F:\\密码-me号数据\\" + EVN + "\\密码\\";
        String prefix = "Hujiao_USER_PASSWORD_";
        for (int i = 0; i < 100; i++) {
            File file = new File(dirName + prefix + i + ".csv");
            if (file.exists()) {
                fileMap.put(i, file);
            }
        }
        return fileMap;
    }
}

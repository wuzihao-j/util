package com.wzh.util.yy.udb;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzihao3
 * @date 2022/1/25
 */
public class GroupUserId {

    private static final String EVN = "测试";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:\\密码-me号数据\\" + EVN + "\\Hujiao_USER_BL_ID.csv")), "UTF-8"));//构造一个BufferedReader类来读取文件
        Integer lineCount = 1;
        String line = br.readLine();
        Map<Integer, BufferedWriter> bufferedWriterMap = listPasswordFile();
        while (StringUtils.isNotEmpty(line)) {
            line = line.replaceAll("\"", "");
            String[] split = line.split(",");
            String userId = split[0];
            String meId = split[1];
            BufferedWriter bufferedWriter = bufferedWriterMap.get(Integer.parseInt(userId) % 100);
            bufferedWriter.write(line + "\n");
            lineCount++;
            if (lineCount % 1000 == 0) {
                System.out.println("当前进度，第" + lineCount + "行。");
            }
            line = br.readLine();
        }
        for (Map.Entry<Integer, BufferedWriter> bufferedWriterEntry : bufferedWriterMap.entrySet()) {
            bufferedWriterEntry.getValue().close();
        }
    }

    private static Map<Integer, BufferedWriter> listPasswordFile() throws IOException {
        Map<Integer, BufferedWriter> bufferedWriterMap = new HashMap<>();
        String dirName = "F:\\密码-me号数据\\" + EVN + "\\me号\\";
        String prefix = "Hujiao_USER_BL_ID_";
        for (int i = 0; i < 100; i++) {
            File file = new File(dirName + prefix + i + ".csv");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriterMap.put(i, bufferedWriter);
        }
        return bufferedWriterMap;
    }
}

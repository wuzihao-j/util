package com.wzh.util;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wuzihao3
 * @date 2022/1/4
 */
public class ReplaceAnnotation {

    public static void main(String[] args) throws IOException {
        //哪些要替换
        String consumerXmlPath = "E:\\code\\bilin-java\\punish-center\\src\\main\\resources\\applicationContext-consumer.xml";
        String xmlStr = Files.readLines(new File(consumerXmlPath), Charsets.UTF_8)
                .stream().collect(Collectors.joining("\n"));
        String interfaceRegexp = "interface.*\\.(\\w+)";
        Pattern r = Pattern.compile(interfaceRegexp);
        Matcher m = r.matcher(xmlStr);
        List<String> allInterface = new ArrayList<>();
        while (m.find()) {
            allInterface.add(m.group(1));
        }
        //组装oldStr和newStr


        //遍历指定目录下的所有文件
        String dirPath = "E:\\code\\bilin-java\\punish-center\\src\\main\\java";
        List<String> allFiles = getAllFiles(dirPath);
        int index = 0;
//        allInterface.removeAll(Lists.newArrayList("IUserCenterService", "IWebConfigService", "IUserLoginService",
//                "ISpamCenterService", "ISysDictService", "IllegalAuditedResultCacheService"));
        for (String interfaceName : allInterface) {
            for (String fileName : allFiles) {
                File file = new File(fileName);
                String fileStr = Files.readLines(file, Charsets.UTF_8)
                        .stream().collect(Collectors.joining("\n"));
                String replace = replace(fileStr, "    @.*\n" +
                        "    @.*\n" +
                        "(    private){0,1} " + interfaceName, "    @org.apache.dubbo.config.annotation.Reference(registry = \"yrpc-bilin-zk\", protocol = \"dubbo\")\n" +
                        "    private " + interfaceName);
                replace = replace(replace, "    @Autowired\n" +
                        "(    private){0,1} " + interfaceName, "    @org.apache.dubbo.config.annotation.Reference(registry = \"yrpc-bilin-zk\", protocol = \"dubbo\")\n" +
                        "    private " + interfaceName);
                if (!fileStr.equalsIgnoreCase(replace)) {
                    file.delete();
                    file.createNewFile();
                    Files.write(replace.getBytes(), file);
                }
            }
            System.out.println(String.format("完成了第%s个标签替换,标签:%s", ++index, interfaceName));
        }
    }

    private static List<String> getAllFiles(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            List<String> filePaths = new ArrayList<>();
            File[] files = file.listFiles();
            for (File subfile : files) {
                List<String> allFiles = getAllFiles(subfile.getAbsolutePath());
                filePaths.addAll(allFiles);
            }
            return filePaths;
        } else {
            return Lists.newArrayList(path);
        }
    }

    private static String replace(String source, String oldStr, String newStr) {
        return source.replaceAll(oldStr, newStr);
    }

}

package com.wzh.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 把properties配置转成yml，并追加到yml文件下
 *
 * @author wuzihao3
 * @date 2022/2/10
 */
public class PropertiesToYml {

    public static void main(String[] args) {
        String containFileName = "application.yml";
        List<File> list = new ArrayList<>();

        getAllDirs("E:\\code\\bilin-java2", containFileName, list);

        for (File dir : list) {
            File propertiesFile = getFileByFileNameInDir(dir, "projectGlobal.properties");
            if (propertiesFile != null) {

            }
        }
    }

    private static File getFileByFileNameInDir(File dir, String fileName) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    private static void getAllDirs(String path, String containFileName, List<File> dirs) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (StringUtils.isNotEmpty(containFileName)) {
                Optional<String> first = Arrays.stream(files).map(f -> f.getName()).filter(name -> name.equals(containFileName)).findFirst();
                if (first.isPresent()) {
                    dirs.add(file);
                }
            } else {
                dirs.add(file);
            }
            for (File subfile : files) {
                getAllDirs(subfile.getAbsolutePath(), containFileName, dirs);
            }
        }
    }
}

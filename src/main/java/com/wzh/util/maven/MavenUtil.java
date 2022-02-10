package com.wzh.util.maven;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author wuzihao3
 * @date 2021/4/25
 */
public class MavenUtil {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        String path = "pom.xml";
        String outFile = "F:\\maven/dependencyTree.txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));

        List<File> list = new ArrayList<>();
        getAllDirs("E:\\code\\microservices", path, list);
        for (File dir : list) {
            System.out.println(dir.getAbsolutePath());
            Process exec = Runtime.getRuntime().exec("cmd /k cd " + dir.getAbsolutePath() + " && mvn dependency:tree");
            InputStream is = exec.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            bufferedWriter.write("------------------------------------------------" + dir.getName() + "  begin  " + "-------------------------------------------\n");
            while (StringUtils.isNotEmpty(line)) {
                bufferedWriter.write(line);
                bufferedWriter.write("\n");
                line = br.readLine();
            }
            bufferedWriter.write("------------------------------------------------" + dir.getName() + "  end  " + "-------------------------------------------\n");
            bufferedWriter.flush();
        }
        bufferedWriter.close();
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

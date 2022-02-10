package com.wzh.util.yy;

import cn.hutool.core.lang.copier.Copier;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 修改me号
 */
public class MeIdChange {
    private static final String cookid = "Cookie: JSESSIONID=8531DBABF497C61984AFCC8C42485C70; sysop_privilege_user_id=50045180; sysop_privilege_global_user_id=50045180; hd_newui=0.026677731155966944; hiido_ui=0.24412456748232647; duowan_yadmin_token=9e39507990a940c891a2e8cce23cc626; oa_username=dw_wuzihao3";

    public MeIdChange() {
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<String> list = Files.readLines(new File("E:\\文档\\me号覆盖.txt"), Charsets.UTF_8);
        FileWriter fw = new FileWriter(new File("E:\\文档\\me.txt"));

        for (int i = 1; i < list.size(); ++i) {
            String line = (String) list.get(i);
            String[] split = line.split("\t");
            String uid = split[0];
            String oldMe = split[1];
            String newMe = split[2];
            String num = "第" + i + "个";
            String str = "curl --location --request POST 'http://bilinadmin.duowan.com/HujiaoAdmin/afterLogin/modifyBilinId.htm?action=modify' --header '%s' --header 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'userId=%s' --data-urlencode 'oldBilinId=%s' --data-urlencode 'newBilinId=%s'  >> meModify.log | echo %suid为-%s ----  >> meModify.log \n";
            String format = String.format(str, cookid, uid, oldMe, newMe, num, uid);
            System.out.println(format);
            fw.write(format);
        }
        Copier<ArrayList> arrayListCopier = ArrayList::new;

        fw.close();
    }
}

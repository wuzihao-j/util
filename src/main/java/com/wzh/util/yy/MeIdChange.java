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
    private static final String cookid = "Cookie: JSESSIONID=5235DB27FEAAEF9AFFB2BA6FA61AC315; hd_newui=0.026677731155966944; hiido_ui=0.24412456748232647; isFirst=0; sysop_privilege_user_name=dw_wuzihao3; sysop_privilege_nick_name=%25E5%2590%25B4%25E5%25AD%2590%25E8%25B1%25AA; sysop_privilege_yy_no=909044180; sysop_privilege_idcode=951B852C03478DF3C1229F5642805397E14641BE; sysop_privilege_user_id=50045180; sysop_privilege_global_user_id=50045180; username=dw_wuzihao3; hd_qy_jwt=eyJhbGciOiJSUzI1NiJ9.eyJ5eXVpZCI6IjUwMDQ1MTgwIiwicGFzc3BvcnQiOiJkd193dXppaGFvMyIsInN0YWZmX2lkIjoiNjQyMjMiLCJ0b2tlbiI6IjdjOWI1YTc0MjgwMDQyM2ZhNjNiN2FhYWY1ZDU0ODAxIiwiZXhwIjoxNjQ2NjUzMzMzfQ.Lv2zQPB1m-Vnl5FuQm-_8vXVPDE6ws7l5d8j09Vr7fB_5NUmZh6lCVrlqTuVxnZdqANRS0dG_8XJ0pC4yCPgoRLrgyTwRPMrPwn96DIlMRjthAjbVGl75z2qZrm7hv_X-RZXR9yZ-4ox_S5QpMvsMZofLgjTNDoErPlo_uAn8YzIuslOUCf-_Om3F33sGWxuZGgL8v0vB_JWPniwmoM1TcVcYl7j_qJ-rzFiotPSw8AIxJEey8z7gb3TZg47OJV-nyQRkvX_WRLNKw9vssQ9zv6ObabvJS0KyMGwjhJrtmyCPmu1MzXsHC17016ughhJEDwjIFBjCeD5lWtZqUNT0A; hd_qy_uid=50045180; hd_qy_passport=dw_wuzihao3; hd_qy_token=7c9b5a742800423fa63b7aaaf5d54801; yyuid=50045180; duowan_yadmin_token=a480323ee8284badb86cb6b9656458c9; oa_username=dw_wuzihao3";

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

package com.pzh.util.myutil.common.jvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * 获得服务器端的java版本信息
 */
public class JavaVersion {

    /***
     * 获取java的版本,通过调用服务器命令脚本java -version来获取java的版本信息
     * @return java的版本信息,与在命令行执行效果一致
     */
    public static String version(){
        try {
            // 调用服务器命令脚本
            Process p = Runtime.getRuntime().exec(new String[]{"java", "-version"});
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            bufferedReader.close();
            p.destroy();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

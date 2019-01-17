package com.pzh.util.myutil.common.jvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * 获得服务器端的java版本信息
 */
public class JavaVersion {

    public static String version(){
        StringBuilder sb = null;
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"java", "-version"});
            InputStreamReader inputStreamReader = new InputStreamReader(p.getErrorStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            sb = new StringBuilder();
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

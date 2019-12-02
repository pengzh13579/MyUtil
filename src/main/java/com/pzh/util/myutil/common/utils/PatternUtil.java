package com.pzh.util.myutil.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 正则工具类
 * @author pengzh
 */
public class PatternUtil {

    private PatternUtil() {
    }

    /***
     * 匹配字符出现次数
     * @param srcText 源文本
     * @param findText 需要匹配文本
     * @return 匹配次数
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /***
     * 根据规则获取所有匹配的字符串
     * @param srcText 源文本
     * @param pattern 匹配规则
     * @return 符合条件的字符串列表
     */
    public static List<String> getMatchStrToList(String srcText, String pattern) {
        List<String> result = new ArrayList<>();
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(srcText);
        while(true) {
            if(m.find()){
                result.add(m.group(1));
            } else {
                break;
            }
        }
        return result;
    }

}

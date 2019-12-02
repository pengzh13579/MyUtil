package com.pzh.util.myutil.common.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 数组工具类
 * @author pengzh
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    /***
     * 去掉数组中空项
     * @param inArray 传入数组
     * @return outArrays 传出非空数组
     */
    public static String[] trim(String[] inArray) {
        if (inArray ==null || inArray.length == 0){
            return inArray;
        }
        List<String> list = new ArrayList<>();
        for (String array : inArray) {
            if (array != null && !"".equals(array)) {
                list.add(array);
            }
        }
        String[] outArrays = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            outArrays[i] = list.get(i);
        }
        return outArrays;
    }

}

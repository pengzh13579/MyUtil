package com.pzh.util.myutil.common.utils;

import java.io.Closeable;
import java.io.UnsupportedEncodingException;

/***
 * 转换工具类
 * @author pengzh
 */
public class ConvertUtil {

    private ConvertUtil() {
    }

    /***
     * 全角字符串转换为半角字符串
     * @param input 待转换的字符串
     * @return 转换后的半角字符串
     */
    public static String fullToHalf(String input) {
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 12288) {
                charArray[i] =' ';
            } else if (charArray[i] >= ' ' &&
                    charArray[i]  <= 65374) {
                charArray[i] = (char) (charArray[i] - 65248);
            } else {
            }
        }
        String returnString = new String(charArray);
        return returnString;
    }

    /***
     * 半角字符串转换为全角字符串
     * @param input 待转换的字符串
     * @return 转换后的全角字符串
     */
    public static String halfToFull(String input) {
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 32) {
                charArray[i] = (char) 12288;
            } else if (charArray[i] < 127) {
                charArray[i] = (char) (charArray[i] + 65248);
            }
        }
        return new String(charArray);
    }

    /***
     * 转换为Enum对象<br/>
     * 如果给定的值为空，或者转换失败，返回默认值<br/>
     * @param clazz Enum的Class
     * @param value 值
     * @param defaultValue 默认值
     * @return Enum对象
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value, E defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (clazz.isAssignableFrom(value.getClass())) {
            @SuppressWarnings("unchecked")
            E myE = (E) value;
            return myE;
        }
        final String valueStr = String.valueOf(value);
        if (StringUtil.isBlank(valueStr)) {
            return defaultValue;
        }
        try {
            return Enum.valueOf(clazz, valueStr);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /***
     * String的字符串转换成unicode的String
     * @param strText 全角字符串
     * @return String 每个unicode之间无分隔符
     */
    public static String strToUnicode(String strText) {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
                str.append("\\u" + strHex);
            else // 低位在前面补00
                str.append("\\u00" + strHex);
        }
        return str.toString();
    }

    /***
     * unicode的String转换成String的字符串
     * @param hex 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串
     */
    public static String unicodeToStr(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

    /**
     * 给定字符串转换字符编码<br/>
     * 如果参数为空，则返回原字符串，不报错
     * @param str 被转码的字符串
     * @param sourceCharset 原字符集
     * @param destCharset 目标字符集
     * @return 转换后的字符串
     */
    public static String convertCharset(String str, String sourceCharset, String destCharset) {
        if (StringUtil.hasBlank(str, sourceCharset, destCharset)) {
            return str;
        }
        try {
            return new String(str.getBytes(sourceCharset), destCharset);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static void main(String[] args) {
        System.out.println("半角转全角：" + strToUnicode("1234567890!@#$%^&*()QWDERINJNOA"));
    }
}


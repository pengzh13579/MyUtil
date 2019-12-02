package com.pzh.util.myutil.common.utils;

import java.util.Random;

/***
 * 随机函数工具类
 * @author pengzh
 */
public class RandomUtil {

    // 随机需要的源字符
    private static String originStr = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private RandomUtil() {
    }

    /***
     * 获取随机几位字符串
     * @param num 随机字符串位数
     * @return 随机字符串
     */
    public static String getRandomStr(int num) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= num; i++) {
            int randomNumber = new Random().nextInt(46);
            sb.append(originStr.charAt(randomNumber));
        }
        return sb.toString();
    }
}

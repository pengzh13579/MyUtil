package com.pzh.util.myutil.common.utils;

/***
 * 数字金额大写转换
 * @author pengzh
 */
public class MoneyToChineseUtil {

    private MoneyToChineseUtil() {
    }

    static String fraction[] = { "角", "分"};
    static String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    static String unit[][] = { { "元", "万", "亿"}, { "", "拾", "佰", "仟"}};

    /***
     * 数字金额大写转换
     * @param n 数字金额
     * @return 大写金额
     */
    public static String digitUppercase(double n) {

        String head = n < 0 ? "负" : "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }
}

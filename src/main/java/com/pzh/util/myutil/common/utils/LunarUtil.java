package com.pzh.util.myutil.common.utils;

import com.pzh.util.myutil.model.LunarInfo;
import java.util.Calendar;
import java.util.Date;

/***
 *
 * @author pengzh
 */
public final class LunarUtil {

    private static int monCyl, dayCyl, yearCyl;
    private static int year, month, day;
    private static boolean isLeap;
    private static int[] lunarInfo = {0x04bd8, 0x04ae0, 0x0a570, 0x054d5,
            0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0,
            0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2,
            0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40,
            0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
            0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7,
            0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0,
            0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355,
            0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263,
            0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0,
            0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0,
            0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46,
            0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50,
            0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954,
            0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0,
            0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
            0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50,
            0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6,
            0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0,
            0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};
    /***
     * 天干
     * 排列顺序为：子、丑、寅、卯、辰、巳、午、未、申、酉、戌、亥。
     * 甲、丙、戊、庚、壬为阳干。
     * 乙、丁、己、辛、癸为阴干。
     * 子、寅、辰、午、申、戌为阳支，
     * 丑、卯、巳、未、酉、亥为阴支。
     */
    private static String[] Gan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛",
            "壬", "癸"};
    /***
     * 地支
     */
    private static String[] Zhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未",
            "申", "酉", "戌", "亥"};
    /***
     * 生肖
     */
    private static String[] Animals = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
            "猴", "鸡", "狗", "猪"};
    private static String[] nStr1 = {"日", "一", "二", "三", "四", "五", "六", "七",
            "八", "九", "十"};
    private static String[] nStr2 = {"初", "十", "廿", "卅", "　"};
    private static String[] monthNong = {"正", "正", "二", "三", "四", "五", "六",
            "七", "八", "九", "十", "十一", "十二"};
    private static String[] yearName = {"零", "壹", "贰", "叁", "肆", "伍", "陆",
            "柒", "捌", "玖"};

    private LunarUtil() {
    }

    /***
     * 传回农历y年的总天数
     * @param y 年
     * @return 当年总天数
     */
    private static int lYearDays(int y) {
        int i;
        int sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            // 大月加1天
            sum += (lunarInfo[y - 1900] & i) == 0 ? 0 : 1;
        }
        // 加闰月的天数
        return (sum + leapDays(y));
    }

    /***
     * 判断y年是否为闰月并返回天数
     * @param y 年
     * @return 当年总天数
     */
    private static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            return ((lunarInfo[y - 1900] & 0x10000) == 0 ? 29 : 30);
        } else {
            return (0);
        }
    }

    /***
     * 传回农历y年闰哪个月1-12,没闰传回0
     * @param y 年
     * @return 哪个月是闰月
     */
    private static int leapMonth(int y) {
        return (lunarInfo[y - 1900] & 0xf);
    }

    /***
     * 传回农历y年m月的总天数
     * @param y 年
     * @param m 月
     * @return y年m月的天数
     */
    private static int monthDays(int y, int m) {
        return ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0 ? 29 : 30);
    }

    /***
     * 算出农历, 传入日期, 传回农历日期，该物件属性：year,month,day,isLeap,yearCyl,dayCyl,monCyl
     * @param objDate 日期
     * @return 农历日期
     */
    private static void Lunar1(Date objDate) {
        int i, leap, temp = 0;
        Calendar cl = Calendar.getInstance();
        // 1900-01-31是农历1900年正月初一
        cl.set(1900, 0, 31);
        Date baseDate = cl.getTime();
        // 1900-01-31是农历1900年正月初一
        // 天数(86400000=24*60*60*1000)
        int offset = (int) ((objDate.getTime() - baseDate.getTime()) / 86400000);
        // 1899-12-21是农历1899年腊月甲子日
        dayCyl = offset + 40;
        //1898-10-01是农历甲子月
        monCyl = 14;

        //得到年数
        for (i = 1900; i < 2050 && offset > 0; i++) {
            // 农历每年天数
            temp = lYearDays(i);
            offset -= temp;
            monCyl += 12;
        }
        if (offset < 0) {
            offset += temp;
            i--;
            monCyl -= 12;
        }
        // 农历年份
        year = i;
        // 1864年是甲子年
        yearCyl = i - 1864;
        // 闰哪个月
        leap = leapMonth(i);
        isLeap = false;
        for (i = 1; i < 13 && offset > 0; i++) {
            // 闰月
            if (leap > 0 && i == (leap + 1) && !isLeap) {
                --i;
                isLeap = true;
                temp = leapDays(year);
            } else {
                temp = monthDays(year, i);
            }
            // 解除闰月
            if (isLeap && i == (leap + 1)) {
                isLeap = false;
            }
            offset -= temp;
            if (!isLeap) {
                monCyl++;
            }
        }
        if (offset == 0 && leap > 0 && i == leap + 1) {
            if (isLeap) {
                isLeap = false;
            } else {
                isLeap = true;
                --i;
                --monCyl;
            }
        }
        if (offset < 0) {
            offset += temp;
            --i;
            --monCyl;
        }
        // 农历月份
        month = i;
        // 农历天份
        day = offset + 1;
    }

    private static int getYear() {
        return (year);
    }
    private static int getMonth() {
        return (month);
    }
    private static int getDay() {
        return (day);
    }
    private static int getMonCyl() {
        return (monCyl);
    }
    private static int getYearCyl() {
        return (yearCyl);
    }
    private static int getDayCyl() {
        return (dayCyl);
    }
    private static boolean getIsLeap() {
        return (isLeap);
    }

    /***
     * 传入 offset 传回干支, 0=甲子
     */
    private static String cyclical(int num) {
        return (Gan[num % 10] + Zhi[num % 12]);
    }

    /***
     * 中文日期
     */
    private static String cDay(int d) {
        String s;
        switch (d) {
            case 10:
                s = "初十";
                break;
            case 20:
                s = "二十";
                break;
            case 30:
                s = "三十";
                break;
            default:
                // 取商
                s = nStr2[ (int)(d / 10) ];
                // 取余
                s += nStr1[d % 10];
        }
        return (s);
    }

    private static String cYear(int y) {
        String s = " ";
        int d;
        while (y > 0) {
            d = y % 10;
            y = (y - d) / 10;
            s = yearName[d] + s;
        }
        return (s);
    }

    /***
     * 通过年月日返回农历时间
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 农历时间
     */
    public static String getLunar(String year, String month, String day) {
        int sy = (Integer.parseInt(year) - 4) % 12;
        Calendar cl = Calendar.getInstance();
        cl.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
        Date sDObj = cl.getTime();
        // 日期
        Lunar1(sDObj);
        LunarInfo lunarInfo = new LunarInfo();
        lunarInfo.setAnimalYear(Animals[sy]);
        lunarInfo.setIsLeap(getIsLeap() ? "闰" : "");
        lunarInfo.setMonthNong(monthNong[getMonth()]);
        lunarInfo.setDayNong(cDay(getDay()));
        lunarInfo.setBigMonth(monthDays(getYear(), getMonth()) == 29 ? "小" : "大");
        lunarInfo.setDayCyl(cyclical(getYearCyl()) + "年" + cyclical(getMonCyl()) + "月"+ cyclical(getDayCyl()) + "日");
        String s = "农历 " + "【" + Animals[sy] + "】" + cYear(getYear()) + "年" + " ";
        s += (getIsLeap() ? "闰" : "") + monthNong[getMonth()] + "月"+ (monthDays(getYear(), getMonth()) == 29 ? "小" : "大");
        s += cDay(getDay()) + " ";
        s += cyclical(getYearCyl()) + "年" + cyclical(getMonCyl()) + "月"+ cyclical(getDayCyl()) + "日";
        return s;
    }

    public static void main(String[] args) {
        System.out.println(getLunar("2018", "10", "24"));
        System.out.println(getLunar("2018", "12", "24"));
    }
}

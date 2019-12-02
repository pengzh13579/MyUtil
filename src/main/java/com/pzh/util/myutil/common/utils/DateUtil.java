package com.pzh.util.myutil.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/***
 * 日期操作工具类
 * @author pengzh
 */
public class DateUtil {

	/***
	 * 格式化日期
	 * @param date 日期
	 * @param pattern 格式
	 * @return 格式化后日期
	 */
	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 日期比较
	 * @param date1 字符串日期
	 * @param date2 字符串日期
	 * @param pattern 格式
	 * @return date1>=date2，返回true 否则返回false
	 */
	public static boolean compareDate(String date1, String date2, String pattern) {
		if (parse(date1, pattern) == null || parse(date2, pattern) == null) {
			return false;
		}
		return parse(date1, pattern).getTime() >= parse(date2, pattern).getTime();
	}

	/***
	 * 格式化日期
	 * @param date 日期
	 * @param pattern 格式
	 * @return 时间类型日期
	 */
	public static Date parse(String date, String pattern) {
		try {
			return DateUtils.parseDate(date,pattern);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 时间相减得到天数
	 * @param beginDateStr 开始时间
	 * @param endDateStr 结束时间
	 * @param pattern 格式
	 * @return 相差天数
	 */
	public static long getDaySub(String beginDateStr, String endDateStr, String pattern) {
		Date beginDate = parse(beginDateStr, pattern);
		Date endDate = parse(endDateStr, pattern);
		return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 得到n天之后的日期
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar calendar = Calendar.getInstance();
		// 日期减，如果不够减会将月变动
		calendar.add(Calendar.DATE, daysInt);
		Date date = calendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

}

package com.pzh.util.myutil.common.model;

import com.pzh.util.myutil.common.utils.ArithmeticalMethodUtil;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JVM相关信息
 */
public class JvmInfo {
	/**
	 * 当前JVM占用的内存总数(B)
	 */
	private double totalByte;

	/**
	 * JVM最大可用内存总数(B)
	 */
	private double maxByte;

	/**
	 * JVM空闲内存(B)
	 */
	private double freeByte;
	/**
	 * 当前JVM占用的内存总数(M)
	 */
	private String total;

	/**
	 * JVM最大可用内存总数(M)
	 */
	private String max;

	/**
	 * JVM空闲内存(M)
	 */
	private String free;

	/**
	 * JDK版本
	 */
	private String version;

	/**
	 * JDK路径
	 */
	private String home;

	public double getTotalByte() {
		return ArithmeticalMethodUtil.div(totalByte, (1024 * 1024), 2).doubleValue();
	}

	public void setTotalByte(double totalByte) {
		this.totalByte = totalByte;
	}

	public double getMaxByte() {
		return ArithmeticalMethodUtil.div(maxByte, (1024 * 1024), 2).doubleValue();
	}

	public void setMaxByte(double maxByte) {
		this.maxByte = maxByte;
	}

	public double getFreeByte() {
		return ArithmeticalMethodUtil.div(freeByte, (1024 * 1024), 2).doubleValue();
	}

	public void setFreeByte(double freeByte) {
		this.freeByte = freeByte;
	}

	public double getUsedByte() {
		return ArithmeticalMethodUtil.div(totalByte - freeByte, (1024 * 1024), 2).doubleValue();
	}

	public double getUsageByte() {
		return ArithmeticalMethodUtil.mul(ArithmeticalMethodUtil.div(totalByte - freeByte, totalByte, 4).doubleValue(), 100).doubleValue();
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	/**
	 * 获取JDK名称
	 */
	public String getName() {
		return ManagementFactory.getRuntimeMXBean().getVmName();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * JDK启动时间
	 */
	public String getStartTime() {

		return format(getServerStartDate(),"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * JDK运行时间
	 */
	public String getRunTime() {
		return getDatePoor(new Date(),getServerStartDate());
	}

	@Override
	public String toString() {
		return "Jvm [totalByte=" + totalByte + "freeByte=" + freeByte + "total=" + total + ", max=" + max + ", free=" + free
				+ ", version=" + version + ", home=" + home + "]";
	}
	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	
	/**
	 * 计算两个时间差
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}
}

package com.pzh.util.myutil.common.model;

import com.pzh.util.myutil.common.utils.ArithmeticalMethodUtil;

/***
 * CPU相关信息
 */
public class CpuInfo {

	/**
	 * 核心数
	 */
	private int cpuNum;

	/**
	 * CPU总的使用率
	 */
	private double total;

	/**
	 * CPU系统使用率
	 */
	private double sys;

	/**
	 * CPU用户使用率
	 */
	private double used;

	/**
	 * CPU当前等待率
	 */
	private double wait;

	/**
	 * CPU当前空闲率
	 */
	private double free;

	public int getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(int cpuNum) {
		this.cpuNum = cpuNum;
	}

	public double getTotal() {
		return ArithmeticalMethodUtil.round(ArithmeticalMethodUtil.mul(total, 100).doubleValue(), 2).doubleValue();
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getSys() {
		return ArithmeticalMethodUtil.round(ArithmeticalMethodUtil.mul(sys / total, 100).doubleValue(), 2).doubleValue();
	}

	public void setSys(double sys) {
		this.sys = sys;
	}

	public double getUsed() {
		return ArithmeticalMethodUtil.round(ArithmeticalMethodUtil.mul(used / total, 100).doubleValue(), 2).doubleValue();
	}

	public void setUsed(double used) {
		this.used = used;
	}

	public double getWait() {
		return ArithmeticalMethodUtil.round(ArithmeticalMethodUtil.mul(wait / total, 100).doubleValue(), 2).doubleValue();
	}

	public void setWait(double wait) {
		this.wait = wait;
	}

	public double getFree() {
		return ArithmeticalMethodUtil.round(ArithmeticalMethodUtil.mul(free / total, 100).doubleValue(), 2).doubleValue();
	}

	public void setFree(double free) {
		this.free = free;
	}

	@Override
	public String toString() {
		return "Cpu [cpuNum=" + cpuNum + ", total=" + total + ", sys=" + sys
				+ ", used=" + used + ", wait=" + wait + ", free=" + free + "]";
	}

}

package com.pzh.util.myutil.common.model;

import com.pzh.util.myutil.common.utils.ArithmeticalMethodUtil;

/**
 * 內存相关信息
 */
public class MemoryInfo {

	/**
	 * 内存总量
	 */
	private double total;

	/**
	 * 已用内存
	 */
	private double used;

	/**
	 * 剩余内存
	 */
	private double free;

	public double getTotal() {
		return ArithmeticalMethodUtil.div(total, (1024 * 1024 * 1024), 2);
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public double getUsed() {
		return ArithmeticalMethodUtil.div(used, (1024 * 1024 * 1024), 2);
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public double getFree() {
		return ArithmeticalMethodUtil.div(free, (1024 * 1024 * 1024), 2);
	}

	public void setFree(long free) {
		this.free = free;
	}

	public double getUsage() {
		return ArithmeticalMethodUtil.mul(ArithmeticalMethodUtil.div(used, total, 4), 100);
	}

	@Override
	public String toString() {
		return "Mem [total=" + total + ", used=" + used + ", free=" + free
				+ "]";
	}

}

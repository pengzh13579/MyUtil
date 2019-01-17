package com.pzh.util.myutil.model;

public class LunarInfo {
    // 生肖年
    private String animalYear;
    // 是否闰年
    private String isLeap;
    // 农历月
    private String monthNong;
    // 大小月
    private String bigMonth;
    // 农历日
    private String dayNong;
    // 天干地支日
    private String dayCyl;

    public String getAnimalYear() {
        return animalYear;
    }

    public void setAnimalYear(String animalYear) {
        this.animalYear = animalYear;
    }

    public String getIsLeap() {
        return isLeap;
    }

    public void setIsLeap(String isLeap) {
        this.isLeap = isLeap;
    }

    public String getMonthNong() {
        return monthNong;
    }

    public void setMonthNong(String monthNong) {
        this.monthNong = monthNong;
    }

    public String getBigMonth() {
        return bigMonth;
    }

    public void setBigMonth(String bigMonth) {
        this.bigMonth = bigMonth;
    }

    public String getDayNong() {
        return dayNong;
    }

    public void setDayNong(String dayNong) {
        this.dayNong = dayNong;
    }

    public String getDayCyl() {
        return dayCyl;
    }

    public void setDayCyl(String dayCyl) {
        this.dayCyl = dayCyl;
    }
}

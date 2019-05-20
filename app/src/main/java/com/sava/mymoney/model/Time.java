package com.sava.mymoney.model;

public class Time {
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mDoW;
    private String[] months = {"","JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","STEP","OCT","NOV","DEC"};
    public Time() {
    }

    public Time(int mDay, int mMonth, int mYear) {
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.mYear = mYear;
    }

    public Time(int mDay, int mMonth, int mYear, int mDoW) {
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.mYear = mYear;
        this.mDoW = mDoW;
    }

    public Time(int mDay) {
        this.mDay = mDay;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    @Override
    public String toString() {
        String day = (mDay < 10) ? ("0" + mDay + " - ") : (mDay + " - ");
        String month = (mMonth < 10) ? ("0" + mMonth + " - ") : (mMonth + " - ");
        return day + month + mYear;
    }
    public int comperTiem(Time time2) {
        int t1 = getmYear() * 500 + getmMonth() * 35 + getmDay();
        int t2 = time2.getmYear() * 500 + time2.getmMonth() * 35 + time2.getmDay();
        return t1 - t2;
    }
    public String showMonth(){
        return months[mMonth]+" " + mYear;
    }
    public  String showDay(){
        String d = (mDay<10) ? ("0"+mDay) : ""+mDay;
        return d + " "+ months[mMonth] + " , " + mYear;
    }
}

package com.sava.mymoney.model;

public class Time {
    private int mDay;
    private int mMonth;
    private int mYear;

    public Time(int mDay, int mMonth, int mYear) {
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.mYear = mYear;
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
        return  mDay + " - " + mMonth +" - " + mYear;
    }
}

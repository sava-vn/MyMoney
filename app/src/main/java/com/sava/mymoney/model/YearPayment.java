package com.sava.mymoney.model;

public class YearPayment extends TimePayment {
    private MonthPayment[] mArrMonthPayment;

    public YearPayment() {
        this.mArrMonthPayment = new MonthPayment[13];
        for (int i = 1; i <= 12; i++)
            this.mArrMonthPayment[i] = new MonthPayment();
    }

    public MonthPayment[] getmArrMonthPayment() {
        return mArrMonthPayment;
    }

    @Override
    public String toString() {
        return this.mTime.getmYear() + "";
    }
}

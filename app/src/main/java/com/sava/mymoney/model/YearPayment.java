package com.sava.mymoney.model;

public class YearPayment extends TimePayment {
    private MonthPayment[] mArrMonthPayment;

    public YearPayment(Time time) {
        this.mArrMonthPayment = new MonthPayment[13];
        for (int i = 1; i <= 12; i++)
            this.mArrMonthPayment[i] = null;
        this.mTime = time;
    }

    public MonthPayment[] getmArrMonthPayment() {
        return mArrMonthPayment;
    }

    public void setmArrMonthPayment(MonthPayment[] mArrMonthPayment) {
        this.mArrMonthPayment = mArrMonthPayment;
    }

    @Override
    public String toString() {
        return this.mTime.getmYear() + "";
    }
}

package com.sava.mymoney.model;

public class MonthPayment extends TimePayment {
    private DayPayment[] mArrDayPayment;


    public MonthPayment() {
        this.mArrDayPayment = new DayPayment[32];
        for(int i =1;i<=31;i++)
            this.mArrDayPayment[i]=new DayPayment();
    }

    public DayPayment[] getmArrDayPayment() {
        return mArrDayPayment;
    }

    public void setmArrDayPayment(DayPayment[] mArrDayPayment) {
        this.mArrDayPayment = mArrDayPayment;
    }

    @Override
    public String toString() {
        return this.mTime.showMonth();
    }
}

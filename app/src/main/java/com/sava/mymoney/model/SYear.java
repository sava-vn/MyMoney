package com.sava.mymoney.model;

public class SYear extends SDMY {
    private SMonth[] mArrMonthPayment;

    public SYear() {
        this.mArrMonthPayment = new SMonth[13];
        for (int i = 1; i <= 12; i++)
            this.mArrMonthPayment[i] = new SMonth();
    }

    public SMonth[] getmArrMonthPayment() {
        return mArrMonthPayment;
    }

    @Override
    public String toString() {
        return this.mSDate.getmYear() + "";
    }
}

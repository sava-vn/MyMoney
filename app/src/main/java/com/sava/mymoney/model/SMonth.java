package com.sava.mymoney.model;

public class SMonth extends SDMY {
    private SDay[] mArrSDay;


    public SMonth() {
        this.mArrSDay = new SDay[32];
        for(int i =1;i<=31;i++)
            this.mArrSDay[i]=new SDay();
    }

    public SDay[] getmArrSDay() {
        return mArrSDay;
    }

    public void setmArrSDay(SDay[] mArrSDay) {
        this.mArrSDay = mArrSDay;
    }

    @Override
    public String toString() {
        return this.mSDate.showMonth();
    }
}

package com.sava.mymoney.model;

public class Payment {
    private String mIdPayment;
    private Time mTime;
    private int mMoney;
    private int mType;
    private String mNote;
    private Time mTime2;

    public String getmIdPayment() {
        return mIdPayment;
    }

    public void setmIdPayment(String mIdPayment) {
        this.mIdPayment = mIdPayment;
    }

    public Payment() {
        this.mTime2 = new Time(0,0,0);
    }
    public Payment(Time mTime, int mMoney, int mType, String mNote) {
        this.mTime = mTime;
        this.mMoney = mMoney;
        this.mType = mType;
        this.mNote = mNote;
        this.mTime2 = new Time(0,0,0);
    }

    public Time getmTime2() {
        return mTime2;
    }

    public void setmTime2(Time mTime2) {
        this.mTime2 = mTime2;
    }

    public Time getmTime() {
        return mTime;
    }

    public void setmTime(Time mTime) {
        this.mTime = mTime;
    }

    public int getmMoney() {
        return mMoney;
    }

    public void setmMoney(int mMoney) {
        this.mMoney = mMoney;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }
}

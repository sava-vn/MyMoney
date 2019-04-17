package com.sava.mymoney.model;

public class Payment {
    private Time mTime;
    private int mMoney;
    private int mType;
    private String mNote;

    public Payment() {
    }

    public Payment(Time mTime, int mMoney, int mType, String mNote) {
        this.mTime = mTime;
        this.mMoney = mMoney;
        this.mType = mType;
        this.mNote = mNote;
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

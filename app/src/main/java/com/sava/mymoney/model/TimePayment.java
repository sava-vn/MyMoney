package com.sava.mymoney.model;

public class TimePayment {
    protected int mMoney;
    protected int mVi;
    protected Time mTime;
    protected int mViewType;
    protected String mNote;

    public TimePayment() {

    }

    public TimePayment(int mMoney, int mVi, Time mTime, int mViewType, String mNote) {
        this.mMoney = mMoney;
        this.mVi = mVi;
        this.mTime = mTime;
        this.mViewType = mViewType;
        this.mNote = mNote;
    }

    public TimePayment(Time mTime) {
        this.mTime = mTime;
    }

    public int getmMoney() {
        return mMoney;
    }

    public void setmMoney(int mMoney) {
        this.mMoney += mMoney;
    }

    public int getmVi() {
        return mVi;
    }

    public void setmVi(int mVi) {
        this.mVi = mVi;
    }

    public Time getmTime() {
        return mTime;
    }

    public void setmTime(Time mTime) {
        this.mTime = mTime;
    }

    public int getmViewType() {
        return mViewType;
    }

    public void setmViewType(int mViewType) {
        this.mViewType = mViewType;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }
}

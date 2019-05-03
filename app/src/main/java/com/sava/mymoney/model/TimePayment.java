package com.sava.mymoney.model;

public class TimePayment {
    protected int mMoney;
    protected int mVi;
    protected Time mTime;
    protected int mViewType;
    protected String mNote;
    protected int mCountPay;

    public int getmCountPay() {
        return mCountPay;
    }

    public void setmCountPay(int mCountPay) {
        this.mCountPay += mCountPay;
    }

    public TimePayment() {

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

package com.sava.mymoney.model;

public class TimePayment {
    //Tiền vào ra trong ngày
    protected int mMoney;

    //Số dư trong ngày
    protected int mBalance;

    //Ngày
    protected Time mTime;

    //Kiểu hiển thị
    protected int mViewType;

    //Ghi chú
    protected String mNote;

    //Đếm số lượng thanh toán trong ngày
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

    public int getmBalance() {
        return mBalance;
    }

    public void setmBalance(int mBalance) {
        this.mBalance = mBalance;
    }

    public Time getmTime() {
        return mTime;
    }

    public void setmTime(Time mTime) {
        this.mTime = new Time(mTime.getmDay(),mTime.getmMonth(),mTime.getmYear());
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

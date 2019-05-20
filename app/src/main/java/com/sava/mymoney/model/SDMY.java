package com.sava.mymoney.model;

public class SDMY {
    protected int mMoneyOut;
    protected int mMoneyIn;
    protected SDate mSDate;
    protected int mViewType;
    protected int mCountPay;
    protected int mBalance;

    public SDMY() {
        this.mSDate = new SDate();
    }

    public int getmMoneyOut() {
        return mMoneyOut;
    }

    public void setmMoneyOut(int mMoneyOut) {
        this.mMoneyOut += mMoneyOut;
    }

    public int getmMoneyIn() {
        return mMoneyIn;
    }

    public void setmMoneyIn(int mMoneyIn) {
        this.mMoneyIn +=mMoneyIn;
    }

    public SDate getmSDate() {
        return mSDate;
    }

    public void setmSDate(SDate mSDate) {
        this.mSDate = new SDate(mSDate.getmDay(),mSDate.getmMonth(),mSDate.getmYear(),mSDate.getmDoW());
    }

    public int getmViewType() {
        return mViewType;
    }

    public void setmViewType(int mViewType) {
        this.mViewType = mViewType;
    }

    public int getmCountPay() {
        return mCountPay;
    }

    public void setmCountPay(int mCountPay) {
        this.mCountPay += mCountPay;
    }

    public int getmBalance() {
        return mBalance;
    }

    public void setmBalance(int mBalance) {
        this.mBalance = mBalance;
    }
}

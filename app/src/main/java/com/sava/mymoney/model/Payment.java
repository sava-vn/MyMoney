package com.sava.mymoney.model;

public class Payment {
    private String mIdPayment;
    private SDate mSDate;
    private int mMoney;
    private int mType;
    private String mNote;

    public Payment() {
        this.mSDate = new SDate();
        this.mNote = "";
    }

    public Payment(String mIdPayment, SDate mSDate, int mMoney, int mType, String mNote) {
        this.mIdPayment = mIdPayment;
        this.mSDate = new SDate(mSDate.getmDay(),mSDate.getmMonth(),mSDate.getmYear(),mSDate.getmDoW());
        this.mMoney = mMoney;
        this.mType = mType;
        this.mNote = mNote;
    }

    public String getmIdPayment() {
        return mIdPayment;
    }

    public void setmIdPayment(String mIdPayment) {
        this.mIdPayment = mIdPayment;
    }

    public SDate getmSDate() {
        return mSDate;
    }

    public void setmSDate(SDate mSDate) {
        this.mSDate = new SDate(mSDate.getmDay(),mSDate.getmMonth(),mSDate.getmYear(),mSDate.getmDoW());
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

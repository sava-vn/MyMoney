package com.sava.mymoney.model;

public class Borrow extends Payment {
    private SDate mSDate2;
    private String mPerson;

    public Borrow() {
        super();
        this.mSDate2 = new SDate();
        this.mPerson = "";
    }

    public Borrow(String mIdPayment, SDate mSDate, int mMoney, int mType, String mNote, SDate mSDate2, String mPerson) {
        super(mIdPayment, mSDate, mMoney, mType, mNote);
        this.mSDate2 = new SDate(mSDate2.getmDay(),mSDate2.getmMonth(),mSDate2.getmYear(),mSDate2.getmDoW());
        this.mPerson = mPerson;
    }

    public SDate getmSDate2() {
        return mSDate2;
    }

    public void setmSDate2(SDate mSDate2) {
        this.mSDate2 = mSDate2;
    }

    public String getmPerson() {
        return mPerson;
    }

    public void setmPerson(String mPerson) {
        this.mPerson = mPerson;
    }
}

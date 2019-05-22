package com.sava.mymoney.model;

public class SBL extends Payment {
    private SDate mSDate2;
    private SDate mSDate3;
    private String mPerson;
    private int isPayment;

    public SDate getmSDate3() {
        return mSDate3;
    }

    public void setmSDate3(SDate mSDate3) {
        this.mSDate3 = new SDate(mSDate3.getmDay(),mSDate3.getmMonth(),mSDate3.getmYear(),mSDate3.getmDoW());
    }

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }

    public SBL() {
        super();
        this.mSDate2 = new SDate();
        this.mSDate3 = new SDate();
        this.mPerson = "";
    }

    public SBL(String mIdPayment, SDate mSDate, int mMoney, int mType, String mNote, SDate mSDate2, String mPerson) {
        super(mIdPayment, mSDate, mMoney, mType, mNote);
        this.mSDate2 = new SDate(mSDate2.getmDay(),mSDate2.getmMonth(),mSDate2.getmYear(),mSDate2.getmDoW());
        this.mPerson = mPerson;
    }

    public SDate getmSDate2() {
        return mSDate2;
    }

    public void setmSDate2(SDate mSDate2) {
        this.mSDate2 = new SDate(mSDate2.getmDay(),mSDate2.getmMonth(),mSDate2.getmYear(),mSDate2.getmDoW());
    }

    public String getmPerson() {
        return mPerson;
    }

    public void setmPerson(String mPerson) {
        this.mPerson = mPerson;
    }
}

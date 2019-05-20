package com.sava.mymoney.model;
import java.util.ArrayList;

public class SDay extends SDMY {
    private ArrayList<Payment> mListPayment;

    public SDay() {
        this.mListPayment = new ArrayList<>();
    }

    public ArrayList<Payment> getmListPayment() {
        return mListPayment;
    }

    @Override
    public String toString() {
        return this.mSDate.showDay();
    }
}

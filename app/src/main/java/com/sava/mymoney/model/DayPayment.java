package com.sava.mymoney.model;
import java.util.ArrayList;

public class DayPayment extends TimePayment {
    private ArrayList<Payment> mListPayment;

    public DayPayment() {
        this.mListPayment = new ArrayList<>();
    }

    public ArrayList<Payment> getmListPayment() {
        return mListPayment;
    }

    @Override
    public String toString() {
        return this.mTime.showDay();
    }
}

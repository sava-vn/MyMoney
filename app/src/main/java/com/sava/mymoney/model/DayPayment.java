package com.sava.mymoney.model;
import java.util.ArrayList;

public class DayPayment extends TimePayment {
    private ArrayList<Payment> mListPayment;

    public DayPayment(Time time) {
        this.mListPayment = new ArrayList<>();
        this.mTime = time;
    }

    public ArrayList<Payment> getmListPayment() {
        return mListPayment;
    }

    public void setmListPayment(ArrayList<Payment> mListPayment) {
        this.mListPayment = mListPayment;
    }

    @Override
    public String toString() {
        return this.mTime.toString();
    }
}

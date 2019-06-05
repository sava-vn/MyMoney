package com.sava.mymoney.model;

import com.sava.mymoney.common.MyValues;

import java.util.ArrayList;

public class Wallet {
    private int mMoney;
    private SYear[] sYears;
    private ArrayList<SBL> mListBorrow;
    private int mMoneyBorrow;
    private ArrayList<SBL> mListLoan;
    private int mMoneyLoan;

    public Wallet() {
        this.sYears = new SYear[31];
        for (int i = 1; i <= 30; i++)
            this.sYears[i] = new SYear();
        mListBorrow = new ArrayList<>();
        mListLoan = new ArrayList<>();
    }

    public ArrayList<SBL> getmListBorrow() {
        return mListBorrow;
    }

    public ArrayList<SBL> getmListLoan() {
        return mListLoan;
    }

    public int getmMoney() {
        return mMoney;
    }

    public SYear[] getsYears() {
        return sYears;
    }

    public void addPayment(Payment payment) {
        int ngay = payment.getmSDate().getmDay();
        int thang = payment.getmSDate().getmMonth();
        int nam = payment.getmSDate().getmYear() - 2015;
        int money = payment.getmMoney();
        SDate SDate = payment.getmSDate();

        this.mMoney += money;
        if (money > 0) {
            sYears[nam].setmMoneyIn(money);
            sYears[nam].getmArrMonthPayment()[thang].setmMoneyIn(money);
            sYears[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay].setmMoneyIn(money);
        } else {
            sYears[nam].setmMoneyOut(money);
            sYears[nam].getmArrMonthPayment()[thang].setmMoneyOut(money);
            sYears[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay].setmMoneyOut(money);
        }
        sYears[nam].setmCountPay(1);
        sYears[nam].setmSDate(SDate);

        sYears[nam].getmArrMonthPayment()[thang].setmCountPay(1);
        sYears[nam].getmArrMonthPayment()[thang].setmSDate(SDate);

        sYears[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay].setmCountPay(1);
        sYears[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay].setmSDate(SDate);
        sYears[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay].getmListPayment().add(payment);
        if (payment.getmType() == 39) {
            for (int i = 0; i < mListLoan.size(); i++) {
                if (mListLoan.get(i).getmSDate().comperTiem(payment.getmSDate()) <= 0) {
                    mListLoan.add(i, (SBL) payment);
                    mMoneyLoan += money;
                    return;
                }
            }
            mListLoan.add((SBL) payment);
            mMoneyLoan += money;
        }
        if (payment.getmType() == 6 && money > 0) {
            for (int i = 0; i < mListBorrow.size(); i++) {
                int d1 = mListBorrow.get(i).getmSDate().comperTiem(payment.getmSDate());
                if (d1 <= 0) {
                    mListBorrow.add(i, (SBL) payment);
                    mMoneyBorrow += money;
                    return;
                }
            }
            mListBorrow.add((SBL) payment);
            mMoneyBorrow += money;
        }

    }

    public ArrayList<SDMY> getAllDay() {
        ArrayList<SDMY> listDay = new ArrayList<>();
        int MN = getmMoney();
        for (int i = 30; i > 0; i--) {
            SYear yearsYear = sYears[i];
            if (yearsYear.getmCountPay() > 0) {
                for (int ii = 12; ii > 0; ii--) {
                    SMonth sMonth = yearsYear.getmArrMonthPayment()[ii];
                    if (sMonth.getmCountPay() > 0) {
                        SDay sDayx = new SDay();
                        sDayx.setmSDate(sMonth.getmSDate());
                        listDay.add(sDayx);
                        for (int iii = 31; iii > 0; iii--) {
                            SDay sDay = sMonth.getmArrSDay()[iii];
                            if (sDay.getmCountPay() > 0) {
                                sDay.setmViewType(MyValues.ITEM);
                                sDay.setmBalance(MN);
                                MN -= (sDay.getmMoneyIn() + sDay.getmMoneyOut());
                                listDay.add(sDay);
                            }
                        }
                    }
                }
            }
        }
        return listDay;
    }

    public ArrayList<SDMY> getAllMonth() {
        ArrayList<SDMY> listMonth = new ArrayList<>();
        int MN = this.mMoney;
        for (int i = 30; i > 0; i--) {
            SYear sYear = sYears[i];
            if (sYear.getmCountPay() > 0) {
                SMonth sMonth = new SMonth();
                sMonth.setmSDate(sYear.getmSDate());
                listMonth.add(sMonth);
                for (int ii = 12; ii > 0; ii--) {
                    SMonth monthPayment = sYear.getmArrMonthPayment()[ii];
                    if (monthPayment.getmCountPay() > 0) {
                        monthPayment.setmBalance(MN);
                        MN -= (monthPayment.getmMoneyIn() + monthPayment.getmMoneyOut());
                        monthPayment.setmViewType(1);
                        listMonth.add(monthPayment);
                    }
                }
            }
        }
        return listMonth;
    }

    public ArrayList<SDMY> getAllYear() {
        ArrayList<SDMY> listYear = new ArrayList<>();
        int MN = this.mMoney;
        for (int i = 30; i > 0; i--) {
            SYear sYear = sYears[i];
            if (sYear.getmCountPay() > 0) {
                listYear.add(sYear);
                sYear.setmBalance(MN);
                sYear.setmViewType(MyValues.ITEM);
                MN -= (sYear.getmMoneyIn() + sYear.getmMoneyOut());
            }
        }
        return listYear;
    }

    public SDay removePayment(Payment payment) {
        int ngay = payment.getmSDate().getmDay();
        int thang = payment.getmSDate().getmMonth();
        int nam = payment.getmSDate().getmYear() - 2015;
        int money = payment.getmMoney();

        SYear sYear = getsYears()[nam];
        SMonth sMonth = sYear.getmArrMonthPayment()[thang];
        SDay sDay = sMonth.getmArrSDay()[ngay];

        this.mMoney -= payment.getmMoney();
        if (money > 0) {
            sYear.setmMoneyIn(-money);
            sMonth.setmMoneyIn(-money);
            sDay.setmMoneyIn(-money);
        } else {
            sYear.setmMoneyOut(-money);
            sMonth.setmMoneyOut(-money);
            sDay.setmMoneyOut(-money);
        }
        sYear.setmCountPay(-1);
        sMonth.setmCountPay(-1);
        sDay.setmCountPay(-1);

        sDay.setmBalance(sDay.getmBalance() - money);
        sYear.setmBalance(sYear.getmBalance() - money);
        sMonth.setmBalance(sMonth.getmBalance() - money);

        sDay.getmListPayment().remove(payment);

        if (payment.getmType() == 39) {
            mMoneyLoan -= money;
            mListLoan.remove(payment);
        }
        if (payment.getmType() == 6) {
            mListBorrow.remove(payment);
            mMoneyBorrow -= money;
        }
        ;
        return sDay;
    }

    public int getmMoneyBorrow() {
        return mMoneyBorrow;
    }

    public void setmMoneyBorrow(int mMoneyBorrow) {
        this.mMoneyBorrow = mMoneyBorrow;
    }

    public int getmMoneyLoan() {
        return mMoneyLoan;
    }

    public void setmMoneyLoan(int mMoneyLoan) {
        this.mMoneyLoan = mMoneyLoan;
    }
}

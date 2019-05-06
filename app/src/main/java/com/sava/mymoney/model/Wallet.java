package com.sava.mymoney.model;

import com.sava.mymoney.common.MyValues;

import java.util.ArrayList;

public class Wallet {
    private int mCountPay;
    private int mMoney;
    private YearPayment[] mArrYearPayment;

    public Wallet() {
        this.mArrYearPayment = new YearPayment[31];
        for(int i =1;i<=30;i++)
            this.mArrYearPayment[i] = new YearPayment();
    }

    public int getmMoney() {
        return mMoney;
    }
    public YearPayment[] getmArrYearPayment() {
        return mArrYearPayment;
    }
    public void addPayment(Payment payment){
        int ngay = payment.getmTime().getmDay();
        int thang = payment.getmTime().getmMonth();
        int nam = payment.getmTime().getmYear()-2015;
        int money = payment.getmMoney();
        Time time = payment.getmTime();

        this.mMoney +=money;
        this.mCountPay++;

        mArrYearPayment[nam].setmMoney(money);
        mArrYearPayment[nam].setmCountPay(1);
        mArrYearPayment[nam].setmTime(time);

        mArrYearPayment[nam].getmArrMonthPayment()[thang].setmMoney(money);
        mArrYearPayment[nam].getmArrMonthPayment()[thang].setmCountPay(1);
        mArrYearPayment[nam].getmArrMonthPayment()[thang].setmTime(time);

        mArrYearPayment[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay].setmMoney(money);
        mArrYearPayment[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay].setmCountPay(1);
        mArrYearPayment[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay].setmTime(time);
        mArrYearPayment[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay].getmListPayment().add(payment);
    }


    public ArrayList<TimePayment> getAllThang(){
        ArrayList<TimePayment> listThang = new ArrayList<>();
        int MN = this.mMoney;
        for(int i =30;i>0;i--){
            YearPayment yearPayment = mArrYearPayment[i];
            if(yearPayment.getmCountPay()>0){
                MonthPayment monthPayment1 = new MonthPayment();
                monthPayment1.setmNote("Năm "+ yearPayment.getmTime().getmYear());
                listThang.add(monthPayment1);
                for(int ii=12;ii>0;ii--){
                    MonthPayment monthPayment = yearPayment.getmArrMonthPayment()[ii];
                    if(monthPayment.getmCountPay()>0){
                        monthPayment.setmBalance(MN);
                        MN -= monthPayment.getmMoney();
                        monthPayment.setmViewType(MyValues.ITEM);
                        listThang.add(monthPayment);
                    }
                }
            }
        }
        return  listThang;
    }


    public ArrayList<TimePayment> getAllNam(){
        ArrayList<TimePayment> listNam = new ArrayList<>();
        int MN = this.mMoney;
        for (int i = 30; i > 0; i--) {
            YearPayment yearPayment = mArrYearPayment[i];
            if (yearPayment.getmCountPay()>0) {
                listNam.add(yearPayment);
                yearPayment.setmBalance(MN);
                yearPayment.setmViewType(MyValues.ITEM);
                MN -= yearPayment.getmMoney();
            }
        }
        return listNam;
    }

    public ArrayList<TimePayment> getAllNgay(){
        ArrayList<TimePayment> listNgay = new ArrayList<>();
        int MN = getmMoney();
        for(int i = 30;i>0;i--){
            YearPayment yearPayment = mArrYearPayment[i];
            if(yearPayment.getmCountPay()>0){
                for(int ii =12;ii>0;ii--){
                    MonthPayment monthPayment = yearPayment.getmArrMonthPayment()[ii];
                    if(monthPayment.getmCountPay()>0){
                        DayPayment ngayx = new DayPayment();
                        ngayx.setmNote("Tháng "+ monthPayment.getmTime().getmMonth() + " năm "+ monthPayment.getmTime().getmYear());
                        listNgay.add(ngayx);
                        for(int iii=31;iii>0;iii--){
                            DayPayment dayPayment = monthPayment.getmArrDayPayment()[iii];
                            if(dayPayment.getmCountPay()>0){
                                dayPayment.setmViewType(MyValues.ITEM);
                                dayPayment.setmBalance(MN);
                                MN -= dayPayment.getmMoney();
                                listNgay.add(dayPayment);
                            }
                        }
                    }
                }
            }
        }
        return listNgay;
    }



    public DayPayment removePayment(Payment payment){
        int ngay = payment.getmTime().getmDay();
        int thang = payment.getmTime().getmMonth();
        int nam = payment.getmTime().getmYear()-2015;
        YearPayment yearPayment = getmArrYearPayment()[nam];
        MonthPayment monthPayment = yearPayment.getmArrMonthPayment()[thang];
        DayPayment dayPayment = monthPayment.getmArrDayPayment()[ngay];
        this.mMoney -=payment.getmMoney();
        yearPayment.setmMoney(-payment.getmMoney());
        yearPayment.setmCountPay(-1);
        monthPayment.setmMoney(-payment.getmMoney());
        monthPayment.setmCountPay(-1);
        dayPayment.setmMoney(-payment.getmMoney());
        dayPayment.setmCountPay(-1);
        dayPayment.setmBalance(dayPayment.getmBalance() - payment.getmMoney());
        yearPayment.setmBalance(yearPayment.getmBalance() - payment.getmMoney());
        monthPayment.setmBalance(monthPayment.getmBalance() - payment.getmMoney());
        dayPayment.getmListPayment().remove(payment);
        return dayPayment;
    }
}

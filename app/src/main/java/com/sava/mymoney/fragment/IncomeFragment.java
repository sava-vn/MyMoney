package com.sava.mymoney.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sava.mymoney.MainActivity;
import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.SDay;
import com.sava.mymoney.model.SMonth;
import com.sava.mymoney.model.Payment;

import java.util.ArrayList;


public class IncomeFragment extends Fragment {
    private PieChart pieChart_income;
    private ArrayList<PieEntry> listValue;
    private Bundle bundle;
    private int ngay;
    private int thang;
    private int nam;
    private int money;
    public IncomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        pieChart_income = view.findViewById(R.id.piechar_income);
        pieChart_income.setUsePercentValues(true);
        pieChart_income.setExtraOffsets(5, 10, 5, 5);
        pieChart_income.setDragDecelerationFrictionCoef(0.95f);
        pieChart_income.setDrawHoleEnabled(true);
        pieChart_income.setHoleColor(Color.WHITE);
        pieChart_income.setTransparentCircleRadius(30);
        pieChart_income.animateY(500, Easing.EaseInCubic);
    }

    public void initData() {
        listValue = new ArrayList<>();
        bundle = getArguments();
        ngay = bundle.getInt(MyValues.DAY);
        thang = bundle.getInt(MyValues.MONTH);
        nam = bundle.getInt(MyValues.YEAR) - 2015;
        money =0;
        if(bundle.getInt(MyValues.TYPE_SHOW)==MyValues.SHOW_DAYPAY)
            initValue1();
        if(bundle.getInt(MyValues.TYPE_SHOW)==MyValues.SHOW_MONTHPAY)
            initValue2();
        PieDataSet dataSet = new PieDataSet(listValue, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        Description description = new Description();
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), MyValues.FONT_V);
        description.setTextSize(18);
        description.setTypeface(typeface);
        description.setTextColor(Color.WHITE);
        if (listValue.size() > 0) {
            pieChart_income.setCenterText(MySupport.converToMoney(money));
            description.setText("Thống kê thu nhập");
        }
        else{
            description.setText("");
            pieChart_income.setCenterText("Không có khoản thu nào");
        }
        pieChart_income.setCenterTextTypeface(typeface);
        pieChart_income.setCenterTextSize(18);
        pieChart_income.getLegend().setTextColor(Color.WHITE);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13);
        data.setValueTextColor(Color.BLACK);

        pieChart_income.setData(data);
        pieChart_income.setDescription(description);
    }
    public void initValue1(){
        int[] Money = new int[20];
        SDay SDay = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay];
        ArrayList<Payment> listPayment = SDay.getmListPayment();
        for (Payment payment : listPayment) {
            if (payment.getmMoney() > 0) {
                int type = payment.getmType();
                Money[type]+=payment.getmMoney();
            }
        }
        for(int i =0;i<20;i++){
            if(Money[i]>0){
                listValue.add(new PieEntry(Money[i],MainActivity.TYPE_INCOMES[i]));
                money+=Money[i];
            }
        }
    }
    public void initValue2(){
        int[] Money = new int[20];
        SMonth monthPayment = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang];
        for(int i =31;i>0;i--){
            SDay SDay = monthPayment.getmArrSDay()[i];
            if(SDay !=null){
                for (Payment payment : SDay.getmListPayment()) {
                    if (payment.getmMoney() > 0) {
                        int type = payment.getmType();
                        Money[type]+=payment.getmMoney();
                    }
                }
            }
        }
        for(int i =0;i<20;i++){
            if(Money[i]>0){
                listValue.add(new PieEntry(Money[i],MainActivity.TYPE_INCOMES[i]));
                money+=Money[i];
            }
        }
    }
}

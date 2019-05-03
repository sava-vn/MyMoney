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
import com.sava.mymoney.model.DayPayment;
import com.sava.mymoney.model.MonthPayment;
import com.sava.mymoney.model.Payment;

import java.util.ArrayList;


public class ExpenditureFragment extends Fragment {
    private PieChart pieChart_expenditure;
    private ArrayList<PieEntry> listValue;
    private Bundle bundle;
    private int ngay;
    private int thang;
    private int nam;
    private int money;
    public ExpenditureFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        initView(view);
        initData();
        return view;
    }
    public void initView(View view) {
        pieChart_expenditure = view.findViewById(R.id.piechar_expenditure);
        pieChart_expenditure.setUsePercentValues(true);
        pieChart_expenditure.setExtraOffsets(5, 10, 5, 5);
        pieChart_expenditure.setDragDecelerationFrictionCoef(0.95f);
        pieChart_expenditure.setDrawHoleEnabled(true);
        pieChart_expenditure.setHoleColor(Color.WHITE);
        pieChart_expenditure.setTransparentCircleRadius(30);
        pieChart_expenditure.animateY(500, Easing.EaseInCubic);
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
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        Description description = new Description();
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),MyValues.FONT_V);
        description.setTextSize(18);
        description.setTypeface(typeface);
        description.setTextColor(Color.WHITE);
        if (listValue.size() > 0) {
            pieChart_expenditure.setCenterText(MySupport.converToMoney(money));
            description.setText("Thống kê chi tiêu");
        }
        else{
            description.setText("");
            pieChart_expenditure.setCenterText("Không có khoản chi nào");
        }
        pieChart_expenditure.setCenterTextTypeface(typeface);
        pieChart_expenditure.setCenterTextSize(18);
        pieChart_expenditure.getLegend().setTextColor(Color.WHITE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13);
        data.setValueTextColor(Color.BLACK);

        pieChart_expenditure.setData(data);
        pieChart_expenditure.setDescription(description);
    }
    public void initValue1(){
        int[] Money = new int[60];
        DayPayment dayPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay];
        ArrayList<Payment> listPayment = dayPayment.getmListPayment();
        for (Payment payment : listPayment) {
            if (payment.getmMoney() < 0) {
                int type = payment.getmType();
                Money[type]-=payment.getmMoney();
            }
        }
        for(int i =0;i<60;i++){
            if(Money[i]>0){
                listValue.add(new PieEntry(Money[i],MainActivity.TYPE_EXPENDITURES[i]));
                money+=Money[i];
            }
        }

    }
    public void initValue2(){
        int[] Money = new int[60];
        MonthPayment monthPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang];
        for(int i =31;i>0;i--){
            DayPayment dayPayment = monthPayment.getmArrDayPayment()[i];
            if(dayPayment!=null){
                for (Payment payment : dayPayment.getmListPayment()) {
                    if (payment.getmMoney() < 0) {
                        int type = payment.getmType();
                        int typeParent= MainActivity.TYPE_PARENT_INT[type];
                        Money[typeParent]-=payment.getmMoney();
                    }
                }
            }
        }
        for(int i =0;i<60;i++){
            if(Money[i]>0){
                listValue.add(new PieEntry(Money[i],MainActivity.TYPE_PARENT_STRING[i]));
                money+=Money[i];
            }
        }
    }

}

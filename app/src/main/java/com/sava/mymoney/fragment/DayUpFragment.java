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
import com.github.mikephil.charting.data.Entry;
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
import com.sava.mymoney.model.Payment;

import java.util.ArrayList;


public class DayUpFragment extends Fragment {
    private PieChart pieChart_income;
    private ArrayList<PieEntry> listValue;
    private Bundle bundle;

    public DayUpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_up, container, false);
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
        int money = 0;
        listValue = new ArrayList<>();
        bundle = getArguments();
        int ngay = bundle.getInt(MyValues.DAY);
        int thang = bundle.getInt(MyValues.MONTH);
        int nam = bundle.getInt(MyValues.YEAR) - 2015;
        DayPayment dayPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay];
        ArrayList<Payment> listPayment = dayPayment.getmListPayment();
        for (Payment payment : listPayment) {
            if (payment.getmMoney() > 0) {
                PieEntry pieEntry = new PieEntry(payment.getmMoney(), MainActivity.TYPE_INCOMES[payment.getmType()]);
                listValue.add(pieEntry);
                money += payment.getmMoney();
            }
        }
        PieDataSet dataSet = new PieDataSet(listValue, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        Description description = new Description();
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), MyValues.FONT_AGENCY);
        description.setTextSize(18);
        description.setTypeface(typeface);

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

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13);
        data.setValueTextColor(Color.BLACK);

        pieChart_income.setData(data);
        pieChart_income.setDescription(description);
    }

}

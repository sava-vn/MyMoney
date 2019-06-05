package com.sava.mymoney.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sava.mymoney.DetailIncomeActivity;
import com.sava.mymoney.DetailTypeActivity;
import com.sava.mymoney.MainActivity;
import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.SDate;
import com.sava.mymoney.model.SDay;
import com.sava.mymoney.model.SMonth;
import com.sava.mymoney.model.Payment;

import java.util.ArrayList;


public class IncomeFragment extends Fragment {
    private static final int DAY = 1;
    private static final int MONTH = 2;
    private PieChart pieChart_income;
    private ArrayList<PieEntry> listValue;
    private Bundle bundle;
    private int ngay;
    private int thang;
    private int nam;
    private int money;
    private int dayOrMonth;
    private ArrayList<Integer> listType;
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
        listType = new ArrayList<>();
        bundle = getArguments();
        dayOrMonth = bundle.getInt(MyValues.TYPE_SHOW);
        ngay = bundle.getInt(MyValues.DAY);
        thang = bundle.getInt(MyValues.MONTH);
        nam = bundle.getInt(MyValues.YEAR) - 2015;
        final SDate sDate = new SDate(ngay, thang, nam, 0);
        money = 0;
        if (dayOrMonth == DAY)
            initValue1();
        if (dayOrMonth == MONTH)
            initValue2();

        PieDataSet dataSet = new PieDataSet(listValue, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13);
        data.setValueTextColor(Color.BLACK);

        Description description = new Description();
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), MyValues.FONT_V);
        description.setTextSize(18);
        description.setTypeface(typeface);
        description.setTextColor(Color.WHITE);

        if (listValue.size() > 0) {
            pieChart_income.setCenterText(MySupport.converToMoney(money));
            description.setText("Thống kê thu nhập (%)");
        } else {
            description.setText("");
            pieChart_income.setCenterText("Không có khoản thu nào");
        }
        pieChart_income.setCenterTextTypeface(typeface);
        pieChart_income.setCenterTextSize(18);
        pieChart_income.getLegend().setTextColor(Color.WHITE);

        pieChart_income.setData(data);
        pieChart_income.setDescription(description);
        pieChart_income.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, final Highlight h) {
                PieEntry pieEntry = (PieEntry) e;
                final Dialog dialog = new Dialog(getContext());
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.dialog_detail);

                TextView tvDate = dialog.findViewById(R.id.d_detial_tv_date);
                TextView tvTitle = dialog.findViewById(R.id.d_detial_tv_title);
                TextView tvMoney = dialog.findViewById(R.id.d_detial_tv_money);
                Button btnOK = dialog.findViewById(R.id.d_detial_btn_ok);
                Button btnDetail = dialog.findViewById(R.id.d_detial_btn_detial);
                tvTitle.setText("Số tiền thu về từ " + pieEntry.getLabel());
                if (dayOrMonth == DAY)
                    tvDate.setText(sDate.showDay());
                else
                    tvDate.setText(sDate.showMonth());

                tvMoney.setText(MySupport.converToMoney((int) pieEntry.getValue()));
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), DetailIncomeActivity.class);
                        intent.putExtra("NGAY",ngay);
                        intent.putExtra("THANG",thang);
                        intent.putExtra("NAM",nam);
                        intent.putExtra("DAYORMONTH",dayOrMonth);
                        intent.putExtra("TYPE",listType.get((int)h.getX()));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
                InsetDrawable insetDrawable = new InsetDrawable(colorDrawable, 30, 50, 30, 50);
                dialog.getWindow().setBackgroundDrawable(insetDrawable);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    public void initValue1() {
        int[] Money = new int[20];
        listType.clear();
        SDay SDay = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay];
        ArrayList<Payment> listPayment = SDay.getmListPayment();
        for (Payment payment : listPayment) {
            if (payment.getmMoney() > 0) {
                int type = payment.getmType();
                Money[type] += payment.getmMoney();
            }
        }
        for (int i = 0; i < 20; i++) {
            if (Money[i] > 0) {
                listValue.add(new PieEntry(Money[i], MainActivity.TYPE_INCOMES[i]));
                money += Money[i];
                listType.add(i);
            }
        }
    }

    public void initValue2() {
        int[] Money = new int[20];
        listType.clear();
        SMonth monthPayment = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang];
        for (int i = 31; i > 0; i--) {
            SDay SDay = monthPayment.getmArrSDay()[i];
            if (SDay != null) {
                for (Payment payment : SDay.getmListPayment()) {
                    if (payment.getmMoney() > 0) {
                        int type = payment.getmType();
                        Money[type] += payment.getmMoney();
                    }
                }
            }
        }
        for (int i = 0; i < 20; i++) {
            if (Money[i] > 0) {
                listValue.add(new PieEntry(Money[i], MainActivity.TYPE_INCOMES[i]));
                money += Money[i];
                listType.add(i);
            }
        }
    }
}

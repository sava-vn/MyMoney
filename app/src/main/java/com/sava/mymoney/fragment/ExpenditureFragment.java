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
import android.widget.Toast;

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
import com.sava.mymoney.DetailTypeActivity;
import com.sava.mymoney.MainActivity;
import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.SDate;
import com.sava.mymoney.model.SDay;
import com.sava.mymoney.model.SMonth;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.SYear;

import java.util.ArrayList;
import java.util.Date;


public class ExpenditureFragment extends Fragment {
    private static final int DAY = 1;
    private static final int MONTH = 2;
    private static final int YEAR = 3;
    private PieChart pieChart_expenditure;
    private ArrayList<PieEntry> listValue;
    private ArrayList<Integer> listType;
    private Bundle bundle;
    private int ngay;
    private int thang;
    private int nam;
    private int money;
    private int dayOrMonth;

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
        listType = new ArrayList<>();
        bundle = getArguments();
        ngay = bundle.getInt(MyValues.DAY);
        thang = bundle.getInt(MyValues.MONTH);
        nam = bundle.getInt(MyValues.YEAR) - 2015;
        final SDate sDate = new SDate(ngay, thang, nam + 2015, 0);
        money = 0;
        dayOrMonth = bundle.getInt(MyValues.TYPE_SHOW);

        if (dayOrMonth == DAY)
            initValue1();
        if (dayOrMonth == MONTH)
            initValue2();
        if (dayOrMonth == YEAR)
            initValue3();

        Description description = new Description();
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), MyValues.FONT_V);
        description.setTextSize(18);
        description.setTypeface(typeface);
        description.setTextColor(Color.WHITE);

        pieChart_expenditure.setCenterTextTypeface(typeface);
        pieChart_expenditure.setCenterTextSize(18);
        pieChart_expenditure.getLegend().setTextColor(Color.WHITE);

        if (listValue.size() > 0) {
            pieChart_expenditure.setCenterText(MySupport.converToMoney(money));
            description.setText("Thống kê chi tiêu (%)");
        } else {
            description.setText("");
            pieChart_expenditure.setCenterText("Không có khoản chi nào");
        }

        PieDataSet dataSet = new PieDataSet(listValue, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13);
        data.setValueTextColor(Color.BLACK);

        pieChart_expenditure.setData(data);
        pieChart_expenditure.setDescription(description);
        pieChart_expenditure.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
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
                tvTitle.setText("Số tiền dành cho " + pieEntry.getLabel());
                if (dayOrMonth == DAY)
                    tvDate.setText(sDate.showDay());
                if (dayOrMonth == MONTH)
                    tvDate.setText(sDate.showMonth());
                if (dayOrMonth == YEAR)
                    tvDate.setText(sDate.getmYear() + "");
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
                        Intent intent = new Intent(getContext(), DetailTypeActivity.class);
                        intent.putExtra("NGAY", ngay);
                        intent.putExtra("THANG", thang);
                        intent.putExtra("NAM", nam);
                        intent.putExtra("DAYORMONTH", dayOrMonth);
                        intent.putExtra("TYPE", listType.get((int) h.getX()));
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
        listType.clear();
        int[] Money = new int[60];
        SDay SDay = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay];
        ArrayList<Payment> listPayment = SDay.getmListPayment();
        for (Payment payment : listPayment) {
            if (payment.getmMoney() < 0) {
                int type = payment.getmType();
                Money[type] -= payment.getmMoney();
            }
        }
        for (int i = 0; i < 60; i++) {
            if (Money[i] > 0) {
                listValue.add(new PieEntry(Money[i], MainActivity.TYPE_EXPENDITURES[i]));
                money += Money[i];
                listType.add(i);
            }
        }
    }

    public void initValue2() {
        listType.clear();
        int[] Money = new int[60];
        SMonth monthPayment = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang];
        for (int i = 31; i > 0; i--) {
            SDay SDay = monthPayment.getmArrSDay()[i];
            if (SDay != null) {
                for (Payment payment : SDay.getmListPayment()) {
                    if (payment.getmMoney() < 0) {
                        int type = payment.getmType();
                        int typeParent = MainActivity.TYPE_PARENT_INT[type];
                        Money[typeParent] -= payment.getmMoney();
                    }
                }
            }
        }
        for (int i = 0; i < 60; i++) {
            if (Money[i] > 0) {
                listValue.add(new PieEntry(Money[i], MainActivity.TYPE_PARENT_STRING[i]));
                money += Money[i];
                listType.add(i);
            }
        }
    }

    public void initValue3() {
        int[] Money = new int[60];
        listType.clear();
        SYear sYear = MainActivity.mWallet.getsYears()[nam];
        for (int i = 12; i > 0; i--) {
            SMonth sMonth = sYear.getmArrMonthPayment()[i];
            if (sMonth.getmMoneyOut() < 0) {
                for (int ii = 31; ii > 0; ii--) {
                    SDay sDay = sMonth.getmArrSDay()[ii];
                    if (sDay.getmMoneyOut() < 0) {
                        for (Payment payment : sDay.getmListPayment()) {
                            if (payment.getmMoney() < 0) {
                                    int type = payment.getmType();
                                    int typeParent = MainActivity.TYPE_PARENT_INT[type];
                                    Money[typeParent] -= payment.getmMoney();
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 60; i++) {
            if (Money[i] > 0) {
                listValue.add(new PieEntry(Money[i], MainActivity.TYPE_PARENT_STRING[i]));
                money += Money[i];
                listType.add(i);
            }
        }
    }
}

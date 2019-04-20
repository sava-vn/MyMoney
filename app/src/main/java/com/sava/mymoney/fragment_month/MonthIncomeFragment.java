package com.sava.mymoney.fragment_month;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sava.mymoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthIncomeFragment extends Fragment {


    public MonthIncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month_income, container, false);
    }

}

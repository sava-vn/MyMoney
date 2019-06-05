package com.sava.mymoney.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sava.mymoney.DetailActivity;
import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.MainActivity;
import com.sava.mymoney.R;
import com.sava.mymoney.adapter.PaymentAdapter;
import com.sava.mymoney.adapter.SDMYAdpter;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.SBL;
import com.sava.mymoney.model.SDate;
import com.sava.mymoney.model.SDay;
import com.sava.mymoney.model.SMonth;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.SDMY;
import com.sava.mymoney.model.SYear;
import com.sava.mymoney.swipe.RecyclerItemTouchHelper;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private TextView tvBalance;
    private TextView tvMoney;
    private RecyclerView mRecyclerView;
    private int ngay;
    private int thang;
    private int nam;
    private PaymentAdapter mPaymentAdapter;
    private ArrayList<Payment> mListPayments;
    private ArrayList<SDMY> mListDayPayments;
    private SDMYAdpter mSDMYAdpter;
    private Bundle bundle;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mData;
    private CoordinatorLayout mCodl;
    private SBL pSbl;
    private int A, B;
    private NestedScrollView nestHome;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_DAYPAY)
            initData1();
        if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_MONTHPAY)
            initData2();
        if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_YEARPAY)
            initData3();
        return view;
    }

    public void initView(View view) {
        tvBalance = view.findViewById(R.id.tvSodu_fragHome);
        tvMoney = view.findViewById(R.id.tv_thuChi_fragHome);
        mRecyclerView = view.findViewById(R.id.rcv_itempayment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mCodl = getActivity().findViewById(R.id.codl);
        nestHome = view.findViewById(R.id.nest_home);
    }

    public void initData() {
        bundle = getArguments();
        ngay = bundle.getInt(MyValues.DAY);
        thang = bundle.getInt(MyValues.MONTH);
        nam = bundle.getInt(MyValues.YEAR) - 2015;
        mListDayPayments = null;
        mListPayments = null;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference().child(mUser.getUid());
    }

    public void initData1() {
        SDay sDay = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay];
        mListPayments = new ArrayList<>();
        mListPayments.addAll(sDay.getmListPayment());
        tvBalance.setText(MySupport.converToMoney(sDay.getmBalance()));
        tvMoney.setText(MySupport.converToMoney(sDay.getmMoneyIn() + sDay.getmMoneyOut()));
        mPaymentAdapter = new PaymentAdapter(getContext(), mListPayments);
        mRecyclerView.setAdapter(mPaymentAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
    }

    public void initData3() {
        SYear sYear = MainActivity.mWallet.getsYears()[nam];
        mListDayPayments = new ArrayList<>();
        int BLANCE = MainActivity.mWallet.getmMoney();
        sYear.setmBalance(BLANCE);
        for (int i = 12; i > 0; i--) {
            SMonth sMonth = sYear.getmArrMonthPayment()[i];
            if (sMonth.getmCountPay() > 0) {
                sMonth.setmViewType(1);
                sMonth.setmBalance(BLANCE);
                mListDayPayments.add(sMonth);
                BLANCE -= (sMonth.getmMoneyIn() + sMonth.getmMoneyOut());
            }
        }
        tvBalance.setText(MySupport.converToMoney(sYear.getmBalance()));
        tvMoney.setText(MySupport.converToMoney(sYear.getmMoneyIn() + sYear.getmMoneyOut()));
        mSDMYAdpter = new SDMYAdpter(getContext(), mListDayPayments, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SDMY SDMY = mListDayPayments.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(MyValues.DAY, SDMY.getmSDate().getmDay());
                bundle.putInt(MyValues.MONTH, SDMY.getmSDate().getmMonth());
                bundle.putInt(MyValues.YEAR, SDMY.getmSDate().getmYear());
                bundle.putInt(MyValues.TYPE_SHOW, 2);
                intent.putExtra(MyValues.BUNDLEDAY, bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mSDMYAdpter);
        mRecyclerView.setNestedScrollingEnabled(false);
        nestHome.getParent().requestChildFocus(nestHome, nestHome);
    }

    public void initData2() {
        SMonth sMonth = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang];
        mListDayPayments = new ArrayList<>();
        for (int i = 31; i > 0; i--) {
            if (sMonth.getmArrSDay()[i].getmCountPay() > 0)
                mListDayPayments.add(sMonth.getmArrSDay()[i]);
        }
        tvBalance.setText(MySupport.converToMoney(sMonth.getmBalance()));
        tvMoney.setText(MySupport.converToMoney(sMonth.getmMoneyIn() + sMonth.getmMoneyOut()));
        mSDMYAdpter = new SDMYAdpter(getContext(), mListDayPayments, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SDMY SDMY = mListDayPayments.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(MyValues.DAY, SDMY.getmSDate().getmDay());
                bundle.putInt(MyValues.MONTH, SDMY.getmSDate().getmMonth());
                bundle.putInt(MyValues.YEAR, SDMY.getmSDate().getmYear());
                bundle.putInt(MyValues.TYPE_SHOW, 1);
                intent.putExtra(MyValues.BUNDLEDAY, bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mSDMYAdpter);
        mRecyclerView.setNestedScrollingEnabled(false);
        nestHome.getParent().requestChildFocus(nestHome, nestHome);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        pSbl = null;

        final Payment payment = mListPayments.get(position);

        SDate date = payment.getmSDate();
        SDay day = MainActivity.mWallet.getsYears()[date.getmYear() - 2015].getmArrMonthPayment()[date.getmMonth()].getmArrSDay()[date.getmDay()];
        A = day.getmMoneyIn() + day.getmMoneyOut() - payment.getmMoney();
        B = day.getmBalance() - payment.getmMoney();
        if (payment.getmType() == 7 || payment.getmType() == 38) {
            SBL sbl = (SBL) payment;
            SDate sDate = sbl.getmSDate2();

            ArrayList<Payment> payments = MainActivity.mWallet.getsYears()[sDate.getmYear() - 2015].getmArrMonthPayment()[sDate.getmMonth()].getmArrSDay()[sDate.getmDay()].getmListPayment();

            for (Payment pay : payments) {
                if (pay.getmIdPayment().equals(sbl.getmPerson())) {
                    pSbl = (SBL) pay;
                    pSbl.setIsPayment(0);
                    mData.child(pSbl.getmIdPayment()).setValue(pSbl);
                    break;
                }
            }
        }

        mData.child(payment.getmIdPayment()).removeValue();

        SDay sDay = MainActivity.mWallet.removePayment(payment);

        mListPayments.remove(payment);
        mPaymentAdapter.notifyDataSetChanged();

        tvMoney.setText(MySupport.converToMoney(A));
        tvBalance.setText(MySupport.converToMoney(B));

        Snackbar snackbar = Snackbar.make(mCodl, "Bạn vừa xóa " + payment.getmNote(), 5000);
        snackbar.setAction("Hoàn tác", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPayment = mData.push().getKey();
                payment.setmIdPayment(idPayment);

                if (pSbl != null) {
                    pSbl.setIsPayment(1);
                    mData.child(pSbl.getmIdPayment()).setValue(pSbl);
                }
                mData.child(idPayment).setValue(payment);
                MainActivity.mWallet.addPayment(payment);
                mListPayments.add(payment);
                mPaymentAdapter.notifyDataSetChanged();
                A += payment.getmMoney();
                B += payment.getmMoney();
                tvMoney.setText(MySupport.converToMoney(A));
                tvBalance.setText(MySupport.converToMoney(B));
            }
        });
        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_MONTHPAY && mListDayPayments != null) {
            SMonth sMonth = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang];
            mListDayPayments.clear();
            for (int i = 31; i > 0; i--) {
                if (sMonth.getmArrSDay()[i].getmCountPay() > 0)
                    mListDayPayments.add(sMonth.getmArrSDay()[i]);
            }
            tvBalance.setText(MySupport.converToMoney(sMonth.getmBalance()));
            tvMoney.setText(MySupport.converToMoney(sMonth.getmMoneyIn() + sMonth.getmMoneyOut()));
            mSDMYAdpter.notifyDataSetChanged();
        }
    }
}

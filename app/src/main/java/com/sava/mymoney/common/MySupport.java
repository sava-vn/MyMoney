package com.sava.mymoney.common;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class MySupport {
    public static void setFontRegular(Context context, TextView textView, String font){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),font);
        textView.setTypeface(typeface);
    }
    public static void setFontBold(Context context,TextView textView, String font){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),font);
        textView.setTypeface(typeface,Typeface.BOLD);
    }
    public static String converToMoney(int x) {
        String sx = String.valueOf(x);
        String money = "";
        money += sx.charAt(0);
        for (int i = 1; i < sx.length(); i++) {
            if ((sx.length() - i) % 3 == 0 && (i > 1 || sx.charAt(0) != '-'))
                money += ",";
            money += sx.charAt(i);
        }
        return money;
    }
    public static int StringToMoney(String sMoney){
        int money =0;
        String newMoney ="";
        for(int i =0;i<sMoney.length();i++)
            if(sMoney.charAt(i)!=',')
                newMoney+=sMoney.charAt(i);
        money = Integer.parseInt(newMoney.trim());
        return money;
    }
}

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
}

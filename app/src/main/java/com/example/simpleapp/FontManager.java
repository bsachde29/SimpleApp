package com.example.simpleapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontManager {

    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fa-solid-900.ttf";

    public static void markAsIconContainer(View v, Typeface typeface) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child, typeface);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTypeface(typeface);
        }
    }

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }



}
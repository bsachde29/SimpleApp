package com.example.simpleapp;

import android.content.*;
import android.preference.*;

public class SaveSharedPreference
{
    static final String PREF_BUYER_ID= "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPrefBuyerId(Context ctx, String buyerID)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_BUYER_ID, buyerID);
        editor.apply();
    }

    public static String getPrefBuyerId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_BUYER_ID, "");
    }
}
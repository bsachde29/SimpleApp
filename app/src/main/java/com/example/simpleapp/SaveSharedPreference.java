package com.example.simpleapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_BUYER_ID = "";
    static final String PREF_CART_ID = "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPrefBuyerId(Context ctx, String buyerID) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_BUYER_ID, buyerID);
        editor.apply();
    }

    public static void setPrefCartId(Context ctx, String cartID) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_CART_ID, cartID);
        editor.apply();
    }


    public static String getPrefBuyerId(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_BUYER_ID, "1");
    }

    public static String getPrefCartId(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_CART_ID, "1");
    }


}
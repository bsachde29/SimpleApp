package com.example.simpleapp;

import android.content.*;
import android.preference.*;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SaveSharedPreference
{
    static final String PREF_BUYER_ID = "";
    static final String PREF_CART_ID = "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPrefBuyerId(Context ctx, String buyerID)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_BUYER_ID, buyerID);
        editor.apply();
    }

    public static void setPrefCartId(Context ctx, String cartID)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_CART_ID, cartID);
        editor.apply();
    }



    public static String getPrefBuyerId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_BUYER_ID, "");
    }
    public static String getPrefCartId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_CART_ID, "");
    }


}
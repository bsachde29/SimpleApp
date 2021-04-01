package com.example.simpleapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CartCell {

    TableRow tableRow;
    LinearLayout main_V, imgDeets_H, Deets_V, Buttons_H, qty_H;
    ImageView imageView;
    TextView title, desc, price, qty;
    Button qty_inc, qty_dec, delete;


    CartCell(Context context,  String Title, String Desc, String Price, Drawable d, String Qty) {
        qty = new TextView(context);
        qty.setText(Qty);

    }

}

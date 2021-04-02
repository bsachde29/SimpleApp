package com.example.simpleapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CartCell {

    TableRow tableRow;
    LinearLayout main_V, imgDeets_H, Deets_V, Buttons_H, qty_H;
    ImageView imageView;
    TextView title, price, qty;
    Button qty_inc, qty_dec, delete;


    CartCell(Context context,  String Title,  String Price, Drawable d, String Qty) {
        qty = new TextView(context);
        qty_inc = new Button(context);
        qty_dec = new Button(context);

        qty.setText(Qty);
        qty_inc.setText("+");
        qty_dec.setText("-");

//        ViewGroup.LayoutParams layoutParams = qty_inc.getLayoutParams();
//        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//
//        ViewGroup.LayoutParams layoutParams2 = qty_dec.getLayoutParams();
//        layoutParams2.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        qty_H = new LinearLayout(context);
        qty_H.setOrientation(LinearLayout.HORIZONTAL);
        qty_H.addView(qty_inc,0);
        qty_H.addView(qty, 1);
        qty_H.addView(qty_dec,2);

        delete = new Button(context);
        delete.setText("Delete");



        Buttons_H = new LinearLayout(context);
        Buttons_H.setOrientation(LinearLayout.HORIZONTAL);
        Buttons_H.addView(qty_H, 0);
        Buttons_H.addView(delete, 1);

        title=  new TextView(context);
        price = new TextView(context);
        title.setText(Title);
        price.setText("$ " + Price);
        price.setTextColor(Color.parseColor("#00b300"));

        Deets_V = new LinearLayout(context);
        Deets_V.setOrientation(LinearLayout.VERTICAL);
        Deets_V.addView(title,0);
        Deets_V.addView(price, 1);

        imageView = new ImageView(context);
        imageView.setImageDrawable(d);

        imgDeets_H = new LinearLayout(context);
        imgDeets_H.setOrientation(LinearLayout.HORIZONTAL);

        imgDeets_H.addView(imageView, 0);
        imgDeets_H.addView(Deets_V, 1);

        main_V = new LinearLayout(context);
        main_V.setOrientation(LinearLayout.VERTICAL);

        main_V.addView(imgDeets_H, 0);
        main_V.addView(Buttons_H, 1);

        tableRow = new TableRow(context);
        tableRow.addView(main_V);

    }

}

package com.example.simpleapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class OrderDetailsCell {

    TableRow tableRow;
    LinearLayout linearLayoutH, linearLayoutV, price_qty;
    ImageView imageView;
    TextView title, desc, price, qty;

    OrderDetailsCell(Context ctx, String Title, String Desc, String Price, String Qty, Drawable d) {
        tableRow = new TableRow(ctx);
        linearLayoutH = new LinearLayout(ctx);
        linearLayoutV = new LinearLayout(ctx);
        title = new TextView(ctx);
        desc = new TextView(ctx);
        price = new TextView(ctx);
        qty = new TextView(ctx);
        imageView = new ImageView(ctx);
        title.setText(Title);
        price.setText("$ " + Price);
        qty.setText("Qty: " + Qty);
        price_qty = new LinearLayout(ctx);
        price_qty.setOrientation(LinearLayout.VERTICAL);
        desc.setText(Desc);
        desc.setMaxWidth(400);
        imageView.setImageDrawable(d);




        linearLayoutV.setMinimumWidth(700);
        setMargins((ViewGroup) tableRow, 10, 20, 8, 8);
        tableRow.setBackgroundColor(Color.parseColor("#ffffff"));
        tableRow.setPadding(3, 30, 3, 30);
        desc.setPadding(5, 30, 5, 0);

        linearLayoutH.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutV.setOrientation(LinearLayout.VERTICAL);
        linearLayoutH.setMinimumHeight(100);
        linearLayoutH.addView(imageView);
        linearLayoutV.addView(title, 0);
        linearLayoutV.addView(desc, 1);
        price_qty.addView(price, 0);
        price_qty.addView(qty, 1);
        tableRow.addView(linearLayoutH);
        tableRow.addView(linearLayoutV);

        tableRow.addView(price_qty);


    }


    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}


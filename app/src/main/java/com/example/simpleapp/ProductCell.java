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


public class ProductCell {

    TableRow tableRow;
    LinearLayout linearLayoutH, linearLayoutV;
    ImageView imageView;
    TextView title, desc, price;

    ProductCell(Context ctx, String Title, String Desc, String Price, Drawable d) {
        tableRow = new TableRow(ctx);
        linearLayoutH = new LinearLayout(ctx);
        linearLayoutV = new LinearLayout(ctx);
        title = new TextView(ctx);
        desc = new TextView(ctx);
        price = new TextView(ctx);
        imageView = new ImageView(ctx);
        title.setText(Title);
        price.setText("$ " + Price);
        desc.setText(Desc);
        desc.setMaxWidth(700);
        imageView.setImageDrawable(d);

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
        tableRow.addView(linearLayoutH);
        tableRow.addView(linearLayoutV);
        tableRow.addView(price);

    }

    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


}


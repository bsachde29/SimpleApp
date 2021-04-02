package com.example.simpleapp;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class OrderCell {

    TableRow tableRow;
    LinearLayout ID_Total_H, main_V;
    TextView orderID, total, status;


    OrderCell(Context context, String OrderID, String Total, String Status) {
        orderID = new TextView(context);
        orderID.setText(OrderID);
        total = new TextView(context);
        total.setText(Total);
        status = new TextView(context);
        status.setText(Status);
        main_V = new LinearLayout(context);
        main_V.setOrientation(LinearLayout.VERTICAL);
        ID_Total_H = new LinearLayout(context);
        ID_Total_H.setOrientation(LinearLayout.HORIZONTAL);
        ID_Total_H.addView(orderID, 0);
        ID_Total_H.addView(total, 1);
        main_V.addView(ID_Total_H, 0);
        main_V.addView(status, 1);
        tableRow = new TableRow(context);
        tableRow.addView(main_V);
    }


}

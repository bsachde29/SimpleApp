package com.example.simpleapp;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

public class NotificationCell {

    TableRow tableRow;
    TextView textView;



    NotificationCell(Context context, String notification) {
        textView = new TextView(context);
        textView.setText(notification);
        tableRow = new TableRow(context);
        tableRow.addView(textView);
    }



}

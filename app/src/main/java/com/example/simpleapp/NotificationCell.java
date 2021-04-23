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

        textView.setMinHeight(300);
        textView.setMaxWidth(1000);



        textView.setLines(2);
        tableRow = new TableRow(context);
        tableRow.setGravity(16);
        tableRow.setMinimumHeight(300);
        tableRow.addView(textView);
    }



}

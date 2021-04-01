package com.example.simpleapp;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableRow;

public class RowDivider {

    TableRow tableRow;

    RowDivider(Context context) {
        tableRow = new TableRow(context);
        tableRow.setMinimumHeight(8);
        tableRow.setBackgroundColor(Color.parseColor("#cccccc"));
    }
}

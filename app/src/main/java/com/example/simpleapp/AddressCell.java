package com.example.simpleapp;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AddressCell {

    TableRow tableRow;
    TextView street_lb, city_lb, state_lb, zip_lb, street, city, state, zip;
    Button delete;
    LinearLayout Main_v;


    AddressCell(Context context, String Street, String City, String State, String Zip) {
        street_lb = new TextView(context);
        street_lb.setText("Street Address: ");
        street = new TextView(context);
        street.setText(Street);
        city_lb = new TextView(context);
        city_lb.setText("City: ");
        city = new TextView(context);
        city.setText(City);
        state_lb = new TextView(context);
        state_lb.setText("State: ");
        state = new TextView(context);
        state.setText(State);
        zip_lb = new TextView(context);
        zip_lb.setText("Zip: ");
        zip = new TextView(context);
        zip.setText(Zip);

        delete = new Button(context);
        delete.setText("Delete");

        Main_v = new LinearLayout(context);
        Main_v.setOrientation(LinearLayout.VERTICAL);

        Main_v.addView(street_lb, 0);
        Main_v.addView(street,1);
        Main_v.addView(city_lb, 2);
        Main_v.addView(city, 3);
        Main_v.addView(state_lb, 4);
        Main_v.addView(state, 5);
        Main_v.addView(zip_lb, 6);
        Main_v.addView(zip, 7);
        Main_v.addView(delete, 8);

        tableRow = new TableRow(context);

        tableRow.addView(Main_v);


    }

}

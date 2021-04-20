package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import java.io.IOException;
import java.io.InputStream;

public class SearchActivity2 extends AppCompatActivity {

    TableLayout tableLayout;
    Button searchButton;
    EditText keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        keyword = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);
        tableLayout = findViewById(R.id.searchTable);


        try {
            InputStream ims = getAssets().open("prod_holder.jpg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            ProductCell cell = null;
            cell = new ProductCell(getApplicationContext(), "name", "desc", "price", d);
            tableLayout.addView(cell.tableRow);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductPageActivity extends AppCompatActivity {

    TextView title = findViewById(R.id.title_pp), desc = findViewById(R.id.desc_pp), price = findViewById(R.id.price_pp);
    Button button = findViewById(R.id.add_pp);
    ImageView image = findViewById(R.id.imageView_pp);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
    }
}
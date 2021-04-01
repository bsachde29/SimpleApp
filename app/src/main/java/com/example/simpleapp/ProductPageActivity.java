package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class ProductPageActivity extends AppCompatActivity {

    TextView title, desc , price;
    Button button;
    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        this.title = findViewById(R.id.title_pp);
        this.desc = findViewById(R.id.desc_pp);
        this.price = findViewById(R.id.price_pp);
        this.button = findViewById(R.id.add_pp);
        this.image = findViewById(R.id.imageView_pp);


        try {
            Intent intent = getIntent();

            String name =  intent.getStringExtra("Name");
            String desc = intent.getStringExtra("Description");
            String price = "$ " + intent.getStringExtra("price");
            String ProductID = intent.getStringExtra("ProductID");
            String assetImage = intent.getStringExtra("assetImage");


            InputStream ims = getAssets().open(assetImage);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();


            this.title.setText(name);
            this.desc.setText(desc);
            this.price.setText(price);
            this.image.setImageDrawable(d);

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    /*
                        Add code here
                     */
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
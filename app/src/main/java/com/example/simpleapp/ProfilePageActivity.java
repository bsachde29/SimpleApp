package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilePageActivity extends AppCompatActivity {

    TextView  addAddress, sellerDetails, orders, buyerdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.UserIcon), iconFont);

        setContentView(R.layout.activity_profile_page);
        buyerdetails = findViewById(R.id.gotoDetails);
        addAddress = findViewById(R.id.GotoAddAddress);
        sellerDetails = findViewById(R.id.GotoSellerDetails);
        orders = findViewById(R.id.GotoOrder);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddressBookActivity.class);
                startActivity(intent);
            }
        });
        buyerdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                startActivity(intent);
            }
        });


        sellerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SellerDetailsActivity.class);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Orders.class);
                startActivity(intent);
            }
        });
    }
}
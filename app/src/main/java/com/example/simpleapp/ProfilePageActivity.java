package com.example.simpleapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilePageActivity extends AppCompatActivity {

    TextView  addAddress, sellerDetails, orders, buyerdetails, buyerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.prof), iconFont);

        setContentView(R.layout.activity_profile_page);
        buyerdetails = findViewById(R.id.gotoDetails);
        addAddress = findViewById(R.id.GotoAddAddress);
        sellerDetails = findViewById(R.id.GotoSellerDetails);
        orders = findViewById(R.id.GotoOrder);
        buyerName = findViewById(R.id.buyerName);
        populateFields();


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

    void populateFields() {

        final String buyerID = SaveSharedPreference.getPrefBuyerId(getBaseContext());
        // final String buyerID ="2";
        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_GetBuyer,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String FirstName = jsonObject.getString("FirstName");
                                String LastName = jsonObject.getString("LastName");
                                String PhNumber = jsonObject.getString("MobileNum");
                                String SecAns = jsonObject.getString("SecAns");
                                String eMail = jsonObject.getString("Email");

//                                email.setText(eMail);
                                buyerName.setText(FirstName + " " + LastName);
//                                lastName.setText(LastName);
//                                phNumber.setText(PhNumber);
//                                secAns.setText(SecAns);

//                                saveBut.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        changeDetails();
//                                    }
//                                });

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.getMessage());
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("BuyerID", buyerID);
                    return params;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
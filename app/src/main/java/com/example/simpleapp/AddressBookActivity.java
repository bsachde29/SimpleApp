package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddressBookActivity extends AppCompatActivity {

    TableLayout tableLayout;
    Button add;
    private String BuyerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        StringRequest stringRequest = null;
        add = findViewById(R.id.AddAddressFromBook);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddAdressActivity.class);
                startActivity(intent);
            }
        });

        this.BuyerID = SaveSharedPreference.getPrefBuyerId(getApplicationContext());
        System.out.println("currBuyerID: " + BuyerID);
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_GetAddress,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            AddressCell addressCell;
                            try {

                                System.out.println(response);
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    addressCell = new AddressCell(getApplicationContext(),
                                        jsonObject.getString("StreetAddress") + ", " + jsonObject.getString("Apt"),
                                            jsonObject.getString("City"),
                                            jsonObject.getString("State"),
                                            jsonObject.getString("PostalCode")
                                            );
                                    String addressID = jsonObject.getString("AddressID");
                                    addressCell.delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteAddress(addressID);
                                        }
                                    });
                                    tableLayout = findViewById(R.id.tableLayoutAddressBook);
                                    tableLayout.addView(new RowDivider(getApplicationContext()).tableRow);
                                    tableLayout.addView(addressCell.tableRow);
                                }

                            } catch (Exception e) {
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
                    params.put("BuyerID", BuyerID);
                    return params;
                }
            };


        } catch (Exception E) {
            E.printStackTrace();
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    protected void DeleteAddress(String addressID) {
        StringRequest stringRequest = null;

        this.BuyerID = SaveSharedPreference.getPrefBuyerId(getApplicationContext());
        System.out.println("currBuyerID: " + BuyerID);
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_DeleteAddress,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
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
                    params.put("BuyerID", BuyerID);
                    params.put("AddressID", addressID);
                    return params;
                }
            };


        } catch (Exception E) {
            E.printStackTrace();
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}
package com.example.simpleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddAdressActivity extends AppCompatActivity {

    EditText street, apt, city, state, zip;
    Button add;
    String BuyerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);

        this.BuyerID = SaveSharedPreference.getPrefBuyerId(getApplicationContext());

        add = findViewById(R.id.AddAddress);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                street = findViewById(R.id.AddStreet);
                apt = findViewById(R.id.AddApt);
                city = findViewById(R.id.AddCity);
                state = findViewById(R.id.AddState);
                zip = findViewById(R.id.AddZip);

                addAddress();

            }
        });
    }

    protected void addAddress() {
        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_AddAddress,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            Intent intent = new Intent(getBaseContext(), AddressBookActivity.class);
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
                    params.put("StreetAddress", street.getText().toString());
                    params.put("Apt", apt.getText().toString());
                    params.put("City", city.getText().toString());
                    params.put("State", state.getText().toString());
                    params.put("PostalCode", zip.getText().toString());
                    return params;
                }
            };
        } catch (Exception E) {
            E.printStackTrace();
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
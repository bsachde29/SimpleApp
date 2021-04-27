package com.example.simpleapp;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    EditText email, firstName, lastName, phNumber, secAns;
    Button saveBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        email = (EditText) findViewById(R.id.email_edit);
        phNumber = (EditText) findViewById(R.id.phNumber_edit);
        firstName = findViewById(R.id.firstName_edit);
        lastName = findViewById(R.id.lastName_edit);
        secAns = (EditText) findViewById(R.id.secAns_edit);

        saveBut = findViewById(R.id.saveButton_edit);

        populateFields();


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

                                email.setText(eMail);
                                firstName.setText(FirstName);
                                lastName.setText(LastName);
                                phNumber.setText(PhNumber);
                                secAns.setText(SecAns);

                                saveBut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        changeDetails();
                                    }
                                });

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

    public void changeDetails() {

        final String Email = email.getText().toString();
        final String FirstName = firstName.getText().toString();
        final String LastName = lastName.getText().toString();
        final String PhNumber = phNumber.getText().toString();
        final String BuyerID = SaveSharedPreference.getPrefBuyerId(getBaseContext());
        final String SecAns = secAns.getText().toString();

        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_ModifyBuyer,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                System.out.println(response);
                                Toast.makeText(getApplicationContext(),"Details Changed", Toast.LENGTH_LONG).show();

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
                    params.put("FirstName", FirstName);
                    params.put("LastName", LastName);
                    params.put("Email", Email);
                    params.put("MobileNum", PhNumber);
                    params.put("BuyerID", BuyerID);
                    params.put("SecAns", SecAns);
                    return params;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
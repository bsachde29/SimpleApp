package com.example.simpleapp;

import android.app.ProgressDialog;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button signIn, signup;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signup_instead);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        email = (EditText) findViewById(R.id.emai_li);
        password = (EditText) findViewById(R.id.password_li);
        signIn = (Button) findViewById(R.id.signin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        signIn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                signInUser();

            }

        });

    }

    private void signInUser() {
        final String Email = email.getText().toString();
        final String Pass = password.getText().toString();
        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_LogIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();
//                            email.setText(response);
                            if (response.trim().equalsIgnoreCase("Wrong Details")) {
                                Toast.makeText(getApplicationContext(),
                                        response, Toast.LENGTH_LONG).show();
                            } else {
                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    String BuyerID = (String) jsonObject.get("BuyerID");
                                    System.out.println(BuyerID);
                                    SaveSharedPreference.setPrefBuyerId(getBaseContext(), BuyerID);
                                    setPrefCardId();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
                    params.put("Email", Email);
                    params.put("Pswd", Pass);
                    return params;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void setPrefCardId() {
        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_GetCartID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println("Cart Fetched Successfully");
                                SaveSharedPreference.setPrefCartId(getBaseContext(), response.replace("\"", ""));
                                Toast.makeText(getApplicationContext(),
                                        "Successfully logged in", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getBaseContext(), ProductListActivity.class);
                                startActivity(intent);

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
                    params.put("BuyerID", SaveSharedPreference.getPrefBuyerId(getApplicationContext()));
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
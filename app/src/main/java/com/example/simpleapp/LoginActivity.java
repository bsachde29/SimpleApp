package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    Button signIn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.emai_li);
        password = (EditText) findViewById(R.id.password_li);
        signIn = (Button) findViewById(R.id.signin);
        progressDialog.setMessage("Please wait...");
        signIn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                //TODO check password matching
                signInUser();
            }

        });

    }

    private void signInUser() {
        final String Email = email.getText().toString();
        final String Pass = password.getText().toString();
        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.GET,
                    Constants.URL_SignUp,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            if (response.equalsIgnoreCase("Wrong Details")) {
                                Toast.makeText(getApplicationContext(),
                                        response, Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    email.setText(response);  //TODO change add login successful
                                    JSONObject jsonObject = new JSONObject(response);
                                    String BuyerID = (String) jsonObject.get("BuyerID");
                                    SaveSharedPreference.setPrefBuyerId(getApplicationContext(), BuyerID);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully logged in", Toast.LENGTH_LONG).show();
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


    }
}
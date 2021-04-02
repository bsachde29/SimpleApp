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

public class SignUpActivity extends AppCompatActivity {

    EditText email, password, repassword, firstName, lastName, phNumber;
    Button signup;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email_su);
        phNumber = (EditText) findViewById(R.id.phNumber_su);
        firstName = findViewById(R.id.firstName_su);
        lastName = findViewById(R.id.lastName_su);
        password = (EditText) findViewById(R.id.pass_su);
        repassword = (EditText) findViewById(R.id.pass_su2);
        signup = (Button) findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        signup.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                //TODO check password matching
                registerUser();
            }

        });

    }


    private void registerUser() {

        final String Email = email.getText().toString();
        final String FirstName = firstName.getText().toString();
        final String LastName = lastName.getText().toString();
        final String PhNumber = phNumber.getText().toString();
        final String Pass = password.getText().toString();
        final String SellerID = Integer.toString(Constants.Seller_ID);

        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_SignUp,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {

                                System.out.println(response);
                                JSONObject jsonObject = new JSONObject(response);

                                Intent intent = new Intent(getBaseContext(), ProductPageActivity.class);
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
                    params.put("FirstName", FirstName);
                    params.put("LastName", LastName);
                    params.put("Email", Email);
                    params.put("MobileNum", PhNumber);
                    params.put("Pswd", Pass);
                    params.put("SellerID", SellerID);
                    return params;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

}
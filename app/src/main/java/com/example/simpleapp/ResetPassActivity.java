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

public class ResetPassActivity extends AppCompatActivity {

    EditText email, password, rePassword, secAns;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        email = (EditText) findViewById(R.id.emailReset);
        password = (EditText) findViewById(R.id.passReset);
        rePassword = (EditText) findViewById(R.id.retypeReset);
        secAns = (EditText) findViewById(R.id.secAnswerReset);
        saveButton = (Button) findViewById(R.id.saveReset);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClicked();
            }
        });


    }

    private void saveClicked() {

        final String Email = email.getText().toString();

        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_getEmail,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String ans = jsonObject.getString("SecAns");
                                changePass(ans);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "User Not Found" , Toast.LENGTH_LONG).show();
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
                    params.put("Email", Email);
                    return params;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void changePass(String ans) {
        final String SecAnsFromUser = secAns.getText().toString();
        final String pass = password.getText().toString();
        final String rePass = rePassword.getText().toString();
        final String Email = email.getText().toString();

        if (!pass.equals(rePass)) {
            Toast.makeText(getApplicationContext(),"Passwords Don't Match", Toast.LENGTH_LONG).show();
            return;
        }

        if (!ans.equals(SecAnsFromUser)) {
            Toast.makeText(getApplicationContext(),"Wrong Answer", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_ChangePass,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                Toast.makeText(getApplicationContext(),"Password Updated", Toast.LENGTH_LONG).show();
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
                    params.put("Email", Email);
                    params.put("Pswd", pass);
                    params.put("SecAns", SecAnsFromUser);

                    return params;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


}
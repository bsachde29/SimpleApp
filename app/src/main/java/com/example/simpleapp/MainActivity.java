package com.example.simpleapp;

import android.os.AsyncTask;
import android.os.Bundle;

import android.app.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView text, error;
    Button button;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = (TextView) findViewById(R.id.textView2);

        error = (TextView) findViewById(R.id.textView3);

        button = (Button) findViewById(R.id.button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");



        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                registerUser();
            }

        });
    }

    private void registerUser() {

        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_TEST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {

                                text.setText(response);
                                JSONObject jsonObject = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
    //            @Override
    //            protected Map<String, String> getParams() throws AuthFailureError {
    //                Map<String, String> params = new HashMap<>();
    //                params.put("username", username);
    //                params.put("email", email);
    //                params.put("password", password);
    //                return params;
    //            }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }




//    class Async extends AsyncTask<Void, Void, Void> {
//
//
//
//        String records = "",errort="";
//
//        @Override
//
//        protected Void doInBackground(Void... voids) {
//
//            try
//
//            {
//
//                Class.forName("com.mysql.jdbc.Driver").newInstance();
//
//                String dburl = "jdbc:mysql://selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com:3306/simpledb";
//                String dbusername = "simpledb";
//                String dbpassword = "sell1234";
//
//                Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
//
//                Statement statement = connection.createStatement();
//
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM BUYERS");
//
//                while(resultSet.next()) {
//
//                    records += resultSet.getString(1) + " " + resultSet.getString(2) + "\n";
//
//                }
//
//            }
//
//            catch(Exception e)
//
//            {
//
//                errort = e.toString();
//
//            }
//
//            return null;
//
//        }
//
//
//
//        @Override
//
//        protected void onPostExecute(Void aVoid) {
//
//            text.setText(records);
//
//
//            if(errort != "")
//
//                error.setText(errort);
//
//            super.onPostExecute(aVoid);
//
//        }
//
//
//
//
//
//    }



}
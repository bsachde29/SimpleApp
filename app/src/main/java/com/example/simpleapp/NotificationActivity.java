package com.example.simpleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        tableLayout = findViewById(R.id.notificationTable);
        addNotifications();

    }

    void addNotifications() {
        final String BuyerID = SaveSharedPreference.getPrefBuyerId(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_GetNotification,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONArray jsonArray = new JSONArray(response);

                            NotificationCell cell = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.get(i).toString().equals("null")) {
                                    continue;
                                }
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String OrderID = jsonObject.getString("OrderID");
                                String notify = generateString(OrderID);
                                        cell = new NotificationCell(getApplicationContext(), notify);

                                tableLayout.addView(new RowDivider(getApplicationContext()).tableRow);
                                tableLayout.addView(cell.tableRow);
                            }

//                                tableLayout.addView(new RowDivider(getApplicationContext()).tableRow);
//                                tableLayout.addView(cell2.tableRow);
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

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public String generateString(String OrderID) {
        return "Congrats! " + Constants.Store_Name + " has accepted your Order with the OrderID: " + OrderID;
    }
}
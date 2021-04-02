package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Orders extends AppCompatActivity {


    private String BuyerID;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        BuyerID = SaveSharedPreference.getPrefBuyerId(getApplicationContext());

        StringRequest stringRequest = null;

        try {
            InputStream ims = getAssets().open("prod_holder.jpg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();

            final String SellerID = Integer.toString(Constants.Seller_ID);

            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_GetOrderList,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                System.out.println(response);
                                JSONArray jsonArray = new JSONArray(response);

                                OrderCell cell = null;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    if (jsonArray.get(i).toString().equals("null")) {
                                        continue;
                                    }
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String orderID = jsonObject.getString("OrderID");
                                    String total = jsonObject.getString("totalPrice");
                                    String ProductID = (jsonObject.getString("Status").equals("0")) ? "ACCEPTED" : "NOT ACCEPTED";

                                    cell = new OrderCell(getApplicationContext(),
                                            orderID, total, ProductID);
                                    tableLayout = findViewById(R.id.tableLayoutProdList); //TODO change this
                                    tableLayout.addView(new RowDivider(getApplicationContext()).tableRow);
                                    cell.tableRow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getBaseContext(), ProductPageActivity.class); //TODO change activity to particular view
                                            intent.putExtra("OrderID", orderID);
                                            startActivity(intent);
                                        }
                                    });
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
        } catch (
                Exception E) {
            E.printStackTrace();
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}

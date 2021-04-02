package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class SellerDetailsActivity extends AppCompatActivity {

    TextView name, email, mobNo, facebook, insta, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);

        this.name = findViewById(R.id.SellerName);
        this.email = findViewById(R.id.SellerEmail);
        this.mobNo = findViewById(R.id.SellerMobNo);
        this.facebook = findViewById(R.id.SellerFB);
        this.insta = findViewById(R.id.SellerInsta);
        this.desc = findViewById(R.id.SellerDesc);

        StringRequest stringRequest = null;
//        ProductCell cell1 = null, cell2 = null;
        try {
            InputStream ims = getAssets().open("prod_holder.jpg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();

            final String SellerID = Integer.toString(Constants.Seller_ID);

            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_GetSeller,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                System.out.println(response);
                                JSONObject jsonObject = new JSONObject(response);
                                name.setText(jsonObject.getString("FirstName") + " " + jsonObject.getString("LastName"));
                                email.setText(jsonObject.getString("Email"));
                                mobNo.setText(jsonObject.getString("MobileNum"));
                                facebook.setText(jsonObject.getString("FbHandle"));
                                insta.setText(jsonObject.getString("InstaHandle"));
                                desc.setText(jsonObject.getString("Description"));
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
                    params.put("SellerID", SellerID);
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
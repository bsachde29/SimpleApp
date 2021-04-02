package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProductPageActivity extends AppCompatActivity {

    TextView title, desc , price;
    Button button;
    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        this.title = findViewById(R.id.title_pp);
        this.desc = findViewById(R.id.desc_pp);
        this.price = findViewById(R.id.price_pp);
        this.button = findViewById(R.id.add_pp);
        this.image = findViewById(R.id.imageView_pp);


        SaveSharedPreference.setPrefBuyerId(getApplicationContext(), "1");
        SaveSharedPreference.setPrefCartId(getApplicationContext(), "1");

        try {
            Intent intent = getIntent();

            String name =  intent.getStringExtra("Name");
            String desc = intent.getStringExtra("Description");
            String price = "$ " + intent.getStringExtra("price");
            String ProductID = intent.getStringExtra("ProductID");
            String assetImage = intent.getStringExtra("assetImage");


            InputStream ims = getAssets().open(assetImage);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();


            this.title.setText(name);
            this.desc.setText(desc);
            this.price.setText(price);
            this.image.setImageDrawable(d);

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    addToCart(ProductID);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToCart(String ProductID) {
        String BuyerID = SaveSharedPreference.getPrefBuyerId(getApplicationContext());
        String CartID = SaveSharedPreference.getPrefBuyerId(getApplicationContext());


        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_AddProductCart,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                System.out.println(response);
                                Intent intent = new Intent(getBaseContext(), CartActivity.class);
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
                    System.out.println(SaveSharedPreference.getPrefCartId(getApplicationContext()));
                    params.put("CartID", SaveSharedPreference.getPrefCartId(getApplicationContext()));
                    params.put("ProductID", ProductID);
                    params.put("Count", "1");
                    System.out.println(params.get("CartID") +  " " + params.get("ProductID"));
                    return params;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



    }
}
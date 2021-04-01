package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class ProductListActivity extends AppCompatActivity {

    TableLayout tableLayout;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        StringRequest stringRequest = null;
        ProductCell cell1 = null, cell2 = null;
        try {
            InputStream ims = getAssets().open("prod_holder.jpg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();

            final String SellerID = Integer.toString(Constants.Seller_ID);

            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_Inventory,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                System.out.println(response);
                                JSONArray jsonArray = new JSONArray(response);

//                                cell1 = new ProductCell(getApplicationContext(), "Arjun", "Shivam loda loda loda loda loda loda loda loda loda loda ", "2", d);
//                                cell2 = new ProductCell(getApplicationContext(), "gfxchjk", "dbvck hbsdhjv ljhgsadfb hkvbh kas dbkhv cbhk adsbhj ", "3000", d);

                                ProductCell cell = null;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    if (jsonArray.get(i).toString().equals("null")) {
                                        continue;
                                    }
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name =  jsonObject.getString("Name");
                                    String desc = jsonObject.getString("Description");
                                    String price = jsonObject.getString("price");
                                    String ProductID = jsonObject.getString("ProductID");

                                    cell = new ProductCell(getApplicationContext(),
                                            name, desc, price, d);
                                    tableLayout = findViewById(R.id.tableLayoutProdList);
                                    tableLayout.addView(new RowDivider(getApplicationContext()).tableRow);
                                    cell.tableRow.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            Intent intent = new Intent(getBaseContext(), ProductPageActivity.class);
                                            intent.putExtra("Name", name);
                                            intent.putExtra("Description", desc);
                                            intent.putExtra("price", price);
                                            intent.putExtra("assetImage", "prod_holder.jpg");
                                            intent.putExtra("ProductID", ProductID);
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
package com.example.simpleapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    CartCell cartCell;
    TableLayout tableLayout;
    EditText discountCode;
    Button checkout, discountButton;
    private String CartID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.discountLayout), iconFont);

        StringRequest stringRequest = null;

        discountButton = findViewById(R.id.discountButton);
        discountCode = findViewById(R.id.discountCode);

        this.CartID = SaveSharedPreference.getPrefCartId(getApplicationContext());
        System.out.println("currCartID - " + CartID);
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_GetOGCart,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                System.out.println(response);

                                JSONObject jsonObject = new JSONObject(response);

                                double subTotal = Double.parseDouble(jsonObject.getString("totalPrice"));
                                getProducts(subTotal);

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
                    params.put("CartID", CartID);
                    return params;
                }
            };


        } catch (Exception E) {
            E.printStackTrace();
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    protected void getProducts(double subTotal) {

        System.out.println(CartID);

        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_GetCartProducts,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                System.out.println(response);
                                JSONArray jsonArray = new JSONArray(response);
                                ArrayList<Product> prodList = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (jsonArray.get(i).toString().equals("null")) {
                                        continue;
                                    }
                                    Product newProd = new Product(
                                            jsonObject.getString("Name"),
                                            jsonObject.getString("Description"),
                                            jsonObject.getString("Category"),
                                            Boolean.parseBoolean(jsonObject.getString("hasSubcategories")),
                                            Double.parseDouble(jsonObject.getString("price")),
                                            Integer.parseInt(jsonObject.getString("ProductID")),
                                            inStock.IN_STOCK,
                                            null);
                                    prodList.add(newProd);
                                }
                                getCart(prodList, subTotal);

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
                    params.put("CartID", CartID);
                    return params;
                }
            };


        } catch (Exception E) {
            E.printStackTrace();
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    protected void getCart(ArrayList<Product> prodList, double subtotal) {
        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_GetCart,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                System.out.println(response);
                                JSONArray jsonArray = new JSONArray(response);
                                ArrayList<Integer> countList = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    countList.add(Integer.parseInt(jsonObject.getString("Count")));
                                }

                                updateCart(prodList, countList, subtotal);


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
                    params.put("CartID", CartID);
                    return params;
                }
            };

        } catch (Exception E) {
            E.printStackTrace();
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    protected void updateCart(ArrayList<Product> prodList, ArrayList<Integer> countList, double subTotal) {
        try {
            InputStream ims = getAssets().open("prod_holder.jpg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();

            for (int i = 0; i < prodList.size(); i++) {
                cartCell = new CartCell(getApplicationContext(), prodList.get(i).getName(), Double.toString(prodList.get(i).getPrice()), d, countList.get(i).toString());
                tableLayout = findViewById(R.id.cart_table);
                tableLayout.addView(new RowDivider(getApplicationContext()).tableRow);
                tableLayout.addView(cartCell.tableRow);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView subtotal = findViewById(R.id.subtotal_cart);
        subtotal.setText("$ " + subTotal);
        checkout = findViewById(R.id.checkoutButton);

        discountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDiscount();
            }
        });


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
            }
        });

    }

    protected void addDiscount() {
        final String CartID = SaveSharedPreference.getPrefCartId(getBaseContext());
        final String DiscountCode = discountCode.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_AddDiscount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            if (!response.equals("Cart Updated")) {
                                Toast.makeText(getApplicationContext(),
                                        "Code Applied! Subtotal Updated!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Invalid Code!", Toast.LENGTH_LONG).show();
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
                params.put("CartID", CartID);
                params.put("DiscountCode", DiscountCode);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    protected void addOrder() {
        StringRequest stringRequest = null;
        String BuyerID = SaveSharedPreference.getPrefBuyerId(getApplicationContext());
        String CartID = SaveSharedPreference.getPrefCartId(getApplicationContext());
        String SellerID = Integer.toString(Constants.Seller_ID);
        try {
            stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_AddOrder,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("hello");
                            System.out.println(response);
                            setPrefCardId();
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
                    params.put("CartID", CartID);
                    params.put("SellerID", SellerID);
                    return params;
                }
            };
        } catch (Exception E) {
            E.printStackTrace();
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
                                System.out.println(response);
                                System.out.println("Cart Fetched Successfully");
                                SaveSharedPreference.setPrefCartId(getApplicationContext(), response.replace("\"", ""));
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


}
package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;

import java.io.InputStream;

public class ProductListActivity extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);


        ProductCell cell1 = null, cell2 = null;
        try {
            InputStream ims = getAssets().open("prod_holder.jpg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            ims .close();
            cell1 = new ProductCell(getApplicationContext(), "Arjun", "Shivam loda loda loda loda loda loda loda loda loda loda ", "2", d);
            cell2 = new ProductCell(getApplicationContext(), "gfxchjk", "dbvck hbsdhjv ljhgsadfb hkvbh kas dbkhv cbhk adsbhj ", "3000", d);


            tableLayout = findViewById(R.id.tableLayoutProdList);
            tableLayout.addView(new RowDivider(getApplicationContext()).tableRow);
            tableLayout.addView(cell1.tableRow);
            tableLayout.addView(new RowDivider(getApplicationContext()).tableRow);
            tableLayout.addView(cell2.tableRow);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
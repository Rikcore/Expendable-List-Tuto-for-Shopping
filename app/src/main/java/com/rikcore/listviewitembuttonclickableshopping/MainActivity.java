package com.rikcore.listviewitembuttonclickableshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewProduct = (ListView)findViewById(R.id.listViewProduct);
        TextView textViewTotal = (TextView)findViewById(R.id.textViewTotal);
        Button buttonBuy = findViewById(R.id.buttonBuy);

        ArrayList<Product> productsList = new ArrayList<Product>();

        productsList.add(new Product("Chemise", 3));
        productsList.add(new Product("Pantalon", 4));
        productsList.add(new Product("Pull", 5));

        final ProductAdapter productAdapter = new ProductAdapter(this, productsList, textViewTotal);
        listViewProduct.setAdapter(productAdapter);

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, String.valueOf(productAdapter.getTotal()), Toast.LENGTH_SHORT).show();
                HashMap<Product, Integer> panier = productAdapter.getPanier();
                
                String toto = "toto";
            }
        });
    }
}

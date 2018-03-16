package com.rikcore.listviewitembuttonclickableshopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rikcore on 15/03/2018.
 */

public class ProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> productArrayList;
    TextView textViewTotal;
    int total;
    HashMap<Product, Integer> panier;

    public ProductAdapter (Context context, ArrayList<Product> productArrayList, TextView textViewTotal){
        this.context = context;
        this.productArrayList = productArrayList;
        this.textViewTotal = textViewTotal;
        this.total = 0;
        this.panier = new HashMap<Product, Integer>();

        for (int i = 0; i < productArrayList.size(); i++){
            panier.put(productArrayList.get(i), 0);
        }
    }
    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return productArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.product_item, viewGroup, false);
        }

        final TextView textViewProductName = view.findViewById(R.id.textViewProductName);
        TextView textViewProductPrice = view.findViewById(R.id.textViewProductPrice);
        final Button buttonMinus = view.findViewById(R.id.buttonMinus);
        Button buttonPlus = view.findViewById(R.id.buttonPlus);
        final TextView textViewSelected = view.findViewById(R.id.textViewSelected);

        final Product currentProduct = productArrayList.get(i);

        textViewProductName.setText(currentProduct.getProductName());
        textViewProductPrice.setText(String.valueOf(currentProduct.getProductPrice()) + " €");

        if(panier.get(currentProduct) == null || panier.get(currentProduct) == 0){
            buttonMinus.setEnabled(false);
        }

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panier.put(currentProduct, panier.get(currentProduct) - 1);
                total = total - currentProduct.getProductPrice();
                textViewTotal.setText("Total : " + String.valueOf(total) + " €");
                textViewSelected.setText("Quantité : " + panier.get(currentProduct));
                if(panier.get(currentProduct) == 0){
                    buttonMinus.setEnabled(false);
                    textViewSelected.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panier.put(currentProduct, panier.get(currentProduct) + 1);
                total = total + currentProduct.getProductPrice();
                textViewTotal.setText("Total : " + String.valueOf(total) + " €");
                buttonMinus.setEnabled(true);
                textViewSelected.setVisibility(View.VISIBLE);
                textViewSelected.setText("Quantité : " + panier.get(currentProduct));
            }
        });
        return view;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public HashMap<Product, Integer> getPanier() {
        return panier;
    }

    public void setPanier(HashMap<Product, Integer> panier) {
        this.panier = panier;
    }
}

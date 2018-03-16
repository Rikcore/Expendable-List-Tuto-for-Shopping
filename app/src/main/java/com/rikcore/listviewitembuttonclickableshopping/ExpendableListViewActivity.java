package com.rikcore.listviewitembuttonclickableshopping;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExpendableListViewActivity extends AppCompatActivity {

    ExpendableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Product>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expendable_list_view);


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView textViewTotal = (TextView) mCustomView.findViewById(R.id.title_text);
        listAdapter = new ExpendableListAdapter(ExpendableListViewActivity.this, listDataHeader, listDataChild, textViewTotal);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        textViewTotal.setText("Total : 0 €");

        ImageButton imageButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final int totalPayment = listAdapter.getTotal();
                final HashMap<Product, Integer> panier = listAdapter.getPanier();

                Iterator<Map.Entry<Product,Integer>> iter = panier.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<Product,Integer> entry = iter.next();
                    if(entry.getValue() == 0){
                        iter.remove();
                    }
                }

                Log.d("test", panier.toString());

                StringBuilder stringBuilder = new StringBuilder();
                for(Map.Entry<Product, Integer> e : panier.entrySet()) {
                    Product  key = e.getKey();
                    Integer value = e.getValue();
                    stringBuilder.append(key.getProductName() + " x " + value + " = " + key.getProductPrice()*value + "€ \n");
                }

                String panierString = stringBuilder.toString() + "\n\n" + "Total : " + totalPayment + "€";

                AlertDialog alertDialog = new AlertDialog.Builder(ExpendableListViewActivity.this).create();
                alertDialog.setTitle("Facture");
                alertDialog.setMessage(panierString);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Product>>();

        // Adding child data
        listDataHeader.add("Pressing");
        listDataHeader.add("Repassage");

        // Adding child data
        /*List<String> pressing = new ArrayList<String>();
        pressing.add("Chemise");
        pressing.add("Pantalon");
        pressing.add("Pull");


        List<String> repassage = new ArrayList<String>();
        repassage.add("Pyjama");
        repassage.add("Chaussettes");
        repassage.add("Polo");*/

        List<Product> pressing = new ArrayList<Product>();
        pressing.add(new Product("Chemise", 3));
        pressing.add(new Product("Pantalon", 4));
        pressing.add(new Product("Culotte", 2));

        List<Product> repassage = new ArrayList<Product>();
        repassage.add(new Product("Repassage veste", 3));
        repassage.add(new Product("Repassage Costume", 7));


        //TEST





        listDataChild.put(listDataHeader.get(0), pressing); // Header, Child data
        listDataChild.put(listDataHeader.get(1), repassage);
    }
}

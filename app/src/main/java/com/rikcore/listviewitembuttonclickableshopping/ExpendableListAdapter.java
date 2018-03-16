package com.rikcore.listviewitembuttonclickableshopping;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rikcore on 15/03/2018.
 */


public class ExpendableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Product>> _listDataChild;
    private TextView textViewTotal;

    int total;
    HashMap<Product, Integer> panier;

    public ExpendableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<Product>> listChildData, TextView textViewTotal) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.textViewTotal = textViewTotal;
        this.total = 0;
        this.panier = new HashMap<Product, Integer>();

        for(Map.Entry<String, List<Product>> e : listChildData.entrySet()) {
            String  key = e.getKey();
            List<Product> value = e.getValue();
            for (int i = 0; i < value.size(); i++){
                panier.put(value.get(i), 0);
            }
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Product currentProduct = (Product) getChild(groupPosition, childPosition);
        String productName = currentProduct.getProductName();
        int productPrice = currentProduct.getProductPrice();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView textViewProductionName = (TextView) convertView.findViewById(R.id.textViewProductName);
        TextView textViewProductPrice = convertView.findViewById(R.id.textViewProductPrice);
        final TextView textViewSelected = convertView.findViewById(R.id.textViewSelected);

        textViewProductionName.setText(productName);
        textViewProductPrice.setText(String.valueOf(productPrice) + "€");

        final Button buttonMinus = convertView.findViewById(R.id.buttonMinus);
        final Button buttonPlus = convertView.findViewById(R.id.buttonPlus);
        buttonMinus.setEnabled(false);

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
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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


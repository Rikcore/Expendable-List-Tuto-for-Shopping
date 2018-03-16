package com.rikcore.listviewitembuttonclickableshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        Button buttonSimpleListView = findViewById(R.id.buttonSimpleListView);
        Button buttonExpendable = findViewById(R.id.buttonExpendable);

        buttonSimpleListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BootActivity.this, MainActivity.class));
            }
        });

        buttonExpendable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BootActivity.this, ExpendableListViewActivity.class));
            }
        });
    }
}

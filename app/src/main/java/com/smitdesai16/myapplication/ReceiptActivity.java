package com.smitdesai16.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {

    private static TextView tvTitle, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(intent.hasExtra("receipt")) {
            // todo make call
            // todo set data in title and description
            tvTitle.setText(bundle.getString("receipt"));
        }
    }
}
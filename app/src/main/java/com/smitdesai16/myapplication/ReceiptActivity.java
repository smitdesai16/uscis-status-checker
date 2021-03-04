package com.smitdesai16.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

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
            // tvTitle.setText(bundle.getString("receipt"));

            try {
                String response = new USCISService().execute().get();
                response = response.substring(response.indexOf("<div class=\"rows text-center\">"));
                response = response.substring(0, response.indexOf("<div class=\"col-lg-12 case-status-from form3 mt40 clr\">"));

                tvTitle.setText(response.substring(response.indexOf("<h1>") + 4, response.indexOf("</h1>")));
                tvDescription.setText(response.substring(response.indexOf("<p>") + 3, response.indexOf("</p>")));
                // tvTitle.setText(response);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
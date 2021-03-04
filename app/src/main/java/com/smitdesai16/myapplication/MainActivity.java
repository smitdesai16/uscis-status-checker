package com.smitdesai16.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    private static ListView lvStatus;
    private static EditText etReceiptNumber;
    private static Button btnAddInStatusChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStatus = findViewById(R.id.lvStatus);
        etReceiptNumber = findViewById(R.id.etReceiptNumber);
        btnAddInStatusChecker = findViewById(R.id.btnAddInStatusChecker);

        lvStatus.setOnItemClickListener(this);
        lvStatus.setOnItemLongClickListener(this);
        btnAddInStatusChecker.setOnClickListener(this);

        ReloadListView();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // todo open page with code
        String clickedRestaurant = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, clickedRestaurant, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddInStatusChecker:
                AddReceiptToSharedPreferences(etReceiptNumber.getText().toString());
                ReloadListView();
                etReceiptNumber.setText("");
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        DeleteReceiptFromSharedPreferences(adapterView.getItemAtPosition(i).toString());
        ReloadListView();
        return true;
    }

    private Set<String> GetReceiptsFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet("receiptNumbers", null);
    }

    private void AddReceiptToSharedPreferences(String receipt) {
        if (receipt.equals("")) {
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        Set<String> receiptNumbers = GetReceiptsFromSharedPreferences();
        if (receiptNumbers == null) {
            receiptNumbers = new HashSet<String>();
        }
        receiptNumbers.add(receipt);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("receiptNumbers", receiptNumbers);
        editor.apply();
    }

    private void DeleteReceiptFromSharedPreferences(String receipt) {
        Set<String> receipts = GetReceiptsFromSharedPreferences();
        receipts.remove(receipt);
        SharedPreferences sharedPreferences = getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("receiptNumbers", receipts);
        editor.apply();
    }

    private void ReloadListView() {
        String[] receiptNumberArray = {};

        Set<String> receiptNumbers = GetReceiptsFromSharedPreferences();
        if (receiptNumbers != null) {
            receiptNumberArray = receiptNumbers.toArray(new String[0]);
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, receiptNumberArray);
        lvStatus.setAdapter(listAdapter);
    }
}
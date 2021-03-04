package com.smitdesai16.myapplication;

import android.content.Context;
import android.content.Intent;
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

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    private ListView lvStatus;
    private EditText etReceiptNumber;
    private Button btnAddInStatusChecker;
    private String SHARED_PREFERENCES_NAME = "USCSI";
    private String SHARED_PREFERENCES_RECEIPT_SET = "MyReceipt";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
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
        String receiptNumber = adapterView.getItemAtPosition(i).toString();
        Intent receiptActivity = new Intent(this, ReceiptActivity.class);
        receiptActivity.putExtra("receipt", receiptNumber);
        startActivity(receiptActivity);
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
        return sharedPreferences.getStringSet(SHARED_PREFERENCES_RECEIPT_SET, null);
    }

    private void AddReceiptToSharedPreferences(String receipt) {
        if (receipt.equals("")) {
            return;
        }
        Set<String> receiptNumbers = GetReceiptsFromSharedPreferences();
        if (receiptNumbers == null) {
            receiptNumbers = new HashSet<String>();
        }
        receiptNumbers.add(receipt);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putStringSet(SHARED_PREFERENCES_RECEIPT_SET, receiptNumbers);
        editor.apply();
    }

    private void DeleteReceiptFromSharedPreferences(String receipt) {
        Set<String> receipts = GetReceiptsFromSharedPreferences();
        receipts.remove(receipt);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.remove(SHARED_PREFERENCES_RECEIPT_SET);
        editor.apply();
        editor.putStringSet(SHARED_PREFERENCES_RECEIPT_SET, receipts);
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
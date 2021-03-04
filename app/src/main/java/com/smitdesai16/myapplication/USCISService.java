package com.smitdesai16.myapplication;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class USCISService extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://egov.uscis.gov/casestatus/mycasestatus.do").newBuilder();
        urlBuilder.addQueryParameter("appReceiptNum", strings[0]);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}

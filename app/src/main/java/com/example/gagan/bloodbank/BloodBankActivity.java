package com.example.gagan.bloodbank;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BloodBankActivity extends AppCompatActivity {

    String URL;
    private RecyclerView mRecyclerView;
    private BloodBankAdapter mListAdapter;
    private List<BloodBankItem> mListData;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        URL = "https://data.gov.in/node/356981/datastore/export/json";

        mRecyclerView = findViewById(R.id.bank_recyclerview);
        mProgressBar = findViewById(R.id.bank_progressbar);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        mListData = new ArrayList<>();
        mListAdapter = new BloodBankAdapter(mListData, this);
        mRecyclerView.setAdapter(mListAdapter);
        new AsyncTaskBank().execute(URL);
        mProgressBar.setVisibility(View.VISIBLE);

    }

    public class AsyncTaskBank extends AsyncTask<String, Void, String>{
        public AsyncTaskBank() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

               // mListAdapter.setListData(mListData);
                 mListAdapter.notifyDataSetChanged();

            } else {
                Toast.makeText(BloodBankActivity.this, "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String weather = "UNDEFINED";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject root = new JSONObject(builder.toString());
                JSONArray banksList = root.getJSONArray("data");

                BloodBankItem item;
                for(int i=-0;i<banksList.length();i++)
                {
                    JSONArray bloodBank = banksList.getJSONArray(i);
                    //JsonArray bloodBank = bank.getAsJsonArray();
                    String bankName =  bloodBank.get(4).toString();
                    String district = bloodBank.get(3).toString();
                    String city = bloodBank.get(2).toString();
                    String state = bloodBank.get(1).toString();

                    item = new BloodBankItem();
                    item.setBloodBankName(bankName);
                    item.setDistrict(district);
                    item.setCity(city);
                    item.setState(state);

                    mListData.add(item);
                   // mListAdapter.notifyDataSetChanged();


                }

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return weather;        }
    }
}

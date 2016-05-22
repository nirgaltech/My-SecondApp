package com.example.jbt.refreshlistdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.github.jeremiemartinez.refreshlistview.RefreshListView;

public class MainActivity extends AppCompatActivity implements RefreshListView.OnRefreshListener {

    private ArrayAdapter<String> adapter;
    private RefreshListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.add("One");
        adapter.add("Two");
        adapter.add("Three");

        listView = (RefreshListView) findViewById(R.id.list);
        if(listView!=null) {
            listView.setAdapter(adapter);
            listView.setEnabledDate(true);
            listView.setRefreshListener(this);
        }
    }

    @Override
    public void onRefresh(RefreshListView listView) {
        BackgroundTask t = new BackgroundTask();
        t.execute();
    }

    private class BackgroundTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            return "New value " + Math.random();
        }

        @Override
        protected void onPostExecute(String newValue) {

            adapter.add(newValue);
            listView.finishRefreshing();
        }
    }
}

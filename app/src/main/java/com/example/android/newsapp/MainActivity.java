package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String NEWS_URL = "http://content.guardianapis.com/search?q=debate&tag=environment/recycling&from-date=2014-01-01&contributor=green&api-key=test";
    private ProgressBar progressBar;
    private TextView tvMessage;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsList = (ListView) findViewById(R.id.list);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        newsList.setEmptyView(tvMessage);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsList.setAdapter(newsAdapter);

        if (checkNetworkConnection()) {
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            tvMessage.setText(R.string.no_internet);
            progressBar.setVisibility(View.GONE);
        }

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsAdapter.getItem(position);
                Uri webUri = Uri.parse(news.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webUri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new NewsAsyncLoader(this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
        tvMessage.setText(R.string.no_news);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        newsAdapter.clear();
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}

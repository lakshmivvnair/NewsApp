package com.example.android.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by lakshmivineeth on 8/6/16.
 */
class NewsAsyncLoader extends AsyncTaskLoader<List<News>> {

    private final String mUrl;

    public NewsAsyncLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public List<News> loadInBackground() {
        return Utils.fetchNews(mUrl);
    }
}

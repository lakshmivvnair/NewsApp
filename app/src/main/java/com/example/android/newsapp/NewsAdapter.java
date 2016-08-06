package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lakshmivineeth on 8/6/16.
 */
class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Context context, List<News> objects) {
        super(context, R.layout.news_list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View newsList = convertView;
        ViewHolder holder;
        if (newsList == null) {
            newsList = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        } else {
            holder = (ViewHolder) newsList.getTag();
        }

        News news = getItem(position);

        holder = new ViewHolder();
        holder.tvSectionIndicator = (TextView) newsList.findViewById(R.id.tvSectionIndicator);
        holder.tvTitle = (TextView) newsList.findViewById(R.id.tvTitle);
        holder.tvDate = (TextView) newsList.findViewById(R.id.tvDate);
        holder.tvContributor = (TextView) newsList.findViewById(R.id.tvContributor);

        holder.tvSectionIndicator.setText(processSectionName(news.getSectionName()));
        holder.tvTitle.setText(news.getWebTitle());
        holder.tvContributor.setText(news.getContributor());

        holder.tvDate.setText(processDate(news.getWebPublicationDate()));

        newsList.setTag(holder);

        return newsList;
    }

    private String processDate(String date) {
        Date temp = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            temp = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp.toString();
    }

    private String processSectionName(String sectionName) {
        return sectionName.substring(0, 1);
    }

    static class ViewHolder {
        TextView tvSectionIndicator;
        TextView tvTitle;
        TextView tvDate;
        TextView tvContributor;
    }
}


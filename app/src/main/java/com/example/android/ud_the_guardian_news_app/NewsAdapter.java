package com.example.android.ud_the_guardian_news_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    private Context context;
    public NewsAdapter(Context context, ArrayList<News> earthquakes) {
        super(context, 0, earthquakes);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView tittleTV = listItemView.findViewById(R.id.news_tittle);
        tittleTV.setText(currentNews.getTittle());

        TextView sectionTV = listItemView.findViewById(R.id.news_section);
        sectionTV.setText(currentNews.getSection());

        TextView authorTV = listItemView.findViewById(R.id.news_author);
        String authorName = currentNews.getAuthor();
        if (authorName != null) {
            authorName = context.getResources().getString(R.string.by_author, authorName);
            authorTV.setText(authorName);
        }

        TextView dateTV = listItemView.findViewById(R.id.date);
        String date = currentNews.getDate();
        if (date != null) {
            dateTV.setText(date);
        }

        TextView timeTV = listItemView.findViewById(R.id.time);
        String time = currentNews.getTime();
        if (time != null) {
            timeTV.setText(time);
        }

        return listItemView;
    }
}

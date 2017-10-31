package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterForAnnouncements extends BaseAdapter{
    Context context;
    String[] titles;
    String[] body;
    String[] messages;
    int count;
    AdapterForAnnouncements(Context context,String[] titles,String[] body,String[] messages,int count){
        this.context=context;
        this.titles=titles;
        this.body=body;
        this.messages=messages;
        this.count=count;
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.label_announcements,null);
        TextView title_tv=(TextView)convertView.findViewById(R.id.x_label_announcements_title);
        TextView body_tv=(TextView)convertView.findViewById(R.id.x_label_announcements_body);
        TextView message_tv=(TextView)convertView.findViewById(R.id.x_label_announcements_message);
        title_tv.setText(titles[position]);
        body_tv.setText(body[position]);
        message_tv.setText(messages[position]);
        return convertView;
    }
}
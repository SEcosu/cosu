package com.example.cosu_pra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cosu_pra.R;
import com.example.cosu_pra.mypostList;

import java.util.ArrayList;

public class MypostItemAdapter extends BaseAdapter {
    ArrayList<mypostList> items = new ArrayList<mypostList>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        mypostList MypostList = items.get(position);
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mypostlistitem, parent, false);

        }
        TextView postname = convertView.findViewById(R.id.my_content);
        TextView postcate = convertView.findViewById(R.id.my_category);

        postname.setText(MypostList.getContent());
        postname.setText(MypostList.getCategory());
        return convertView;
    }
    public void addItem(mypostList item){
        items.add(item);
    }
}

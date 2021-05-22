package com.example.cosu_pra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cosu_pra.LikeItem;
import com.example.cosu_pra.R;

import java.util.ArrayList;

public class MylikeItemAdapter  extends BaseAdapter {
    ArrayList<LikeItem> items = new ArrayList<LikeItem>();
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
        LikeItem MylikeList = items.get(position);
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mylikeitem, parent, false);

        }
        TextView postcontent = convertView.findViewById(R.id.mylike_content);
        TextView postwriter = convertView.findViewById(R.id.mylike_writer);

        postcontent.setText(MylikeList.getContent());
        postwriter.setText(MylikeList.getWriter());
        return convertView;
    }
    public void addItem(LikeItem item){
        items.add(item);
    }
}

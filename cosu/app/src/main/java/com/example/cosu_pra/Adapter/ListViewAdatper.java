package com.example.cosu_pra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cosu_pra.DTO.ListViewItem;
import com.example.cosu_pra.R;

import java.util.List;

public class ListViewAdatper extends ArrayAdapter<ListViewItem> {
    private Context context;
    private List<ListViewItem> listviewitem = null;
    public ListViewAdatper(@NonNull Context context, List<ListViewItem> listviewitem) {
        super(context,0, listviewitem);
        this.listviewitem = listviewitem;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListViewItem listViewItem = listviewitem.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.chatroomlist,null);
            TextView nname = (TextView)convertView.findViewById(R.id.chatname);
            nname.setText(listViewItem.getName());
            TextView ncontent = (TextView)convertView.findViewById(R.id.chatContent);
            nname.setText(listViewItem.getContent());
            TextView ntime = (TextView)convertView.findViewById(R.id.chatTime);
            nname.setText(listViewItem.getTime());

        }
        return convertView;
    }

}

package com.example.cosu_pra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cosu_pra.R;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomAdatper extends BaseAdapter {
    private Context context;
    private ArrayList<ChatRoomItem> listViewItemList = new ArrayList<ChatRoomItem>();

    public ChatRoomAdatper() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public ChatRoomItem getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatroomlist, parent, false);

        }

        //convertView = LayoutInflater.from(context).inflate(R.layout.chatroomlist,null);
        TextView nname = (TextView) convertView.findViewById(R.id.chatroomName);
        TextView ncontent = (TextView) convertView.findViewById(R.id.chatContent);
        TextView ntime = (TextView) convertView.findViewById(R.id.chatTime);

        ChatRoomItem item = listViewItemList.get(position);
        ntime.setText(item.getLastTime());
        nname.setText(item.getRoomName());
        ncontent.setText(item.getLastMSG());
        return convertView;
    }

    public void addItem(ChatRoomItem item) {
        listViewItemList.add(item);
    }


}

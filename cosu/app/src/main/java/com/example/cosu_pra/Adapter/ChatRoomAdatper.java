package com.example.cosu_pra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cosu_pra.R;

import java.util.List;

public class ChatRoomAdatper extends ArrayAdapter<ChatRoomItem> {
    private Context context;
    private List<ChatRoomItem> itemList = null;

    public ChatRoomAdatper(@NonNull Context context, List<ChatRoomItem> itemList) {
        super(context,0, itemList);
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChatRoomItem chatRoomItem = itemList.get(position);
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatroomlist, parent, false);

        }

        //convertView = LayoutInflater.from(context).inflate(R.layout.chatroomlist,null);
        TextView nname = (TextView)convertView.findViewById(R.id.chatroomName);
        nname.setText(chatRoomItem.getRoomName());
        TextView ncontent = (TextView)convertView.findViewById(R.id.chatContent);
        ncontent.setText(chatRoomItem.getLastMSG());
        TextView ntime = (TextView)convertView.findViewById(R.id.chatTime);
        ntime.setText(chatRoomItem.getLastTime());

        return convertView;
    }

    public void addItem(ChatRoomItem item){
        itemList.add(item);
    }

}

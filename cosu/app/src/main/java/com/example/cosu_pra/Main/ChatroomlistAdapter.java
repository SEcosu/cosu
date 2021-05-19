package com.example.cosu_pra.Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosu_pra.DTO.Chatroom;
import com.example.cosu_pra.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//채팅방리스트 리사이클러뷰 어댑터
public class ChatroomlistAdapter extends RecyclerView.Adapter<ChatroomlistAdapter.ItemHolder> {

    private ArrayList<Chatroom>chatroomlist = new ArrayList<>();


    @NonNull
    @Override
    public ChatroomlistAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatroomlistAdapter.ItemHolder holder, int position) {
        holder.onBind(chatroomlist.get(position));
    }

    @Override
    public int getItemCount() {
        return chatroomlist.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView ChatImg;
        private TextView ChatroomName;
        private TextView ChatroomContent;
        public ItemHolder(View view) {
            super(view);

            ChatImg = view.findViewById(R.id.chatImg);
            ChatroomName = view.findViewById(R.id.chatroomName);
            ChatroomContent = view.findViewById(R.id.chatContent);
            TextView ChatTime = view.findViewById(R.id.chatTime);

            //채팅방 현재 시간 표시
            long mNow = System.currentTimeMillis();
            Date mDate = new Date(mNow);
            SimpleDateFormat simpleDate = new SimpleDateFormat("MM:dd");
            String getTime = simpleDate.format(mDate);
            ChatTime.setText(getTime);

        }
        //채팅방 사진, 이름, 내용셋팅
        void onBind(Chatroom chatroom) {
           ChatroomName.setText(chatroom.getUserList().toString());
        }
    }
}

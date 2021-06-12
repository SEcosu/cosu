package com.example.cosu_pra;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosu_pra.ConnectFB.HelpChatting;
import com.example.cosu_pra.DTO.ChatData;
import com.example.cosu_pra.DTO.ProjectPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {

    HelpChatting chatHelper;
    String roomID;
    SharedPreferences sh_Pref;
    String userEmail;
    List<MessageItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ImageButton End = findViewById(R.id.project_end);
        ImageButton Exit = findViewById(R.id.exit);

        ImageButton send = findViewById(R.id.sendbtn);
        EditText mc = findViewById(R.id.message_content);
        roomID = getIntent().getStringExtra("roomID");
        sh_Pref = getSharedPreferences("Login Credentials ", MODE_PRIVATE);

        //chatHelper.getMessages()
        chatHelper = new HelpChatting();

        userEmail = sh_Pref.getString("Email", "");


        //Connect recyclerview
        RecyclerView rv = (RecyclerView) findViewById(R.id.message_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(ChattingActivity.this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();


        chatHelper.waitMSG(roomID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value,
                                @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("test", "Listen failed.", error);
                    return;
                }
                for (QueryDocumentSnapshot doc : value) {

                    ChatData MSG = doc.toObject(ChatData.class);

                    Log.d("test",MSG.getMsg());
                    adapter.add(MSG);

                }
                adapter.notifyDataSetChanged();
                rv.setAdapter(adapter);            }
        });


        rv.setAdapter(adapter);

        //send button
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String MSG = mc.getText().toString();
                if (mc != null) {
                    ChatData chat = new ChatData(userEmail, MSG);
                    chatHelper.addChat(roomID, chat);
                    Toast.makeText(ChattingActivity.this, "성공", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ChattingActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();


            }
        });
        //send end button
        End.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }


        });
        //send outchattingroom
        End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //3 dot button
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmenu = new PopupMenu(ChattingActivity.this, Exit);
                popupmenu.getMenuInflater().inflate(R.menu.popup1, popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.menu1:

                                break;

                            case R.id.menu2:
                                Toast.makeText(getApplicationContext(), "채팅방 나가기", Toast.LENGTH_SHORT).show();


                                break;

                        }
                        return false;
                    }
                });
                popupmenu.show();
            }
        });


    }

    //To connect message
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ArrayList<ChatData> chats;

        public RecyclerViewAdapter() {
            chats = new ArrayList<>();

        }

        @NonNull
        @Override

        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemmessage, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MessageViewHolder messageViewHolder = ((MessageViewHolder) holder);
            //If I send message
            if(chats.get(position).getUserID().equals(userEmail)) {
                messageViewHolder.message.setText(chats.get(position).getMsg());

                messageViewHolder.lv.setVisibility(View.INVISIBLE);
                messageViewHolder.message.setTextSize(15);
                messageViewHolder.message.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                messageViewHolder.lv_chamessage_main.setGravity(Gravity.RIGHT);
            }
            //If other send message
            else {
                //To someone else's name
                messageViewHolder.name.setText(chats.get(position).getUserID());
                messageViewHolder.lv.setVisibility(View.VISIBLE);
                messageViewHolder.message.setText(chats.get(position).getMsg());
                messageViewHolder.message.setTextSize(15);
                messageViewHolder.lv_chamessage_main.setGravity(Gravity.LEFT);

            }


        }

        @Override
        public int getItemCount() {
            return chats.size();
        }

        public void add(ChatData chatData){
            chats.add(chatData);
        }

        private class MessageViewHolder extends RecyclerView.ViewHolder {
            public TextView message;
            public TextView name;
            public LinearLayout lv;
            public LinearLayout lv_chamessage_main;

            public MessageViewHolder(View view) {
                super(view);
                message = (TextView) view.findViewById(R.id.profile1_content);
                name = (TextView) view.findViewById(R.id.profile1_name);
                lv = (LinearLayout) view.findViewById(R.id.chat1);
                lv_chamessage_main = (LinearLayout) view.findViewById(R.id.lv_chamessage_main);

            }
        }

    }


}
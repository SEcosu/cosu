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


        //채팅 리사이클러뷰 연결
        RecyclerView rv = (RecyclerView) findViewById(R.id.message_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(ChattingActivity.this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();

//        chatHelper.getMessages(roomID).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    Map<String, ProjectPost> comments = new HashMap<String, ProjectPost>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        ChatData MSG = document.toObject(ChatData.class);
//
//                        //MessageItem item = new MessageItem(MSG.getUserID(), MSG.getMsg(), MSG.getTime());
//                        Log.d("test",MSG.getMsg());
//                        adapter.add(MSG);
//                    }
//                }
//                adapter.notifyDataSetChanged();
//                rv.setAdapter(adapter);
//            }
//
//        });

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

        //TODO SEND버튼
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
        //TODO END 버튼 눌렀을때-모집완료
        End.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }


        });
        //TODO 채팅방나가기
        End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //TODO 3 DOT 버튼
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmenu = new PopupMenu(ChattingActivity.this, Exit);
                popupmenu.getMenuInflater().inflate(R.menu.popup1, popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //TODO 벨 버튼 클릭 후 이벤트
                        switch (item.getItemId()) {
                            //알림끄기
                            case R.id.menu1:

                                break;
                            //채팅방 나가기
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

    //채팅리스트 연결 - 메세지
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ArrayList<ChatData> chats;

        public RecyclerViewAdapter() {
            chats = new ArrayList<>();
            //TODO 방이름 가져오기
            //TODO 대화내용 가져오기
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
            //내가 보낸 메세지라면(오른쪽버블 바탕화면으로
            if(chats.get(position).getUserID().equals(userEmail)) {
                messageViewHolder.message.setText(chats.get(position).getMsg());
                //내 메세지 감쳐주는
                messageViewHolder.lv.setVisibility(View.INVISIBLE);
                messageViewHolder.message.setTextSize(15);
                messageViewHolder.message.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                messageViewHolder.lv_chamessage_main.setGravity(Gravity.RIGHT);
            }
            //상대방이 보낸 메세지
            else {
                //상대방이름가져오기
                messageViewHolder.name.setText(chats.get(position).getUserID());
                messageViewHolder.lv.setVisibility(View.VISIBLE);
                //상대방 메세지
                messageViewHolder.message.setText(chats.get(position).getMsg());
                //텍스트 사이즈
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
package com.example.cosu_pra;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {
    ImageButton End = findViewById(R.id.project_end);
    ImageButton Exit = findViewById(R.id.exit);
    ImageButton send = findViewById(R.id.sendbtn);
    EditText mc = findViewById(R.id.message_content);
    HelpChatting chatHelper;
    String roomID;
    SharedPreferences sh_Pref;
    String userEmail;
    List<MessageItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        roomID = getIntent().getStringExtra("roomID");


        //chatHelper.getMessages()
        chatHelper = new HelpChatting();
        ImageButton End = findViewById(R.id.project_end);
        ImageButton Exit = findViewById(R.id.exit);
        userEmail = sh_Pref.getString("Email", "");

        //채팅 리사이클러뷰 연결
        RecyclerView rv = (RecyclerView) findViewById(R.id.message_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(ChattingActivity.this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();

        chatHelper.getMessages(roomID).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, ProjectPost> comments = new HashMap<String, ProjectPost>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ChatData MSG = document.toObject(ChatData.class);

                        MessageItem item = new MessageItem(MSG.getUserID(), MSG.getMsg(), MSG.getTime());
                        items.add(item);
                    }
                    // TODO:어뎁터에 리스트 연결하면 됨
                }
            }

        });

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

                    MessageItem item = new MessageItem(MSG.getUserID(), MSG.getMsg(), MSG.getTime());
                    items.add(item);

                }
            }
        });





        rv.setAdapter(adapter);

        //TODO SEND버튼
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String MSG = mc.getText().toString();
                if (mc != null) {
                    ChatData chat = new ChatData(userEmail, MSG);
                    chatHelper.addChat(userEmail, chat);

                    Toast.makeText(ChattingActivity.this, "성공", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ChattingActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();


            }
        });
        //TODO END 버튼 눌렀을때
        End.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }


        });
        //TODO 채팅방나가기
        End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                            case R.id.menu1:
                                Toast.makeText(getApplicationContext(), "푸시 알림 설정", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu2:
                                Toast.makeText(getApplicationContext(), "진동", Toast.LENGTH_SHORT).show();

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
        Map<String, ProjectPost> comments = new HashMap<String, ProjectPost>();

        public RecyclerViewAdapter() {
            FirebaseDatabase.getInstance().getReference().child("chatrooms").child("comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    comments.clear();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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
//            //내가 보낸 메세지라면(오른쪽버블 바탕화면으로
//            if() {
//                messageViewHolder.message.setText(comments.get(position).message);
//                messageViewHolder.message.setBackgroundResource(R.drawable.bubble2);
//                //내 메세지 감쳐주는
//                messageViewHolder.message.setVisibility(View.INVISIBLE);
//                messageViewHolder.message.setTextSize(15);
//                messageViewHolder.lv_chamessage_main.setGravity(Gravity.RIGHT);
//            }
//            //상대방이 보낸 메세지
//            else {
//                //상대방이름가져오기
//                messageViewHolder.name.setText();
//                messageViewHolder.lv.setVisibility(View.VISIBLE);
//                messageViewHolder.message.setBackgroundResource(R.drawable.bubble1);
//                //상대방 메세지
//                messageViewHolder.message.setText(comments.get(position).message);
//                //텍스트 사이즈
//                messageViewHolder.message.setTextSize(15);
//            }


        }

        @Override
        public int getItemCount() {
            return comments.size();
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
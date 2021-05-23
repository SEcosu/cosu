package com.example.cosu_pra;

import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosu_pra.ConnectFB.HelpChatting;
import com.example.cosu_pra.DTO.ChatData;
import com.example.cosu_pra.DTO.ProjectPost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    List<ChatData> chatList;
    String roomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        roomID = getIntent().getStringExtra("roomID");

        chatList = new ArrayList<>();
        //chatHelper.getMessages()

        ImageButton End = findViewById(R.id.project_end);
        ImageButton Exit = findViewById(R.id.exit);



        //채팅 리사이클러뷰 연결
        RecyclerView rv = (RecyclerView)findViewById(R.id.message_recyclerview) ;
        rv.setLayoutManager(new LinearLayoutManager(ChattingActivity.this));
        rv.setAdapter(new RecyclerViewAdapter());

        //TODO SEND버튼
        send.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                String MSG = mc.getText().toString();
                if(mc!=null){
                    ChatData chatdata  = new ChatData();

                    chatdata.setMsg(MSG);
                    Toast.makeText(ChattingActivity.this, "성공", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ChattingActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();


            }
        });
        //TODO END 버튼 눌렀을때
        End.setOnClickListener(new View.OnClickListener(){

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
                popupmenu.getMenuInflater().inflate(R.menu.popup1,popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //TODO 벨 버튼 클릭 후 이벤트
                        switch(item.getItemId()){
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
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        Map<String, ProjectPost> comments = new HashMap<String, ProjectPost>();
        public RecyclerViewAdapter(){
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
            MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);
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
                message = (TextView)view.findViewById(R.id.profile1_content);
                name = (TextView)view.findViewById(R.id.profile1_name);
                lv = (LinearLayout)view.findViewById(R.id.chat1);
                lv_chamessage_main = (LinearLayout)view.findViewById(R.id.lv_chamessage_main);

            }
        }
    }



}
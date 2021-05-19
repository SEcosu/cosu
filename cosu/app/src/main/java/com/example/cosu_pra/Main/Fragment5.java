package com.example.cosu_pra.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class Fragment5 extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    // 회원가입시 작성한 이메일 값과 비밀번호 값을 저장할 객체 생성
    private TextView editTextEmail;
    private TextView editTextPassword;

    // 회원가입시 작성한 이름 값과 닉네임 값을 저장할 객체 생성
    private TextView editTextName;
    private TextView editTextNickname;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment5, container, false);
        ImageButton btn = v.findViewById(R.id.mypage_btn);
        Button profilebtn = v.findViewById(R.id.look_profilebtn);

        final LinearLayout[] dialogView = new LinearLayout[1];

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmenu = new PopupMenu(getContext(), btn);
                popupmenu.getMenuInflater().inflate(R.menu.popup,popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //TODO 벨 버튼 클릭 후 이벤트
                        switch(item.getItemId()){
                            case R.id.action_menu1:
                                Toast.makeText(getContext(), "푸시 알림 설정", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_menu2:
                                Toast.makeText(getContext(), "진동", Toast.LENGTH_SHORT).show();

                                break;
                            case R.id.action_menu3:
                                Toast.makeText(getContext(), "알림음", Toast.LENGTH_SHORT).show();

                                break;
                            case R.id.action_menu4:
                                Toast.makeText(getContext(), "알림 ON/OFF", Toast.LENGTH_SHORT).show();

                                break;
                        }
                        return false;
                    }
                });
                popupmenu.show();
            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener(){

            //TODO 프로필 수정
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                dialogView[0] = (LinearLayout) View.inflate(getActivity(), R.layout.dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("프로필 정보 수정");
                dlg.setView(dialogView[0]);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();

            }
        });

        return v;
    }

    //내가 쓴 글 불러오기
    public Query getQuery(FirebaseFirestore databaseReference) {
        firebaseAuth = FirebaseAuth.getInstance();
        String myUserId = firebaseAuth.getCurrentUser().getUid();

        return databaseReference.collection("Projects").whereEqualTo("writer", myUserId);
    }

    //회원정보를 보여주는 showInfo 메서드
    private void showInfo(){
        // 현재 로그인이 되어있는 사용자를 가져옴
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // firestore의 collection 경로를  "users"로 설정
        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 파이어스토어에서 데이터를 가져오는 것을 성공했을 때
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                assert user != null;
                                    // editText에 파이어스토에 저장된 값을 setText해줌
                                    editTextEmail.setText(document.getData().get("Email").toString());
                                    editTextName.setText(document.getData().get("Name").toString());
                                    editTextNickname.setText(document.getData().get("Nickname").toString());
                                    editTextPassword.setText(document.getData().get("Password").toString());
                            }
                        }
                    }
                });
    }

}


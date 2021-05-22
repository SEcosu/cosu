package com.example.cosu_pra.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.*;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cosu_pra.MyCommentActivity;
import com.example.cosu_pra.MylikeActivity;
import com.example.cosu_pra.MypostActivity;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

        lv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SEcosu/cosu"));
                startActivity(intent);
            }
        });

public class Fragment5 extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    private String _userID;


    //고객센터 링크연결
    LinearLayout lv2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        _userID = MainActivity.client.getUserID();
        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
        _userID = firebaseAuth.getCurrentUser().getUid();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment5, container, false);
        ImageButton btn = v.findViewById(R.id.mypage_btn);
        ImageButton mypostbtn= v.findViewById(R.id.mypost);
        ImageButton mylikebtn =  v.findViewById(R.id.mylike);
        ImageButton mycommentbtn= v.findViewById(R.id.mycomment);

        Button profilebtn = v.findViewById(R.id.look_profilebtn);

        final LinearLayout[] dialogView = new LinearLayout[1];

        TextView nickNameView = v.findViewById(R.id.mypage_nickname);
        TextView nameView = v.findViewById(R.id.mypage_nickname);  // fill out later if you want to display
        TextView emailView = v.findViewById(R.id.mypage_nickname);  // fill out later if you want to display
        showInfo(nameView, nickNameView, emailView);

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

                        String _name = ((TextView)dialogView[0].findViewById(R.id.ename)).getText().toString();
                        String _nickname = ((TextView)dialogView[0].findViewById(R.id.enickname)).getText().toString();
                        String _pw = ((TextView)dialogView[0].findViewById(R.id.epw)).getText().toString();
                        String _pwc = ((TextView)dialogView[0].findViewById(R.id.epwc)).getText().toString();

                        if (_name.equals("")) {
                            // empty field, reject
                            return;
                        }
                        if (_nickname.equals("")) {
                            // empty field, reject
                            return;
                        }
                        if (_pw.equals("")) {
                            // empty field, reject
                            return;
                        }
                        if (_pwc.equals("")) {
                            // empty field, reject
                            return;
                        }

                        if (!_pw.equals(_pwc)) {
                            // this is where you display ERROR that they dont match
                            return;
                        }

                        int _on_rejection_error = updateInfo(
                                _userID,
                                _name,
                                _nickname,
                                _pw,
                                _pwc
                        );
                        if (_on_rejection_error == 1) {
                            // wrong password
                            return;
                        }
                        else if (_on_rejection_error == 2) {
                            // no connection
                            return;
                        }
                        else if (_on_rejection_error == 3) {
                            // some other error
                            return;
                        }
                        // if it does not return above, then error code = 0, and success

                        showInfo(nameView, nickNameView, emailView);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();

            }
        });
        mypostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypostActivity.class);
                startActivity(intent);
            }
        });

        mylikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MylikeActivity.class);
                startActivity(intent);
            }
        });

        mycommentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCommentActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private int updateInfo(String _userID, String _name, String _nickname, String _password,String _passwordConfirm) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put("Nickname", _nickname);
        data.put("Name", _name);
        data.put("Password", _password);
        data.put("PasswordConfirm", _passwordConfirm);

        db.collection("users").document(_userID).set(
                data,
                SetOptions.merge()
        );

        return 0;
    }

    //회원정보를 보여주는 showInfo 메서드
    private void showInfo(TextView nameView, TextView emailView, TextView nickNameView){
        // 현재 로그인이 되어있는 사용자를 가져옴
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // firestore의 collection 경로를  "users"로 설정

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 파이어스토어에서 데이터를 가져오는 것을 성공했을 때
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                if (!document.getId().equals(_userID)) continue;
                                nickNameView.setText(document.getData().get("Nickname").toString());
//                                nameView.setText(document.getData().get("Name").toString());
//                                emailView.setText(document.getData().get("Email").toString());
                                break;
                            }
                        }
                    }
                });
    }

}


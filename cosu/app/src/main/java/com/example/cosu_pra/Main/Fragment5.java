package com.example.cosu_pra.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.cosu_pra.AdminActivity;
import com.example.cosu_pra.MyCommentActivity;
import com.example.cosu_pra.MylikeActivity;
import com.example.cosu_pra.MypostActivity;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Fragment5 extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    SharedPreferences sh_Pref;
    private String _userID;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
        _userID = firebaseAuth.getCurrentUser().getUid();

        sh_Pref = this.getActivity().getSharedPreferences("Login Credentials ", MODE_PRIVATE);
        _userID = sh_Pref.getString("Nickname", "");

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment5, container, false);
        ImageButton btn = v.findViewById(R.id.mypage_btn);
        ImageButton mypostbtn= v.findViewById(R.id.mypost);
        ImageButton mylikebtn =  v.findViewById(R.id.mylike);
        ImageButton mycommentbtn= v.findViewById(R.id.mycomment);
        ImageButton logoutbtn = v.findViewById(R.id.logoutbtn);
        ImageButton withdrawbtn = v.findViewById(R.id.withdrawbtn);

        Button profilebtn = v.findViewById(R.id.look_profilebtn);

        final LinearLayout[] dialogView = new LinearLayout[1];

        TextView nickNameView = v.findViewById(R.id.mypage_nickname);
        TextView nameView = v.findViewById(R.id.mypage_nickname);  // fill out later if you want to display
        TextView emailView = v.findViewById(R.id.mypage_nickname);  // fill out later if you want to display

        nickNameView.setText(_userID);
        //showInfo(nameView, nickNameView, emailView);

        //To link Customer Service button
        ImageButton center =  v.findViewById(R.id.mypagelist2);

        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SEcosu/cosu"));
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmenu = new PopupMenu(getContext(), btn);
                popupmenu.getMenuInflater().inflate(R.menu.popup,popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.action_menu2:
                                Intent intent = new Intent(getActivity(),  AdminActivity.class);
                                startActivity(intent);
                                break;

                        }
                        return false;
                    }
                });
                popupmenu.show();
            }
        });
        //To logout button
        logoutbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        //To Withdrawal
        withdrawbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getCurrentUser().delete();
            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener(){

            //To Modify Profile
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                dialogView[0] = (LinearLayout) View.inflate(getActivity(), R.layout.dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("????????? ?????? ??????");
                dlg.setView(dialogView[0]);
                dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
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
                dlg.setNegativeButton("??????", null);
                dlg.show();

            }
        });
        //To see my post
        mypostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypostActivity.class);
                startActivity(intent);
            }
        });
        //To see mylike
        mylikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MylikeActivity.class);
                startActivity(intent);
            }
        });
        //To see my comment
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
        data.put("nickName", _nickname);
        data.put("realName", _name);
      //  data.put("Password", _password);
      //  data.put("PasswordConfirm", _passwordConfirm);

        db.collection("users").document(_userID).set(
                data,
                SetOptions.merge()
        );

        return 0;
    }


    private void showInfo(TextView nameView, TextView emailView, TextView nickNameView){
        // ?????? ???????????? ???????????? ???????????? ?????????
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // firestore??? collection ?????????  "users"??? ??????

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // ???????????????????????? ???????????? ???????????? ?????? ???????????? ???
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                if (!document.getId().equals(_userID)) continue;
                                nickNameView.setText(document.getData().get("nickName").toString());
//                                nameView.setText(document.getData().get("Name").toString());
//                                emailView.setText(document.getData().get("Email").toString());
                                break;
                            }
                        }
                    }
                });
    }


}


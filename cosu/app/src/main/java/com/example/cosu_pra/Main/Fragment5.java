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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cosu_pra.R;


public class Fragment5 extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment5, container, false);
        ImageButton btn = v.findViewById(R.id.mypage_btn);
        Button profilebtn = v.findViewById(R.id.look_profilebtn);
        EditText nname;
        EditText nemail;
        EditText npw;
        EditText nid;
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



}


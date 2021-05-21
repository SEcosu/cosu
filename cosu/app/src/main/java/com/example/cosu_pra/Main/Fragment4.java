package com.example.cosu_pra.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cosu_pra.Adapter.ListViewAdatper;
import com.example.cosu_pra.ChattingActivity;
import com.example.cosu_pra.DTO.ListViewItem;
import com.example.cosu_pra.R;

import java.util.List;
//참고 : https://itqna.net/questions/87594/how-create-custom-listview-inside-fragment

public class Fragment4 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment4, container, false);

        ListView chatroomlist = (ListView)v.findViewById(R.id.mainlist);
        List<ListViewItem> listViewItemList = null;
        ListViewAdatper listviewAdapter = new ListViewAdatper(getActivity(), listViewItemList);

        return v;
    }
    //TODO 단톡방 클릭 이벤트
    public void onListItemClick(ListView l, View v, int position, long id){
        String strText = (String) l.getItemAtPosition(position);
        Log.d("listview: ", position + ": " +strText);
        Intent intent = new Intent(getActivity(), ChattingActivity.class);
        startActivity(intent);
    }



}



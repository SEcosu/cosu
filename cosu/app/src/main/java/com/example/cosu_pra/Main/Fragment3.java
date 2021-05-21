package com.example.cosu_pra.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Fragment3 extends Fragment {

    GridView gridView;
    PostCategoryAdapter adapter;
    FloatingActionButton plus_btn;
    Button search_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment1, container, false);

        gridView = (GridView) v.findViewById(R.id.frg1_gridview);
        adapter = new PostCategoryAdapter();
        gridView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.joystick),
                "질문1");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.bank),
                "Fintech");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.innovation),
                "AI");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.apps),
                "App");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.united),
                "Side Project");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.www),
                "Web");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.competition),
                "Competition");

        plus_btn = v.findViewById(R.id.plus_btn);
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlusActivity.class);
                intent.putExtra("collection", HelpPosting.QNA);
                startActivity(intent);
            }
        });

        search_btn = v.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여기가 글 리스트 보여주는 곳
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("collection", HelpPosting.QNA);
                startActivity(intent);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("collection", HelpPosting.QNA);
                String[] category = getResources().getStringArray(R.array.category);
                intent.putExtra("category",category[position+1]);

                startActivity(intent);
            }
        });
        return v;
    }

}

package com.example.cosu_pra.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cosu_pra.R;

import java.util.ArrayList;


public class PostListAdapter extends BaseAdapter {

    private ArrayList<PostListItem> listViewItemList = new ArrayList<PostListItem>() ;
    String collection;

    // ListViewAdapter의 생성자
    public PostListAdapter() {

    }

    public PostListAdapter(String collection) {
        this.collection = collection;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.study_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView image = (ImageView) convertView.findViewById(R.id.study_image) ;
        TextView title = (TextView) convertView.findViewById(R.id.title) ;
        TextView people = (TextView) convertView.findViewById(R.id.people) ;
        TextView date = (TextView) convertView.findViewById(R.id.date) ;
        TextView good = (TextView) convertView.findViewById(R.id.good) ;
        TextView comment = (TextView) convertView.findViewById(R.id.comment) ;
        LinearLayout list_item = convertView.findViewById(R.id.list_item);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        PostListItem studyItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        image.setImageDrawable(studyItem.getImage());
        title.setText("제목 : " +studyItem.getTitle());
        people.setText("인원 : " +studyItem.getPeople());
        date.setText("기간 : " +studyItem.getDate());
        good.setText(studyItem.getGood());
        comment.setText(studyItem.getComment());

//        list_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // TODO: 디테일로 들어가는거
//                Intent intent = new Intent(context,DetailActivity.class);
//                intent.putExtra("postID",listViewItemList.get(pos).getPostID());
//                intent.putExtra("collection",collection);
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public PostListItem getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable image, String title, String people, String date , String comment , String good) {
        PostListItem item = new PostListItem();

        item.setImage(image);
        item.setTitle(title);
        item.setPeople(people);
        item.setDate(date);
        item.setComment(comment);
        item.setGood(good);
        listViewItemList.add(item);
    }
    public void addItem(PostListItem item) {
        listViewItemList.add(item);
    }

}

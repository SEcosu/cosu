package com.example.cosu_pra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cosu_pra.R;

import java.util.ArrayList;

public class ReportAdatper extends BaseAdapter {
    ArrayList<ReportListItem> items = new ArrayList<ReportListItem>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ReportListItem reportListItem= items.get(position);
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reportitem, parent, false);

        }
        TextView postcontent = convertView.findViewById(R.id.report_content);
        TextView postwriter = convertView.findViewById(R.id.report_writer);

        postcontent.setText(reportListItem.getContent());
        postwriter.setText(reportListItem.getWriter());
        return convertView;
    }
    public void addItem(ReportListItem item){
        items.add(item);
    }
}
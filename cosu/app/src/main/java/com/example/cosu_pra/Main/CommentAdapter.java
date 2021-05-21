package com.example.cosu_pra.Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosu_pra.R;

import java.util.ArrayList;
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<Comment_sub> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.comment_item, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment_sub item = items.get(position);
        holder.setItem(item);
    }
    public void addItem(Comment_sub item){
        items.add(item);
    }
    public void setItems(ArrayList<Comment_sub> items){
        this.items = items;
    }
    public Comment_sub getItem(int position){
        return items.get(position);
    }
    public void setItem(int position, Comment_sub item){
        items.set(position,item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView input_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            input_comment = itemView.findViewById(R.id.input_comment);

        }

        public void setItem(Comment_sub item){
            input_comment.setText(item.getComment());
        }
    }
}
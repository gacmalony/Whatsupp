package com.example.whatsupp.views.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsupp.R;
import com.example.whatsupp.databinding.ItemCardBinding;
import com.example.whatsupp.model.ChatGroup;
import com.example.whatsupp.views.ChatActivity;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.GroupViewHolder> {


    private ArrayList<ChatGroup> arrayList;

    public MyAdapter(ArrayList arrayList) {
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
        R.layout.item_card,
        parent,
        false);
                return new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        ChatGroup currentUser = arrayList.get(position);
        holder.itemCardBinding.setChatGroup(currentUser);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{
        private ItemCardBinding itemCardBinding;

        public GroupViewHolder(ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding = itemCardBinding;

            itemCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    ChatGroup clickedChatGroup = arrayList.get(position);

                    Intent i = new Intent(v.getContext(), ChatActivity.class);
                        i.putExtra("GROUP_NAME", clickedChatGroup.getGroupName());
                    v.getContext().startActivity(i);

        }
    });}
}}

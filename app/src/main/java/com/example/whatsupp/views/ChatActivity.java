package com.example.whatsupp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.whatsupp.R;
import com.example.whatsupp.databinding.ActivityChatBinding;
import com.example.whatsupp.model.ChatMessage;
import com.example.whatsupp.viewmodel.MyViewModel;
import com.example.whatsupp.views.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;


    private List<ChatMessage> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        String groupName = getIntent().getStringExtra("GROUP_NAME");
        myViewModel.getGroupMessages(groupName).observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                list = new ArrayList<>();
                list.addAll(chatMessages);

                chatAdapter = new ChatAdapter(list, getApplicationContext());

                recyclerView.setAdapter(chatAdapter);
                chatAdapter.notifyDataSetChanged();

                int latest_position = chatAdapter.getItemCount() -1;
                if (latest_position >= 0){
                    recyclerView.smoothScrollToPosition(latest_position);
                }

            }
        });

        binding.setVModel(myViewModel);

        binding.sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = binding.edittextChatMessage.getText().toString();
                myViewModel.sendMessage(msg, groupName);

                binding.edittextChatMessage.getText().clear();
            }
        });

    }
}
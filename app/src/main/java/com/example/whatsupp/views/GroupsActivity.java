package com.example.whatsupp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsupp.R;
import com.example.whatsupp.databinding.ActivityGroupsBinding;
import com.example.whatsupp.generated.callback.OnClickListener;
import com.example.whatsupp.model.ChatGroup;
import com.example.whatsupp.viewmodel.MyViewModel;
import com.example.whatsupp.views.adapter.MyAdapter;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private ArrayList<ChatGroup> chatGroupArrayList;
    private RecyclerView recyclerView;

    private MyAdapter myAdapter;
    private ActivityGroupsBinding activityGroupsBinding;
    private MyViewModel myViewModel;


    private Dialog chatGroupDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);


        activityGroupsBinding = DataBindingUtil.setContentView(this, R.layout.activity_groups);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        recyclerView = activityGroupsBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myViewModel.getGroupList().observe(this, new Observer<List<ChatGroup>>() {
            @Override
            public void onChanged(List<ChatGroup> chatGroups) {
                chatGroupArrayList = new ArrayList<>();
                chatGroupArrayList.addAll(chatGroups);
                myAdapter = new MyAdapter(chatGroupArrayList);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
                Log.w("TAGK","GroupsAct savepoint 1");

            }
        });

        activityGroupsBinding.fab231.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    public void showDialog(){
        chatGroupDialog = new Dialog(this);
        chatGroupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.w("TAGK","GroupsAct savepoint 2");



        View view = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout, null);

        chatGroupDialog.setContentView(view);
        chatGroupDialog.show();










        Button btn = view.findViewById(R.id.submit_btn);
        EditText txt = view.findViewById(R.id.chat_group_edt);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = txt.getText().toString();

                Toast.makeText(getApplicationContext(), "Your chat group name is: "+groupName, Toast.LENGTH_SHORT).show();

                myViewModel.createNewChatGroup(groupName);

                chatGroupDialog.dismiss();


            }
        });





    }
}

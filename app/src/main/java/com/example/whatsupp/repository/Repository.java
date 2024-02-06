package com.example.whatsupp.repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsupp.model.ChatGroup;
import com.example.whatsupp.model.ChatMessage;
import com.example.whatsupp.views.GroupsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {


    MutableLiveData<List<ChatGroup>> mutableLiveData;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    DatabaseReference messageReference;

    MutableLiveData<List<ChatMessage>> messagesLiveData;

    public MutableLiveData<List<ChatMessage>> getMessagesLiveData(String groupName) {
        messageReference = firebaseDatabase.getReference().child(groupName);
        List<ChatMessage> messagesList = new ArrayList<>();

        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesList.clear();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                    messagesList.add(message);
                }
                messagesLiveData.postValue(messagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return messagesLiveData;
    }



    public Repository() {
        this.mutableLiveData = new MutableLiveData<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        messagesLiveData = new MutableLiveData<>();
    }

    //Auth
    public void firebaseAuthentication(Context context) {
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(context, GroupsActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    }
                });
    }

    public String getCurrentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public void SignOut() {
        FirebaseAuth.getInstance().signOut();
    }


    //Getting Chat Groups from Realtime Database
    public MutableLiveData<List<ChatGroup>> getDataFromDatabase() {
        List<ChatGroup> groupList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupList.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    ChatGroup group = new ChatGroup(datasnapshot.getKey());
                    groupList.add(group);

                }
                mutableLiveData.postValue(groupList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mutableLiveData;
    }

    public void createNewChatGroup(String groupName) {
        reference.child(groupName).setValue(groupName);
    }


    public void sendMessage(String messageText, String chatGroup) {

        DatabaseReference ref = firebaseDatabase
                .getReference(chatGroup);


        if (!messageText.trim().equals("")) {
            ChatMessage msg = new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    messageText,
                    System.currentTimeMillis()
            );
        Log.w("TAGK",messageText.toString());
        Log.w("TAGK","Nothing");

            String randomKey = ref.push().getKey();

            ref.child(randomKey).setValue(msg);

        }


    }
}

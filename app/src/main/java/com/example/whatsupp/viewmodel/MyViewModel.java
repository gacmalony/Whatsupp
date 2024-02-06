package com.example.whatsupp.viewmodel;


import android.view.View;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsupp.model.ChatGroup;
import com.example.whatsupp.model.ChatMessage;
import com.example.whatsupp.repository.Repository;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    Repository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }


    public void signUpAnonymousUser(){
        Context c = this.getApplication();
        repository.firebaseAuthentication(c);
    }

    public String currentUserId(){
        return repository.getCurrentUserId();

    }

    public void signOut(){
        repository.SignOut();
    }

    public MutableLiveData<List<ChatGroup>> getGroupList(){
        return repository.getDataFromDatabase();

    }

    public MutableLiveData<List<ChatMessage>> getGroupMessages(String groupName){
        return repository.getMessagesLiveData(groupName);
    }

    public void createNewChatGroup(String group){
        repository.createNewChatGroup(group);
    }


    public void sendMessage(String msg, String group){
        repository.sendMessage(msg, group);

    }
}

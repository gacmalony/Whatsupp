package com.example.whatsupp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.whatsupp.R;
import com.example.whatsupp.databinding.ActivityLoginBinding;
import com.example.whatsupp.viewmodel.MyViewModel;
import com.google.firebase.database.DataSnapshot;

import kotlin.Unit;

public class Login extends AppCompatActivity {

    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setVModel(myViewModel);

    }

}

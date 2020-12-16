package com.example.asproj;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asproj.logic.MainActivityLogic;
import com.example.asproj.logic.MainActivityLogic.ActivityProvider;

import org.chen.common.ui.component.ChBaseActivity;

public class MainActivity extends ChBaseActivity implements ActivityProvider{

    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityLogic = new MainActivityLogic(this,savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }

}

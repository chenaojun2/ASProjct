package com.example.asproj;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asproj.http.ApiFactory;
import com.example.asproj.http.api.TestApi;
import com.example.asproj.logic.MainActivityLogic;
import com.example.asproj.logic.MainActivityLogic.ActivityProvider;
import com.google.gson.JsonObject;

import org.chen.cibrary.log.ChLog;
import org.chen.cibrary.restful.ChCallBack;
import org.chen.cibrary.restful.ChResponse;
import org.chen.common.ui.component.ChBaseActivity;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends ChBaseActivity implements ActivityProvider{

    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityLogic = new MainActivityLogic(this,savedInstanceState);

        ApiFactory.INSTANCE.create(TestApi.class).listCities("imooc")
                .enqueue(new ChCallBack<JsonObject>() {
                    @Override
                    public void onSuccess(@NotNull ChResponse<JsonObject> response) {

                    }

                    @Override
                    public void onFailed(@NotNull Throwable throwable) {
                        ChLog.at("MainActivity",throwable);
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }

}

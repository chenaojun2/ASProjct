package com.example.asproj.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asproj.R;

import org.chen.cibrary.executor.ChExecutor;
import org.chen.common.ui.component.ChBaseFragment;

public class HomeFragment extends ChBaseFragment {
    private boolean paused = false;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutView.findViewById(R.id.profile).setOnClickListener(v -> {
            for (int priority = 0; priority < 20; priority++) {
                int finalPriority = priority;
                ChExecutor.INSTANCE.execute(priority, () -> {
                    try {
                        Thread.sleep(2000 - finalPriority * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        });


        layoutView.findViewById(R.id.vip).setOnClickListener(v -> {
            if (paused) {
                ChExecutor.INSTANCE.resume();
            } else {
                ChExecutor.INSTANCE.pause();
            }
            paused = !paused;
        });


        layoutView.findViewById(R.id.authentication).setOnClickListener(v -> {
            ChExecutor.INSTANCE.execute(new ChExecutor.Callable<String>() {
                @Override
                public void onCompleted(String result) {
                    Log.d("MainActivity", "onCompleted: 当前线程是：" + Thread.currentThread().getName());
                    Log.d("MainActivity", "onCompleted: 任务结果是：" + result);
                }

                @Override
                public String onBackground() {
                    Log.d("MainActivity", "onBackground: -当前线程：" + Thread.currentThread().getName());
                    return "我是异步任务的结果";
                }
            });
        });
    }
}

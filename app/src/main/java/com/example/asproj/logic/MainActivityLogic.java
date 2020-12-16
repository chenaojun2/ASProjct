package com.example.asproj.logic;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.example.asproj.R;
import com.example.asproj.fragment.CategoryFragment;
import com.example.asproj.fragment.FavoriteFragment;
import com.example.asproj.fragment.HomeFragment;
import com.example.asproj.fragment.ProfileFragment;
import com.example.asproj.fragment.RecommendFragment;

import org.chen.chui.tab.bottom.ChTabBottomInfo;
import org.chen.chui.tab.bottom.ChTabBottomLayout;
import org.chen.chui.tab.common.IChTabLayout;
import org.chen.common.tab.ChFragmentTabView;
import org.chen.common.tab.ChTabViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivityLogic {

    private ChFragmentTabView fragmentTabView;
    private ChTabBottomLayout chTabBottomLayout;
    private List<ChTabBottomInfo<?>> infoList;
    private ActivityProvider activityProvider;
    private int currentItemIndex;
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.activityProvider = activityProvider;
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
        initTabBottom();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);
    }

    public ChFragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public ChTabBottomLayout getChTabBottomLayout() {
        return chTabBottomLayout;
    }

    public List<ChTabBottomInfo<?>> getInfoList() {
        return infoList;
    }

    private void initTabBottom() {
        chTabBottomLayout = activityProvider.findViewById(R.id.tab_bottom_layout);
        chTabBottomLayout.setTabAlpha(0.85f);
        infoList = new ArrayList<>();
        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);
        ChTabBottomInfo homeInfo = new ChTabBottomInfo<Integer>(
                "首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor
        );
        homeInfo.fragment = HomeFragment.class;
        ChTabBottomInfo infoFavorite = new ChTabBottomInfo<Integer>(
                "收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );
        infoFavorite.fragment = FavoriteFragment.class;
        ChTabBottomInfo infoCategory = new ChTabBottomInfo<Integer>(
                "分类",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_category),
                null,
                defaultColor,
                tintColor
        );
        infoCategory.fragment = CategoryFragment.class;
        ChTabBottomInfo infoRecommend = new ChTabBottomInfo<Integer>(
                "推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor
        );
        infoRecommend.fragment = RecommendFragment.class;
        ChTabBottomInfo infoProfile = new ChTabBottomInfo<Integer>(
                "我的",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_profile),
                null,
                defaultColor,
                tintColor
        );
        infoProfile.fragment = ProfileFragment.class;
        infoList.add(homeInfo);
        infoList.add(infoFavorite);
        infoList.add(infoCategory);
        infoList.add(infoRecommend);
        infoList.add(infoProfile);
        chTabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();
        chTabBottomLayout.addTabSelectedChangeListener(new IChTabLayout.OnTabSelectedListener<ChTabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @NonNull ChTabBottomInfo<?> prevInfo, @NonNull ChTabBottomInfo<?> nextInfo) {
                fragmentTabView.setCurrentPosition(index);
                currentItemIndex = index;
            }
        });
        chTabBottomLayout.defaultSelected(infoList.get(currentItemIndex));
    }

    private void initFragmentTabView() {
        ChTabViewAdapter chTabViewAdapter = new ChTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        fragmentTabView.setAdapter(chTabViewAdapter);
    }


    public interface ActivityProvider {

        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }

}

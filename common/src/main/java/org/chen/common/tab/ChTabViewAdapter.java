package org.chen.common.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.chen.chui.tab.bottom.ChTabBottomInfo;

import java.util.List;

public class ChTabViewAdapter {

    private List<ChTabBottomInfo<?>> mInfoList;
    private Fragment mCurFragment;
    private FragmentManager mFragmentManager;

    public ChTabViewAdapter(FragmentManager fragmentManager,List<ChTabBottomInfo<?>> infoList ) {
        this.mInfoList = infoList;
        this.mFragmentManager = fragmentManager;
    }

    public void instantiateItem(View container, int position){
        FragmentTransaction mCurTransaction = mFragmentManager.beginTransaction();
        if(mCurFragment != null){
            mCurTransaction.hide(mCurFragment);
        }
        String name = container.getId()+":"+position;
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if(fragment != null){
            mCurTransaction.show(fragment);
        } else {
            fragment = getItem(position);
            if(!fragment.isAdded()){
                mCurTransaction.add(container.getId(),fragment,name);
            }
        }
        mCurFragment = fragment;
        mCurTransaction.commitAllowingStateLoss();
    }

    public Fragment getCurrentFragment() {
        return mCurFragment;
    }

    public Fragment getItem(int position){
        try {
            return mInfoList.get(position).fragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount(){
        return mInfoList == null ? 0 : mInfoList.size();
    }
}

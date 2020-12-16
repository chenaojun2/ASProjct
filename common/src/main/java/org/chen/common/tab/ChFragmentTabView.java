package org.chen.common.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChFragmentTabView extends FrameLayout {

    private ChTabViewAdapter mAdapter;
    private int currentPosition;

    public ChFragmentTabView(@NonNull Context context) {
        super(context);
    }

    public ChFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChTabViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ChTabViewAdapter mAdapter) {
        if(this.mAdapter != null || mAdapter==null){
            return;
        }
        this.mAdapter = mAdapter;
        currentPosition = -1;
    }

    public void setCurrentPosition(int position) {
        if(position < 0 || position >= mAdapter.getCount()){
            return;
        }
        if(currentPosition != position){
            this.currentPosition = position;
            mAdapter.instantiateItem(this,position);
        }
    }

    public int getCurrentItem(){
        return currentPosition;
    }

    public Fragment getCurrentFragment(){
        if(this.mAdapter == null){
            throw new IllegalArgumentException("please call setAdapter first");
        }
        return mAdapter.getCurrentFragment();
    }
}

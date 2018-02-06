package com.zhou.xin.adapter.base;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhou on 2018/2/6.
 */

public class GridsSpacesItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public GridsSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;


    }
}

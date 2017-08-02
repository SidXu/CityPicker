package com.sid.citypicker.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sid.citypicker.R;


/**
 * Created by dell on 2016/9/30.
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpanCount;
    private int mSpace;

    public DividerGridItemDecoration(Context context, int spanCount) {
        if (context != null) {
            mSpace = (int) context.getResources().getDimension(R.dimen.space_width);
        }
        this.mSpanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (outRect == null || parent == null) {
            return;
        }
        int pos = parent.getChildLayoutPosition(view);
        outRect.right = mSpace;
        outRect.left = mSpace;
        if (pos >= mSpanCount) {
            outRect.top = mSpace * 2;
        }
    }
}

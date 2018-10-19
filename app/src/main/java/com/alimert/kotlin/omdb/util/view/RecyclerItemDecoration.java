package com.alimert.kotlin.omdb.util.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alimert.kotlin.omdb.R;

public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private final int decorationHeight;

    public RecyclerItemDecoration(Context context) {
        decorationHeight = context.getResources()
                .getDimensionPixelSize(R.dimen.recycler_item_decoration_height);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if ((parent != null) && (view != null)) {
            int itemPosition = parent.getChildAdapterPosition(view);
            int totalCount = parent.getAdapter().getItemCount();

            if (itemPosition >= 0 && itemPosition < totalCount - 1)
                outRect.bottom = decorationHeight;
        }
    }
}

package it.carlom.stikkyheader.core;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class StikkyHeaderRecyclerView extends StikkyHeader {

    private RecyclerView mRecyclerView;
    private int mScrolledY;

    StikkyHeaderRecyclerView(final Context context, final RecyclerView recyclerView, final View header, final int mMinHeightHeader, final HeaderAnimator headerAnimator) {

        this.mContext = context;
        this.mRecyclerView = recyclerView;
        this.mHeader = header;
        this.mMinHeightHeader = mMinHeightHeader;
        this.mHeaderAnimator = headerAnimator;
        this.mScrolledY = 0;

        init();
    }


    private void init() {
        measureHeaderHeight();
        setupAnimator();
        setupOnScrollListener();
    }

    @Override
    protected void setHeightHeader(int heightHeader) {
        super.setHeightHeader(heightHeader);

        setupItemDecorator();
    }

    private void setupItemDecorator() {

        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {

            int orientation = ((GridLayoutManager) layoutManager).getOrientation();

            switch (orientation) {
                case LinearLayoutManager.VERTICAL:

                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);

                            int position = parent.getChildPosition(view);

                            if (position < ((GridLayoutManager) layoutManager).getSpanCount()) {
                                outRect.top = mHeightHeader;
                            }
                        }
                    });

                    break;

                case LinearLayoutManager.HORIZONTAL:
                    //TODO
                    break;
            }

        } else if (layoutManager instanceof LinearLayoutManager) {

            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();

            switch (orientation) {
                case LinearLayoutManager.VERTICAL:

                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);

                            int position = parent.getChildPosition(view);

                            if (position == 0) {
                                outRect.top = mHeightHeader;
                            }
                        }
                    });

                    break;

                case LinearLayoutManager.HORIZONTAL:
                    //TODO
                    break;

            }


        }
    }

    private void setupOnScrollListener() {

        mRecyclerView.setOnScrollListener(new OnScrollListenerRecycler());

    }

    private class OnScrollListenerRecycler extends RecyclerView.OnScrollListener {


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            mScrolledY += dy;
            mHeaderAnimator.onScroll(-mScrolledY);
        }
    }


}

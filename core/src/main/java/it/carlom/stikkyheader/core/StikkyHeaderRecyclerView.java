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
    private OnScrollListenerStikky mOnScrollerListenerStikky;

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
        setupAnimator();
        measureHeaderHeight();
    }

    @Override
    protected void setHeightHeader(int heightHeader) {
        super.setHeightHeader(heightHeader);

        setupItemDecorator();
        // after that we have the height, we can set and init the scrollListener
        setupOnScrollListener();
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

                            int position = parent.getChildAdapterPosition(view);

                            if (position < ((GridLayoutManager) layoutManager).getSpanCount()) {
                                outRect.top = mHeightHeader;
                            }
                        }
                    });

                    break;

                case LinearLayoutManager.HORIZONTAL:
                    //TODO implement horizontal support
                    throw new IllegalStateException("Horizontal LinearLayoutManager not supported");
            }

        } else if (layoutManager instanceof LinearLayoutManager) {

            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();

            switch (orientation) {
                case LinearLayoutManager.VERTICAL:

                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);

                            if (parent.getChildAdapterPosition(view) == 0) {
                                outRect.top = mHeightHeader;
                            }
                        }
                    });

                    break;

                case LinearLayoutManager.HORIZONTAL:
                    //TODO implement horizontal support
                    throw new IllegalStateException("Horizontal LinearLayoutManager not supported");

            }

        }
    }

    private int calculateScrollRecyclerView() {

        final View firstChild = mRecyclerView.getChildAt(0);
        int positionFirstItem = mRecyclerView.getChildAdapterPosition(firstChild);
        int heightDecorator = 0;
        if (positionFirstItem != 0) {
            heightDecorator = mHeightHeader;
        }

        return mRecyclerView.computeVerticalScrollOffset() + heightDecorator;
    }

    private void setupOnScrollListener() {

        if (mOnScrollerListenerStikky != null) {
            mRecyclerView.removeOnScrollListener(mOnScrollerListenerStikky);
        }

        mScrolledY = Integer.MIN_VALUE; // init scroll
        mOnScrollerListenerStikky = new OnScrollListenerStikky();
        mRecyclerView.addOnScrollListener(mOnScrollerListenerStikky);
    }

    private class OnScrollListenerStikky extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (mScrolledY == Integer.MIN_VALUE) {
                mScrolledY = calculateScrollRecyclerView();
            } else {
                mScrolledY += dy;
            }

            mHeaderAnimator.onScroll(-mScrolledY);
        }

    }


}

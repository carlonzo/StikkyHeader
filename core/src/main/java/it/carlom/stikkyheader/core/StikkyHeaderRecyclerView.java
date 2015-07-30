package it.carlom.stikkyheader.core;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


public class StikkyHeaderRecyclerView extends StikkyHeader {

    private RecyclerView mRecyclerView;
    private int mScrolledY;
    private OnScrollListenerStikky mOnScrollerListenerStikky;
    private RecyclerView.ItemDecoration mItemDecoration;

    StikkyHeaderRecyclerView(final Context context, final RecyclerView recyclerView, final View header, final int minHeightHeader, final HeaderAnimator headerAnimator) {
        super(context, recyclerView, header, minHeightHeader, headerAnimator);
        this.mRecyclerView = recyclerView;
    }

    protected void init() {
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

    public RecyclerView.ItemDecoration getItemDecoration() {
        return mItemDecoration;
    }

    private void setupItemDecorator() {

        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();

            switch (orientation) {
                case StaggeredGridLayoutManager.VERTICAL:
                    mItemDecoration = new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);

                            int position = parent.getChildAdapterPosition(view);

                            if (position < ((StaggeredGridLayoutManager) layoutManager).getSpanCount()) {
                                outRect.top = mHeightHeader;
                            }
                        }
                    };
                    mRecyclerView.addItemDecoration(mItemDecoration);

                    break;

                case StaggeredGridLayoutManager.HORIZONTAL:
                    //TODO implement horizontal support
                    throw new IllegalStateException("Horizontal StaggeredGridLayoutManager not supported");
            }

        } else if (layoutManager instanceof GridLayoutManager) {

            int orientation = ((GridLayoutManager) layoutManager).getOrientation();

            switch (orientation) {
                case LinearLayoutManager.VERTICAL:
                    mItemDecoration = new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);

                            int position = parent.getChildAdapterPosition(view);

                            if (position < ((GridLayoutManager) layoutManager).getSpanCount()) {
                                outRect.top = mHeightHeader;
                            }
                        }
                    };
                    mRecyclerView.addItemDecoration(mItemDecoration);

                    break;

                case LinearLayoutManager.HORIZONTAL:
                    //TODO implement horizontal support
                    throw new IllegalStateException("Horizontal LinearLayoutManager not supported");
            }

        } else if (layoutManager instanceof LinearLayoutManager) {

            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();

            switch (orientation) {
                case LinearLayoutManager.VERTICAL:
                    mItemDecoration = new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);

                            if (parent.getChildAdapterPosition(view) == 0) {
                                outRect.top = mHeightHeader;
                            }
                        }
                    };
                    mRecyclerView.addItemDecoration(mItemDecoration);

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

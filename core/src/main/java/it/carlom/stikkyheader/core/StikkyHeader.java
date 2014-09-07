package it.carlom.stikkyheader.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by carlo on 9/6/14.
 */
public class StikkyHeader {

    private final Context mContext;
    private final ListView mListView;

    private View mHeader;
    private final FrameLayout mContainerListView; //TODO useless here?
    private int mMinHeightHeader;
    private final HeaderAnimator mHeaderAnimator;
    private int mHeightHeader;
    private int mMaxHeaderTransaction;
    private View mFakeHeader;
    private StickyOnScrollListener mStickyOnScrollListener;
    private AbsListView.OnScrollListener mDelegateOnScrollListener;


    StikkyHeader(final Context context, final ListView absListView, final View header, final FrameLayout containerListView, final int mMinHeightHeader, final HeaderAnimator headerAnimator) {

        this.mContext = context;
        this.mListView = absListView;
        this.mHeader = header;
        this.mContainerListView = containerListView;
        this.mMinHeightHeader = mMinHeightHeader;
        this.mHeaderAnimator = headerAnimator;

        setFakeHeader();

        init();
    }

    private void init() {

        if (mHeader != null && mHeightHeader == 0) {
            measureHeaderHeight();
            return;
        }

        setAnimator();

        mHeaderAnimator.onAnimatorAttached();

        setStickyOnScrollListener();

    }


    private void measureHeaderHeight() {

        int height = mHeader.getHeight();

        if (height == 0) {
            //waiting for the height
            mHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int height = mHeader.getHeight();
                    if (height > 0) {
                        mHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                        setHeightHeader(height);

                        //call asynchronous. calling init back
                        init();
                    }
                }
            });
        } else {
            setHeightHeader(height);

            init();
        }

    }

    private void setHeightHeader(final int heightHeader) {
        mHeightHeader = heightHeader;

        ViewGroup.LayoutParams lpFakeHeader = mFakeHeader.getLayoutParams();
        lpFakeHeader.height = mHeightHeader;
        mFakeHeader.setLayoutParams(lpFakeHeader);

        ViewGroup.LayoutParams lpHeader = mHeader.getLayoutParams();
        lpHeader.height = mHeightHeader;
        mHeader.setLayoutParams(lpHeader);

        calculateMaxTransaction();
    }

    private void calculateMaxTransaction() {
        mMaxHeaderTransaction = mMinHeightHeader - mHeightHeader;
    }

    private void setFakeHeader() {

        mFakeHeader = new View(mContext);
        mFakeHeader.setVisibility(View.INVISIBLE);

        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        mFakeHeader.setLayoutParams(lp);

        mListView.addHeaderView(mFakeHeader);
    }

    private void setAnimator() {

        mHeaderAnimator.setupAnimator(mHeader, mListView, mMinHeightHeader, mHeightHeader, mMaxHeaderTransaction);

    }


    private void setStickyOnScrollListener() {

        mStickyOnScrollListener = new StickyOnScrollListener();
        mListView.setOnScrollListener(mStickyOnScrollListener);

    }


    private class StickyOnScrollListener implements AbsListView.OnScrollListener {

        private int mScrolledYList = 0;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mDelegateOnScrollListener != null) {
                mDelegateOnScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            mScrolledYList = -calculateScrollYList();
            //notify the animator
            mHeaderAnimator.onScroll(mScrolledYList);

            if (mDelegateOnScrollListener != null) {
                mDelegateOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }

        private int calculateScrollYList() {
            View c = mListView.getChildAt(0);
            if (c == null) {
                return 0;
            }

            //TODO support more than 1 header?

            int firstVisiblePosition = mListView.getFirstVisiblePosition();
            int top = c.getTop();

            int headerHeight = 0;
            if (firstVisiblePosition >= 1) { //TODO >= number of header
                headerHeight = mHeightHeader;
            }

            return -top + firstVisiblePosition * c.getHeight() + headerHeight;
        }

        public int getScrolledY() {
            return mScrolledYList;
        }
    }


    public void setOnScrollListener(final AbsListView.OnScrollListener onScrollListener) {
        mDelegateOnScrollListener = onScrollListener;
    }

    public void setMinHeightHeader(int minHeightHeader) {
        this.mMinHeightHeader = minHeightHeader;
        calculateMaxTransaction();
    }
}

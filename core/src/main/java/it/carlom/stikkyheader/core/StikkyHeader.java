package it.carlom.stikkyheader.core;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;

public class StikkyHeader {

    private final Context mContext;
    private final ListView mListView;

    private View mHeader;
    private int mMinHeightHeader;
    private final HeaderAnimator mHeaderAnimator;
    private int mHeightHeader;
    private int mMaxHeaderTranslation;
    private View mFakeHeader;
    private AbsListView.OnScrollListener mDelegateOnScrollListener;
    private OnStikkyScrollListener mOnStikkyScrollListener;


    StikkyHeader(final Context context, final ListView absListView, final View header, final int mMinHeightHeader, final HeaderAnimator headerAnimator) {

        this.mContext = context;
        this.mListView = absListView;
        this.mHeader = header;
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

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mHeader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                        setHeightHeader(height);

                        //the header is ready! we can init the stikky staff
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

        calculateMaxTranslation();
    }

    private void calculateMaxTranslation() {
        mMaxHeaderTranslation = mMinHeightHeader - mHeightHeader;
    }

    private void setFakeHeader() {

        mFakeHeader = new View(mContext);
        mFakeHeader.setVisibility(View.INVISIBLE);

        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        mFakeHeader.setLayoutParams(lp);

        mListView.addHeaderView(mFakeHeader);
    }

    private void setAnimator() {

        mHeaderAnimator.setupAnimator(mHeader, mListView, mMinHeightHeader, mHeightHeader, mMaxHeaderTranslation);

    }


    private void setStickyOnScrollListener() {

        StickyOnScrollListener mStickyOnScrollListener = new StickyOnScrollListener();
        mListView.setOnScrollListener(mStickyOnScrollListener);

    }


    private class StickyOnScrollListener implements AbsListView.OnScrollListener {

        private int mScrolledYList = 0;

        @Override
        public void onScrollStateChanged(final AbsListView view, final int scrollState) {
            if (mDelegateOnScrollListener != null) {
                mDelegateOnScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {

            mScrolledYList = -calculateScrollYList();

            //notify the animator
            mHeaderAnimator.onScroll(mScrolledYList, mHeader);

            if (mOnStikkyScrollListener != null) {
                mOnStikkyScrollListener.onScroll(view, mScrolledYList);
            }

            if (mDelegateOnScrollListener != null) {
                mDelegateOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }

        private int calculateScrollYList() {
            View c = mListView.getChildAt(0);
            if (c == null) {
                return 0;
            }

            int firstVisiblePosition = mListView.getFirstVisiblePosition();
            return -c.getTop() + firstVisiblePosition * c.getHeight() + (firstVisiblePosition >= 1 ? mHeightHeader : 0);//TODO >= number of header;
        }

    }


    public void setOnScrollListener(final AbsListView.OnScrollListener onScrollListener) {
        mDelegateOnScrollListener = onScrollListener;
    }

    public void setOnStikkyScrollListener(final OnStikkyScrollListener onStikkyScrollListener) {
        mOnStikkyScrollListener = onStikkyScrollListener;
    }

    public void setMinHeightHeader(int minHeightHeader) {
        this.mMinHeightHeader = minHeightHeader;
        calculateMaxTranslation();
    }

    public interface OnStikkyScrollListener {
        public void onScroll(final AbsListView absListView, final int scrolledY);
    }
}

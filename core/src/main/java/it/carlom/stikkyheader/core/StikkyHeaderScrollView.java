package it.carlom.stikkyheader.core;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;


public class StikkyHeaderScrollView extends StikkyHeader {

    private ScrollView mScrollView;

    StikkyHeaderScrollView(final Context context, final ScrollView scrollView, final View header, final int mMinHeightHeader, final HeaderAnimator headerAnimator) {

        this.mContext = context;
        this.mScrollView = scrollView;
        this.mHeader = header;
        this.mMinHeightHeader = mMinHeightHeader;
        this.mHeaderAnimator = headerAnimator;

        init();
    }


    protected void init() {
        measureHeaderHeight();
        setupAnimator();
        setupOnScrollListener();
    }

    @Override
    protected void setHeightHeader(int heightHeader) {
        super.setHeightHeader(heightHeader);

        // creating a placeholder adding a padding to the scrollview behind the header

        mScrollView.setPadding(
            mScrollView.getPaddingLeft(),
            mScrollView.getPaddingTop() + heightHeader,
            mScrollView.getPaddingRight(),
            mScrollView.getPaddingBottom()
        );

        mScrollView.setClipToPadding(false);
    }

    private void setupOnScrollListener() {

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                mHeaderAnimator.onScroll(-mScrollView.getScrollY());

            }
        });

    }


}

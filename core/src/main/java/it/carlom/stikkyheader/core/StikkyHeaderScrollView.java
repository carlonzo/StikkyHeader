package it.carlom.stikkyheader.core;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;


public class StikkyHeaderScrollView extends StikkyHeader {

    private ScrollView mScrollView;

    StikkyHeaderScrollView(final Context context, final ScrollView scrollView, final View header, final int minHeightHeader, final HeaderAnimator headerAnimator) {
        super(context, header, minHeightHeader, headerAnimator);
        this.mScrollView = scrollView;
    }

    @Override
    protected View getScrollingView() {
        return mScrollView;
    }

    protected void init() {
        super.init();
        setupOnScrollListener();
        mScrollView.setClipToPadding(false);
    }

    @Override
    protected void setHeightHeader(int heightHeader) {
        super.setHeightHeader(heightHeader);

        // create a placeholder adding a padding to the scrollview behind the header
        mScrollView.setPadding(
                mScrollView.getPaddingLeft(),
                mScrollView.getPaddingTop() + heightHeader,
                mScrollView.getPaddingRight(),
                mScrollView.getPaddingBottom()
        );
    }

    private void setupOnScrollListener() {

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                onScroll(-mScrollView.getScrollY());
            }
        });

        //init scroll listener when the view is ready
        StikkyHeaderUtils.executeOnGlobalLayout(mScrollView, new Runnable() {
            @Override
            public void run() {
                onScroll(-mScrollView.getScrollY());
            }
        });
    }


}

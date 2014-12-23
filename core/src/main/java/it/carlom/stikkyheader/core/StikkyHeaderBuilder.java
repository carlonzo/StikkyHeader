package it.carlom.stikkyheader.core;


import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;

public abstract class StikkyHeaderBuilder {

    protected final Context mContext;

    protected View mHeader;
    protected int mMinHeight;
    protected HeaderAnimator mAnimator;

    protected StikkyHeaderBuilder(final Context context) {
        mContext = context;
        mMinHeight = 0;
    }

    public static ListViewBuilder stickTo(final ListView listView) {
        return new ListViewBuilder(listView);
    }

    public static RecyclerViewBuilder stickTo(final RecyclerView recyclerView) {
        return new RecyclerViewBuilder(recyclerView);
    }

    public StikkyHeaderBuilder addHeader(final View header, final FrameLayout containerListView) {

        mHeader = header;
        containerListView.addView(header);

        return this;
    }

    public StikkyHeaderBuilder addHeader(final int resLayout, final FrameLayout containerListView) {

        mHeader = LayoutInflater.from(mContext).inflate(resLayout, containerListView, false);
        containerListView.addView(mHeader);

        return this;
    }

    public StikkyHeaderBuilder setHeader(final View header) {
        mHeader = header;
        return this;
    }

    public StikkyHeaderBuilder minHeightHeaderRes(@DimenRes final int resDimension) {
        mMinHeight = mContext.getResources().getDimensionPixelSize(resDimension);
        return this;
    }

    public StikkyHeaderBuilder minHeightHeaderPixel(final int minHeight) {
        mMinHeight = minHeight;
        return this;
    }

    public StikkyHeaderBuilder animator(final HeaderAnimator animator) {
        mAnimator = animator;
        return this;
    }

    public abstract StikkyHeader build();

    public static class ListViewBuilder extends StikkyHeaderBuilder {

        private final ListView mListView;

        protected ListViewBuilder(final ListView listView) {
            super(listView.getContext());
            mListView = listView;
        }

        @Override
        public StikkyHeader build() {

            //if the animator has not been set, the default one is used
            if (mAnimator == null) {
                mAnimator = new HeaderStikkyAnimator();
            }

            return new StikkyHeaderListView(mContext, mListView, mHeader, mMinHeight, mAnimator);
        }
    }

    public static class RecyclerViewBuilder extends StikkyHeaderBuilder {

        private final RecyclerView mRecyclerView;

        protected RecyclerViewBuilder(RecyclerView mRecyclerView) {
            super(mRecyclerView.getContext());
            this.mRecyclerView = mRecyclerView;
        }

        @Override
        public StikkyHeader build() {

            //if the animator has not been set, the default one is used
            if (mAnimator == null) {
                mAnimator = new HeaderStikkyAnimator();
            }

            return new StikkyHeaderRecyclerView(mContext, mRecyclerView, mHeader, mMinHeight, mAnimator);
        }

    }

}

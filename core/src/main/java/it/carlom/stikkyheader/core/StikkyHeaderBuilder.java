package it.carlom.stikkyheader.core;


import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ScrollView;

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

    public static ScrollViewBuilder stickTo(final ScrollView scrollView) {
        return new ScrollViewBuilder(scrollView);
    }

    public StikkyHeaderBuilder setHeader(@IdRes final int idHeader, final ViewGroup view) {
        mHeader = view.findViewById(idHeader);
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
        public StikkyHeaderListView build() {

            //if the animator has not been set, the default one is used
            if (mAnimator == null) {
                mAnimator = new HeaderStikkyAnimator();
            }

            return new StikkyHeaderListView(mContext, mListView, mHeader, mMinHeight, mAnimator);
        }
    }

    public static class RecyclerViewBuilder extends StikkyHeaderBuilder {

        private final RecyclerView mRecyclerView;

        protected RecyclerViewBuilder(final RecyclerView mRecyclerView) {
            super(mRecyclerView.getContext());
            this.mRecyclerView = mRecyclerView;
        }

        @Override
        public StikkyHeaderRecyclerView build() {

            //if the animator has not been set, the default one is used
            if (mAnimator == null) {
                mAnimator = new HeaderStikkyAnimator();
            }

            return new StikkyHeaderRecyclerView(mContext, mRecyclerView, mHeader, mMinHeight, mAnimator);
        }

    }

    public static class ScrollViewBuilder extends StikkyHeaderBuilder {

        private final ScrollView mScrollView;

        protected ScrollViewBuilder(final ScrollView scrollView) {
            super(scrollView.getContext());
            this.mScrollView = scrollView;
        }

        @Override
        public StikkyHeaderScrollView build() {

            //if the animator has not been set, the default one is used
            if (mAnimator == null) {
                mAnimator = new HeaderStikkyAnimator();
            }

            return new StikkyHeaderScrollView(mContext, mScrollView, mHeader, mMinHeight, mAnimator);
        }

    }

}

package it.carlom.stikkyheader.core;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;

public abstract class StikkyHeaderBuilder {

    protected final Context mContext;

    protected View mHeader;
    protected int mMinHeight;
    protected HeaderAnimator mAnimator;
    protected boolean mAllowTouchBehindHeader;

    protected StikkyHeaderBuilder(final Context context) {
        mContext = context;
        mMinHeight = 0;
        mAllowTouchBehindHeader = false;
    }

    public abstract StikkyHeader build();

    public static ListViewBuilder stickTo(final ListView listView) {
        return new ListViewBuilder(listView);
    }

    public static RecyclerViewBuilder stickTo(final ViewGroup recyclerView) {
        StikkyHeaderUtils.checkRecyclerView(recyclerView);
        return new RecyclerViewBuilder(recyclerView);
    }

    public static ScrollViewBuilder stickTo(final ScrollView scrollView) {
        return new ScrollViewBuilder(scrollView);
    }

    public static TargetBuilder stickTo(final Context context) {
        return new TargetBuilder(context);
    }

    public StikkyHeaderBuilder setHeader(@IdRes final int idHeader, final ViewGroup view) {
        mHeader = view.findViewById(idHeader);
        return this;
    }

    public StikkyHeaderBuilder setHeader(final View header) {
        mHeader = header;
        return this;
    }

    /**
     * Deprecated: use {@link #minHeightHeaderDim(int)}
     */
    @Deprecated
    public StikkyHeaderBuilder minHeightHeaderRes(@DimenRes final int resDimension) {
        return minHeightHeaderDim(resDimension);
    }

    public StikkyHeaderBuilder minHeightHeaderDim(@DimenRes final int resDimension) {
        mMinHeight = mContext.getResources().getDimensionPixelSize(resDimension);
        return this;
    }

    /**
     * Deprecated: use {@link #minHeightHeader(int)}
     */
    @Deprecated
    public StikkyHeaderBuilder minHeightHeaderPixel(final int minHeight) {
        return minHeightHeader(minHeight);
    }

    public StikkyHeaderBuilder minHeightHeader(final int minHeight) {
        mMinHeight = minHeight;
        return this;
    }

    public StikkyHeaderBuilder animator(final HeaderAnimator animator) {
        mAnimator = animator;
        return this;
    }

    /**
     * Allows the touch of the views behind the StikkyHeader. by default is false
     *
     * @param allow true to allow the touch behind the StikkyHeader, false to allow only the scroll.
     */
    public StikkyHeaderBuilder allowTouchBehindHeader(boolean allow) {
        mAllowTouchBehindHeader = allow;
        return this;
    }

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

            final StikkyHeaderListView stikkyHeaderListView = new StikkyHeaderListView(mContext, mListView, mHeader, mMinHeight, mAnimator);
            stikkyHeaderListView.build(mAllowTouchBehindHeader);

            return stikkyHeaderListView;
        }
    }

    public static class RecyclerViewBuilder extends StikkyHeaderBuilder {

        private final RecyclerView mRecyclerView;

        protected RecyclerViewBuilder(final ViewGroup mRecyclerView) {
            super(mRecyclerView.getContext());
            this.mRecyclerView = (RecyclerView) mRecyclerView;
        }

        @Override
        public StikkyHeaderRecyclerView build() {

            //if the animator has not been set, the default one is used
            if (mAnimator == null) {
                mAnimator = new HeaderStikkyAnimator();
            }

            final StikkyHeaderRecyclerView stikkyHeaderRecyclerView = new StikkyHeaderRecyclerView(mContext, mRecyclerView, mHeader, mMinHeight, mAnimator);
            stikkyHeaderRecyclerView.build(mAllowTouchBehindHeader);

            return stikkyHeaderRecyclerView;
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

            final StikkyHeaderScrollView stikkyHeaderScrollView = new StikkyHeaderScrollView(mContext, mScrollView, mHeader, mMinHeight, mAnimator);

            stikkyHeaderScrollView.build(mAllowTouchBehindHeader);

            return stikkyHeaderScrollView;
        }

    }

    public static class TargetBuilder extends StikkyHeaderBuilder {

        private final Context mContext;

        protected TargetBuilder(final Context context) {
            super(context);
            mContext = context;
        }

        @Override
        public StikkyHeaderTarget build() {

            //if the animator has not been set, the default one is used
            if (mAnimator == null) {
                mAnimator = new HeaderStikkyAnimator();
            }

            final StikkyHeaderTarget stikkyHeaderTarget = new StikkyHeaderTarget(mContext, mHeader, mMinHeight, mAnimator);
            stikkyHeaderTarget.build(mAllowTouchBehindHeader);

            return stikkyHeaderTarget;
        }

    }

}

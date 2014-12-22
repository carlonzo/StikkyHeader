package it.carlom.stikkyheader.core;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;

public class StikkyHeaderBuilder {

    private final Context mContext;
    private final ListView mListView;

    private View mHeader;
    private int mMinHeight;
    private HeaderAnimator mAnimator;

    private StikkyHeaderBuilder(final ListView listView) {
        mListView = listView;
        mContext = listView.getContext();
        mMinHeight = 0;
    }

    public static StikkyHeaderBuilder stickTo(final ListView listView) {
        return new StikkyHeaderBuilder(listView);
    }

    public StikkyHeaderBuilder addHeader(final View header, FrameLayout containerListView) {

        mHeader = header;
        containerListView.addView(header);

        return this;
    }

    public StikkyHeaderBuilder addHeader(final int resLayout, FrameLayout containerListView) {

        mHeader = LayoutInflater.from(mContext).inflate(resLayout, containerListView, false);
        containerListView.addView(mHeader);

        return this;
    }

    public StikkyHeaderBuilder setHeader(final View header) {
        mHeader = header;
        return this;
    }

    public StikkyHeaderBuilder minHeightHeaderRes(final int resDimension) {
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

    public StikkyHeader build() {

        //if the animator has not been set, the default one is used
        if (mAnimator == null) {
            mAnimator = new HeaderStikkyAnimator();
        }

        return new StikkyHeader(mContext, mListView, mHeader, mMinHeight, mAnimator);
    }

}

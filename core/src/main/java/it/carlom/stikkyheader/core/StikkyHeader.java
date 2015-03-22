package it.carlom.stikkyheader.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public abstract class StikkyHeader {

    protected Context mContext;

    protected View mHeader;
    protected int mMinHeightHeader;
    protected HeaderAnimator mHeaderAnimator;
    protected int mHeightHeader;
    protected int mMaxHeaderTranslation;
    protected View mFakeHeader;

    protected void measureHeaderHeight() {

        int height = mHeader.getHeight();

        if (height == 0) {
            //waiting for the height
            mHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int height = mHeader.getHeight();
                    setHeightHeader(height);

                }
            });
        } else {
            setHeightHeader(height);
        }

    }

    protected void setHeightHeader(final int heightHeader) {
        mHeightHeader = heightHeader;

        // some implementations dont use a fake header
        if (mFakeHeader != null) {
            ViewGroup.LayoutParams lpFakeHeader = mFakeHeader.getLayoutParams();
            lpFakeHeader.height = mHeightHeader;
            mFakeHeader.setLayoutParams(lpFakeHeader);
        }

        ViewGroup.LayoutParams lpHeader = mHeader.getLayoutParams();
        lpHeader.height = mHeightHeader;
        mHeader.setLayoutParams(lpHeader);

        calculateMaxTranslation();
        mHeaderAnimator.setHeightHeader(mHeightHeader, mMaxHeaderTranslation);
    }

    private void calculateMaxTranslation() {
        mMaxHeaderTranslation = mMinHeightHeader - mHeightHeader;
    }

    protected void setupAnimator() {

        mHeaderAnimator.setupAnimator(mHeader, mMinHeightHeader, mHeightHeader, mMaxHeaderTranslation);
    }


    public void setMinHeightHeader(int minHeightHeader) {
        this.mMinHeightHeader = minHeightHeader;
        calculateMaxTranslation();
    }


}

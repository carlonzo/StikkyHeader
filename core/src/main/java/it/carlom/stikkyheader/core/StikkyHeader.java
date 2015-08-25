package it.carlom.stikkyheader.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class StikkyHeader {

    protected Context mContext;

    protected View mHeader;
    protected int mMinHeightHeader;
    private HeaderAnimator mHeaderAnimator;
    protected int mHeightHeader;
    private View.OnTouchListener onTouchListenerOnHeaderDelegate;
    private boolean mAllowTouchBehindHeader;

    protected StikkyHeader(Context context, View header, int minHeightHeader, HeaderAnimator headerAnimator) {
        mContext = context;
        mHeader = header;
        mMinHeightHeader = minHeightHeader;
        mHeaderAnimator = headerAnimator;
    }

    protected abstract View getScrollingView();

    /**
     * Init method called when the StikkyHeader is created
     */
    protected void init() {
        setupAnimator();
        measureHeaderHeight();
    }

    void build(boolean allowTouchBehindHeader) {
        mAllowTouchBehindHeader = allowTouchBehindHeader;
        setOnTouchListenerOnHeader(onTouchListenerOnHeaderDelegate);
        init();
    }

    private void measureHeaderHeight() {
        int height = mHeader.getHeight();

        if (height == 0) {
            final ViewGroup.LayoutParams lp = mHeader.getLayoutParams();
            if (lp != null) {
                height = lp.height;
            }

            /*
            here the height can be:
            1. 0 -> height not ready
            2. -1/-2 -> MATCH_PARENT OR WRAP_CONTENT. we should wait for it
            */
            if (height <= 0) {
                //waiting for the height
                StikkyHeaderUtils.executeOnGlobalLayout(mHeader, new Runnable() {
                    @Override
                    public void run() {
                        setHeightHeader(mHeader.getHeight());
                    }
                });
                return;
            }
        }

        setHeightHeader(height);
    }

    protected void setHeightHeader(final int heightHeader) {
        mHeightHeader = heightHeader;
        mHeaderAnimator.setHeightHeader(heightHeader, mMinHeightHeader);
    }

    public void onScroll(int yScroll) {
        mHeaderAnimator.onScroll(yScroll);
    }

    private void setupAnimator() {
        mHeaderAnimator.setupAnimator(mHeader);
    }

    public void setMinHeightHeader(int minHeightHeader) {
        this.mMinHeightHeader = minHeightHeader;
        mHeaderAnimator.setHeightHeader(mHeightHeader, mMinHeightHeader);
    }

    public void setOnTouchListenerOnHeader(View.OnTouchListener onTouchListenerOnHeaderDelegate) {
        this.onTouchListenerOnHeaderDelegate = onTouchListenerOnHeaderDelegate;
        StikkyHeaderUtils.setOnTouchListenerOnHeader(mHeader, getScrollingView(), onTouchListenerOnHeaderDelegate, mAllowTouchBehindHeader);
    }

    public View getHeader() {
        return mHeader;
    }

    public View getHeader() {
        return mHeader;
    }

}

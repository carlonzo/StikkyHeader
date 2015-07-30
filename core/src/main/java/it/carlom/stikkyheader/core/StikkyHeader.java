package it.carlom.stikkyheader.core;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public abstract class StikkyHeader {

    private final View mScrollingView;
    protected Context mContext;

    protected View mHeader;
    protected int mMinHeightHeader;
    protected HeaderAnimator mHeaderAnimator;
    protected int mHeightHeader;
    protected int mMaxHeaderTranslation;
    protected View mFakeHeader;

    protected StikkyHeader(Context context, View scrollingView, View header, int minHeightHeader, HeaderAnimator headerAnimator) {
        mContext = context;
        mScrollingView = scrollingView;
        mHeader = header;
        mMinHeightHeader = minHeightHeader;
        mHeaderAnimator = headerAnimator;
    }

    /**
     * Init method called when the StikkyHeader is created
     */
    protected abstract void init();

    void build(boolean allowTouchBehindHeader) {

        if (!allowTouchBehindHeader) {
            disallowTouchBehindHeader();
        }

        init();
    }

    protected void disallowTouchBehindHeader() {

        // this listener enables the scroll of the scrollingView touching the header, but disables the click of the scrollingView through the header
        mHeader.setOnTouchListener(new View.OnTouchListener() {

            boolean mDownEventDispatched = false;

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {

                final int actionMasked = MotionEventCompat.getActionMasked(event);
                switch (actionMasked) {

                    case MotionEvent.ACTION_MOVE:

                        if (!mDownEventDispatched) {
                            // if moving, create a fake down event for the scrollingView to start the scroll. the y of the touch in the listview is the y coordinate of the touch in the header + the translation of the header
                            final MotionEvent downEvent = MotionEvent.obtain(event.getDownTime() - 1, event.getEventTime() - 1, MotionEvent.ACTION_DOWN, event.getX(), event.getY() + mHeader.getTranslationY(), 0);
                            mScrollingView.dispatchTouchEvent(downEvent);
                            mDownEventDispatched = true;
                        }

                        //dispatching the move event. we need to create a fake motionEvent using a different y coordinate related to the listview
                        final MotionEvent moveEvent = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), MotionEvent.ACTION_MOVE, event.getX(), event.getY() + mHeader.getTranslationY(), 0);
                        mScrollingView.dispatchTouchEvent(moveEvent);

                        break;
                    case MotionEvent.ACTION_UP:
                        // when action up, dispatch an action cancel to avoid a possible click
                        final MotionEvent cancelEvent = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), MotionEvent.ACTION_CANCEL, event.getX(), event.getY(), 0);
                        mScrollingView.dispatchTouchEvent(cancelEvent);
                        mDownEventDispatched = false;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mScrollingView.dispatchTouchEvent(event);
                        mDownEventDispatched = false;
                        break;
                }

                // eat the touch event so its not dispatched.
                return true;
            }
        });

    }

    protected void measureHeaderHeight() {

        int height = mHeader.getHeight();

        if (height == 0) {
            final ViewGroup.LayoutParams lp = mHeader.getLayoutParams();
            if (lp != null) {
                height = lp.height;
            }

/*
            here the height can be:
            1. 0 -> height not ready (hoping that the real height is not zero!)
            2. -1/-2 -> MATCHPARENT OR WRAPCONTENT. we should wait for it
*/
            if (height <= 0) {
                //waiting for the height
                mHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int height = mHeader.getHeight();
                        setHeightHeader(height);

                    }
                });
                return;
            }
        }

        setHeightHeader(height);

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
        mHeaderAnimator.setupAnimator(mHeader, mMinHeightHeader);
    }


    public void setMinHeightHeader(int minHeightHeader) {
        this.mMinHeightHeader = minHeightHeader;
        calculateMaxTranslation();
    }

    protected Context getContext() {
        return mContext;
    }

    public View getHeader() {
        return mHeader;
    }

}

package it.carlom.stikkyheader.core;

import android.content.Context;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.nineoldandroids.view.ViewHelper;

public abstract class StikkyHeader {

    private final View mScrollingView;
    protected Context mContext;

    protected View mHeader;
    protected int mMinHeightHeader;
    protected HeaderAnimator mHeaderAnimator;
    protected int mHeightHeader;
    protected int mMaxHeaderTranslation;
    private View.OnTouchListener onTouchListenerOnHeaderDelegate;

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

        setOnTouchListenerOnHeader(allowTouchBehindHeader);

        init();
    }

    protected void setOnTouchListenerOnHeader(final boolean allowTouchBehindHeader) {

        // this listener enables the scroll of the scrollingView touching the header, but disables the click of the scrollingView through the header
        mHeader.setOnTouchListener(new View.OnTouchListener() {

            boolean mDownEventDispatched = false;

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {

                boolean eventConsumed = false;
                if (!allowTouchBehindHeader) {
                    switch (MotionEventCompat.getActionMasked(event)) {

                        case MotionEvent.ACTION_MOVE:

                            if (!mDownEventDispatched) {
                                // if moving, create a fake down event for the scrollingView to start the scroll. the y of the touch in the listview is the y coordinate of the touch in the header + the translation of the header
                                final MotionEvent downEvent = MotionEvent.obtain(event.getDownTime() - 1, event.getEventTime() - 1, MotionEvent.ACTION_DOWN, event.getX(), event.getY() + ViewHelper.getTranslationY(mHeader), 0);
                                mScrollingView.dispatchTouchEvent(downEvent);
                                mDownEventDispatched = true;
                            }

                            //dispatching the move event. we need to create a fake motionEvent using a different y coordinate related to the listview
                            final MotionEvent moveEvent = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), MotionEvent.ACTION_MOVE, event.getX(), event.getY() + ViewHelper.getTranslationY(mHeader), 0);
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

                    eventConsumed = true;
                }

                if (onTouchListenerOnHeaderDelegate != null) {
                    eventConsumed |= onTouchListenerOnHeaderDelegate.onTouch(v, event);
                }

                return eventConsumed;
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
                        HeaderAnimator.removeGlobalLayoutListener(this,mHeader);
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

    public void setOnTouchListenerOnHeader(View.OnTouchListener onTouchListenerOnHeaderDelegate) {
        this.onTouchListenerOnHeaderDelegate = onTouchListenerOnHeaderDelegate;
    }

}

package it.carlom.stikkyheader.core.animator;

import it.carlom.stikkyheader.core.HeaderAnimator;


public class HeaderStikkyAnimator extends HeaderAnimator {
    private static final String TAG = HeaderStikkyAnimator.class.getName();

    protected float mTransictionRatio;

    @Override
    public void onScroll(int scrolledY) {

        mHeader.setTranslationY(Math.max(scrolledY, mMaxTransiction));

        mTransictionRatio = calculateTransictionRatio(scrolledY);

    }

    private float calculateTransictionRatio(int scrolledY) {
        return (float) scrolledY / (float) mMaxTransiction;
    }

}

package it.carlom.stikkyheader.core.animator;

import com.nineoldandroids.view.ViewHelper;

import it.carlom.stikkyheader.core.HeaderAnimator;


public class BaseStickyHeaderAnimator extends HeaderAnimator {

    private float mTranslationRatio;

    @Override
    protected void onAnimatorAttached() {
        //nothing to do
    }

    @Override
    protected void onAnimatorReady() {
        //nothing to do
    }

    @Override
    public void onScroll(int scrolledY) {

        ViewHelper.setTranslationY(getHeader(),Math.max(scrolledY, getMaxTranslation()));

        mTranslationRatio = calculateTranslationRatio(scrolledY);
    }

    public float getTranslationRatio() {
        return mTranslationRatio;
    }

    private float calculateTranslationRatio(int scrolledY) {
        //TODO check divisor != 0
        return (float) scrolledY / (float) getMaxTranslation();
    }

}

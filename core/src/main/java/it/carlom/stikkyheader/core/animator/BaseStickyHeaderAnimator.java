package it.carlom.stikkyheader.core.animator;

import it.carlom.stikkyheader.core.HeaderAnimator;


public class BaseStickyHeaderAnimator extends HeaderAnimator {

    private float mTranslationRatio;

    @Override
    protected void onAnimatorCreated() {
        //nothing to do
    }

    @Override
    public void onScroll(int scrolledY) {

        getHeader().setTranslationY(Math.max(scrolledY, getMaxTransiction()));

        mTranslationRatio = calculateTransictionRatio(scrolledY);
    }

    public float getTranslationRatio() {
        return mTranslationRatio;
    }

    private float calculateTransictionRatio(int scrolledY) {
        return (float) scrolledY / (float) getMaxTransiction();
    }

}

package it.carlom.stikkyheader.core.animator;

import it.carlom.stikkyheader.core.StikkyCompat;

public class HeaderStikkyAnimator extends BaseStickyHeaderAnimator {

    protected AnimatorBuilder mAnimatorBuilder;
    private float mBoundedTranslatedRatio;
    private boolean hasAnimatorBundles = false;

    @Override
    protected void onAnimatorReady() {
        super.onAnimatorReady();
        mAnimatorBuilder = getAnimatorBuilder();
        hasAnimatorBundles = (mAnimatorBuilder != null) && (mAnimatorBuilder.hasAnimatorBundles());
    }

    /**
     * Override if you want to load the animator builder
     */
    public AnimatorBuilder getAnimatorBuilder() {
        return null;
    }

    @Override
    public void onScroll(int scrolledY) {
        super.onScroll(scrolledY);

        mBoundedTranslatedRatio = clamp(getTranslationRatio(), 0f, 1f);

        if (hasAnimatorBundles) {
            mAnimatorBuilder.animateOnScroll(mBoundedTranslatedRatio, StikkyCompat.getTranslationY(getHeader()));
        }

    }

    public float getBoundedTranslatedRatio() {
        return mBoundedTranslatedRatio;
    }
}
package it.carlom.stikkyheader.core.animator;

import android.view.View;

public class HeaderStikkyAnimator extends BaseStickyHeaderAnimator {

    private float mBoundedTranslatedRatio;

    protected AnimatorBuilder mAnimatorBuilder;

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
    public void onScroll(final int scrolledY, final View header) {
        super.onScroll(scrolledY, header);

        mBoundedTranslatedRatio = clamp(getTranslationRatio(), 0f, 1f);

        if (hasAnimatorBundles) {
            mAnimatorBuilder.animateOnScroll(mBoundedTranslatedRatio, header.getTranslationY());
        }

    }

    public float getBoundedTransletedRatio() {
        return mBoundedTranslatedRatio;
    }
}

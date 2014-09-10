package it.carlom.stikkyheader.core.animator;

public class HeaderStikkyAnimator extends BaseStickyHeaderAnimator {

    private float mBoundedTranslatedRatio;

    protected AnimatorBuilder mAnimatorBuilder;

    private boolean hasAnimatorBundles = false;

    @Override
    protected void onAnimatorCreated() {
        //nothing to do
    }

    @Override
    protected void onAnimatorAttached() {
        super.onAnimatorAttached();

        hasAnimatorBundles = (mAnimatorBuilder != null) && (mAnimatorBuilder.hasAnimatorBundles());

    }

    @Override
    public void onScroll(int scrolledY) {
        super.onScroll(scrolledY);

        mBoundedTranslatedRatio = clamp(getTranslationRatio(), 0f, 1f);

        if (hasAnimatorBundles) {
            mAnimatorBuilder.animateOnScroll(mBoundedTranslatedRatio, getHeader().getTranslationY());
        }

    }


    public void setAnimatorBuilder(final AnimatorBuilder animatorBuilder) {
        this.mAnimatorBuilder = animatorBuilder;
    }


    public float getBoundedTransletedRatio() {
        return mBoundedTranslatedRatio;
    }
}

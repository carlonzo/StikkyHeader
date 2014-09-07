package it.carlom.stikkyheader.core.animator;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;


public class IconActionBarAnimator extends HeaderStikkyAnimator {

    private final Rect mRectViewToAnimate;
    private final Rect mRectActionBar;
    private View mViewToAnimate;
    private final Interpolator mTranslationInterpolator;
    private final Interpolator mScaleInterpolator;
    private final int resIdLayoutToAnimate;
    private final View homeActionBar;


    public IconActionBarAnimator(final Activity activity, int resIdLayoutToAnimate) {

        this.resIdLayoutToAnimate = resIdLayoutToAnimate;

        mRectActionBar = new Rect();
        mRectViewToAnimate = new Rect();

        mTranslationInterpolator = new AccelerateInterpolator(0.5f);
        mScaleInterpolator = new DecelerateInterpolator(2f);

        homeActionBar = activity.findViewById(android.R.id.home);

    }

    private float mScaleXInterpolator;
    private float mScaleYInterpolator;
    private float finalTranslationX;
    private float finalTranslationY;

    @Override
    protected void onAnimatorAttached() {
        super.onAnimatorAttached();

        mViewToAnimate = mHeader.findViewById(resIdLayoutToAnimate);

        buildRectView(mViewToAnimate, mRectViewToAnimate);
        buildRectView(homeActionBar, mRectActionBar);

        float finalScaleX = ((float) mRectActionBar.width() / mRectViewToAnimate.width());
        float finalScaleY = ((float) mRectActionBar.width() / mRectViewToAnimate.width());

        mScaleXInterpolator = finalScaleX - 1f;
        mScaleYInterpolator = finalScaleY - 1f;

        finalTranslationX = (mRectActionBar.left + mRectActionBar.right) - (mRectViewToAnimate.left + mRectViewToAnimate.right);
        finalTranslationY = (mRectActionBar.top + mRectActionBar.bottom) - (mRectViewToAnimate.top + mRectViewToAnimate.bottom);

    }


    private void buildRectView(View view, Rect rect) {

        if (view == null) {
            rect.set(0, 0, 0, 0);
            return;
        }

        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    @Override
    public void onScroll(int scrolledY) {
        super.onScroll(scrolledY);

//        Log.d("Animat", "\n=======================");

        float boundedRatio = clamp(mTransictionRatio, 0f, 1f);
        float boundedRatioScaleInterpolated = mScaleInterpolator.getInterpolation(boundedRatio);
        float boundedRatioTranslationInterpolated = mTranslationInterpolator.getInterpolation(boundedRatio);

        float scaleX = 1f + boundedRatioScaleInterpolated * mScaleXInterpolator;
        float scaleY = 1f + boundedRatioScaleInterpolated * mScaleYInterpolator;

//        Log.d("Animat", "scaleX: " + scaleX + " boundedRatio: " + boundedRatio);

        mViewToAnimate.setScaleX(scaleX);
        mViewToAnimate.setScaleY(scaleY);

        float translationX = (finalTranslationX * boundedRatioTranslationInterpolated) / 2.0f;
        float translationY = (finalTranslationY * boundedRatioTranslationInterpolated) / 2.0f - mHeader.getTranslationY();

//        Log.d("Animat", "translationX: " + translationX + " mRectViewToAnimate.left: " + (mViewToAnimate.getLeft() + mViewToAnimate.getTranslationX()) + "homeActionBar.left " + homeActionBar.getLeft());

        mViewToAnimate.setTranslationX(translationX);
        mViewToAnimate.setTranslationY(translationY);


    }
}

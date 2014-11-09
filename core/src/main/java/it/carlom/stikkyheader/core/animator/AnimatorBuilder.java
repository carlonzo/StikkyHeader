package it.carlom.stikkyheader.core.animator;

import android.graphics.Rect;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class AnimatorBuilder {

    public static final float DEFAULT_VELOCITY_PARALLAX = 0.5f;

    private List<AnimatorBundle> mListAnimatorBundles;

    public AnimatorBuilder() {
        mListAnimatorBundles = new ArrayList<AnimatorBundle>(2);
    }

    public static AnimatorBuilder create() {
        return new AnimatorBuilder();
    }

    public AnimatorBuilder applyScale(final View viewToScale, final Rect finalRect) {
        return applyScale(viewToScale, finalRect, null);
    }

    public AnimatorBuilder applyScale(final View viewToScale, final Rect finalRect, final Interpolator interpolator) {

        if (viewToScale == null) {
            throw new RuntimeException("You passed a null view");
        }

        Rect from = buildViewRect(viewToScale);
        Float scaleX = calculateScaleX(from, finalRect);
        Float scaleY = calculateScaleY(from, finalRect);

        return applyScale(viewToScale, scaleX, scaleY);
    }

    public AnimatorBuilder applyScale(final View viewToScale, float scaleX, float scaleY) {

        return applyScale(viewToScale, scaleX, scaleY, null);
    }

    public AnimatorBuilder applyScale(final View viewToScale, float scaleX, float scaleY, final Interpolator interpolator) {

        if (viewToScale == null) {
            throw new RuntimeException("You passed a null view");
        }

        AnimatorBundle animatorScale = AnimatorBundle.create(AnimatorBundle.TypeAnimation.SCALE, viewToScale, interpolator, scaleX, scaleY);

        adjustTranslation(animatorScale);

        mListAnimatorBundles.add(animatorScale);

        return this;
    }

    public AnimatorBuilder applyTranslation(final View viewToTranslate, final Rect finalRect) {

        return applyTranslation(viewToTranslate, finalRect, null);
    }

    public AnimatorBuilder applyTranslation(final View viewToTranslate, final Rect finalRect, final Interpolator interpolator) {

        if (viewToTranslate == null) {
            throw new RuntimeException("You passed a null view");
        }

        Rect from = buildViewRect(viewToTranslate);
        Float translationX = calculateTranslationX(from, finalRect);
        Float translationY = calculateTranslationY(from, finalRect);

        return applyTranslation(viewToTranslate, translationX, translationY, interpolator);
    }

    public AnimatorBuilder applyTranslation(final View viewToTranslate, final float translateX, final float translateY) {

        return applyTranslation(viewToTranslate, translateX, translateY, null);
    }

    public AnimatorBuilder applyTranslation(final View viewToTranslate, final float translateX, final float translateY, final Interpolator interpolator) {

        if (viewToTranslate == null) {
            throw new RuntimeException("You passed a null view");
        }

        AnimatorBundle animatorTranslation = AnimatorBundle.create(AnimatorBundle.TypeAnimation.TRANSLATION, viewToTranslate, interpolator, translateX, translateY);

        adjustTranslation(animatorTranslation);

        mListAnimatorBundles.add(animatorTranslation);

        return this;
    }

    public AnimatorBuilder applyFade(final View viewToFade, final float startFade, final float endFade, final Interpolator interpolator) {
        if (viewToFade == null) {
            throw new RuntimeException("You passed a null view");
        }
        mListAnimatorBundles.add(AnimatorBundle.create(AnimatorBundle.TypeAnimation.FADE, viewToFade, interpolator, startFade, endFade));
        return this;
    }

    public AnimatorBuilder applyFade(final View viewToFade, final float startFade, final float endFade) {

        return applyFade(viewToFade, startFade, endFade, null);
    }

    /**
     * @param viewToParallax
     * @param velocityParallax the velocity to apply to the view in order to show the parallax effect. choose a velocity between 0 and 1 for better results
     * @return
     */
    public AnimatorBuilder applyVerticalParallax(final View viewToParallax, final float velocityParallax) {

        if (viewToParallax == null) {
            throw new RuntimeException("You passed a null view");
        }

        mListAnimatorBundles.add(AnimatorBundle.create(AnimatorBundle.TypeAnimation.PARALLAX, viewToParallax, null, -velocityParallax));

        return this;
    }

    public AnimatorBuilder applyVerticalParallax(final View viewToParallax) {

        if (viewToParallax == null) {
            throw new RuntimeException("You passed a null view");
        }

        mListAnimatorBundles.add(AnimatorBundle.create(AnimatorBundle.TypeAnimation.PARALLAX, viewToParallax, null, -DEFAULT_VELOCITY_PARALLAX));

        return this;
    }

    private void adjustTranslation(final AnimatorBundle newAnimator) {

        AnimatorBundle animatorScale = null, animatorTranslation = null;

        for (AnimatorBundle animator : mListAnimatorBundles) {

            if (newAnimator.mView == animator.mView) {

                if (newAnimator.mTypeAnimation == AnimatorBundle.TypeAnimation.SCALE && animator.mTypeAnimation == AnimatorBundle.TypeAnimation.TRANSLATION) {

                    animatorScale = newAnimator;
                    animatorTranslation = animator;

                } else if (newAnimator.mTypeAnimation == AnimatorBundle.TypeAnimation.TRANSLATION && animator.mTypeAnimation == AnimatorBundle.TypeAnimation.SCALE) {

                    animatorScale = animator;
                    animatorTranslation = newAnimator;

                }

                if (animatorScale != null) {
                    Float translationX = (Float) animatorTranslation.mValues[0] - ((float) animatorTranslation.mView.getWidth() * (Float) animatorScale.mValues[0] / 2f);
                    Float translationY = (Float) animatorTranslation.mValues[1] - ((float) animatorTranslation.mView.getHeight() * (Float) animatorScale.mValues[1] / 2f);

                    animatorTranslation.mValues[0] = translationX;
                    animatorTranslation.mValues[1] = translationY;

                    break;
                }
            }

        }
    }

    protected void animateOnScroll(final float boundedRatioTranslationY, final float translationY) {

        for (AnimatorBuilder.AnimatorBundle animatorBundle : mListAnimatorBundles) {

            switch (animatorBundle.mTypeAnimation) {

                case FADE:
                    animatorBundle.mView.setAlpha((((Float) animatorBundle.mValues[1] - (Float) animatorBundle.mValues[0]) * animatorBundle.mInterpolator.getInterpolation(boundedRatioTranslationY)) + (Float) animatorBundle.mValues[0]); //TODO performance issues?
                    break;

                case TRANSLATION:
                    animatorBundle.mView.setTranslationX((Float) animatorBundle.mValues[0] * animatorBundle.mInterpolator.getInterpolation(boundedRatioTranslationY));
                    animatorBundle.mView.setTranslationY(((Float) animatorBundle.mValues[1] * animatorBundle.mInterpolator.getInterpolation(boundedRatioTranslationY)) - translationY);
                    break;

                case SCALE:
                    animatorBundle.mView.setScaleX(1f - (Float) animatorBundle.mValues[0] * animatorBundle.mInterpolator.getInterpolation(boundedRatioTranslationY));
                    animatorBundle.mView.setScaleY(1f - (Float) animatorBundle.mValues[1] * animatorBundle.mInterpolator.getInterpolation(boundedRatioTranslationY));
                    break;

                case PARALLAX:
                    animatorBundle.mView.setTranslationY((Float) animatorBundle.mValues[0] * translationY);
                    break;

                default:
                    break;

            }

        }

    }

    public boolean hasAnimatorBundles() {
        return mListAnimatorBundles.size() > 0;
    }

    public static Rect buildViewRect(final View view) {
        //TODO get coordinates related to the header
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public static float calculateScaleX(final Rect from, final Rect to) {
        return 1f - (float) to.width() / (float) from.width();
    }

    public static float calculateScaleY(final Rect from, final Rect to) {
        return 1f - (float) to.height() / (float) from.height();
    }

    public static float calculateTranslationX(final Rect from, final Rect to) {
        return to.left - from.left;
    }

    public static float calculateTranslationY(final Rect from, final Rect to) {
        return to.top - from.top;
    }

    public static class AnimatorBundle {

        public enum TypeAnimation {
            SCALE, FADE, TRANSLATION, PARALLAX
        }

        private Object[] mValues;
        private final TypeAnimation mTypeAnimation;
        private View mView;
        private Interpolator mInterpolator;

        AnimatorBundle(final TypeAnimation typeAnimation) {
            mTypeAnimation = typeAnimation;
        }

        public static AnimatorBundle create(final AnimatorBundle.TypeAnimation typeAnimation, final View view, final Interpolator interpolator, final Object... values) {
            AnimatorBundle animatorBundle = new AnimatorBundle(typeAnimation);

            animatorBundle.mView = view;
            if (interpolator == null) {
                animatorBundle.mInterpolator = new LinearInterpolator();
            } else {
                animatorBundle.mInterpolator = interpolator;
            }
            animatorBundle.mValues = values;

            return animatorBundle;
        }

    }


}

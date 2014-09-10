package it.carlom.stikkyheader.core.animator;

import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AnimatorBuilder {

    private List<AnimatorBundle> mListAnimatorBundles;

    public AnimatorBuilder() {
        mListAnimatorBundles = new ArrayList<AnimatorBundle>(2);
    }

    public static AnimatorBuilder create() {
        return new AnimatorBuilder();
    }

    public AnimatorBuilder applyScale(final View viewToScale, final Rect finalRect) {

        Rect from = buildViewRect(viewToScale);
        Float scaleX = calculateScaleX(from, finalRect);
        Float scaleY = calculateScaleY(from, finalRect);

        return applyScale(viewToScale, scaleX, scaleY);
    }

    public AnimatorBuilder applyScale(final View viewToScale, float scaleX, float scaleY) {

        AnimatorBundle animatorScale = new AnimatorBundle(AnimatorBundle.TypeAnimation.SCALE, viewToScale, scaleX, scaleY);

        adjustTranslation(animatorScale);

        mListAnimatorBundles.add(animatorScale);

        return this;
    }

    public AnimatorBuilder applyTranslation(final View viewToTranslate, final Rect finalRect) {

        Rect from = buildViewRect(viewToTranslate);
        Float translationX = calculateTranslationX(from, finalRect);
        Float translationY = calculateTranslationY(from, finalRect);

        return applyTranslation(viewToTranslate, translationX, translationY);
    }

    public AnimatorBuilder applyTranslation(final View viewToTranslate, final float translateX, final float translateY) {

        AnimatorBundle animatorTranslation = new AnimatorBundle(AnimatorBundle.TypeAnimation.TRANSLATION, viewToTranslate, translateX, translateY);

        adjustTranslation(animatorTranslation);

        mListAnimatorBundles.add(animatorTranslation);

        return this;
    }

    public AnimatorBuilder applyFade(final View viewToTranslate, final float fade) {

        mListAnimatorBundles.add(new AnimatorBundle(AnimatorBundle.TypeAnimation.FADE, viewToTranslate, fade));

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

    public boolean hasAnimatorBundles() {
        return mListAnimatorBundles.size() > 0;
    }

    protected void animateOnScroll(final float boundedRatioTranslationY, float translationY) {

        for (AnimatorBuilder.AnimatorBundle animatorBundle : mListAnimatorBundles) {

            switch (animatorBundle.mTypeAnimation) {

                case FADE:
                    animatorBundle.mView.setAlpha(boundedRatioTranslationY); //TODO performance issues?
                    break;

                case TRANSLATION:
                    animatorBundle.mView.setTranslationX((Float) animatorBundle.mValues[0] * boundedRatioTranslationY);
                    animatorBundle.mView.setTranslationY(((Float) animatorBundle.mValues[1] * boundedRatioTranslationY) - translationY);
                    break;

                case SCALE:
                    animatorBundle.mView.setScaleX(1f - (Float) animatorBundle.mValues[0] * boundedRatioTranslationY);
                    animatorBundle.mView.setScaleY(1f - (Float) animatorBundle.mValues[1] * boundedRatioTranslationY);
                    break;

                default:
                    break;

            }

        }

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
            SCALE, FADE, TRANSLATION
        }

        private Object[] mValues;
        private TypeAnimation mTypeAnimation;
        private View mView;

        public AnimatorBundle(final TypeAnimation typeAnimation, final View view, final Object... values) {
            mTypeAnimation = typeAnimation;
            mView = view;
            mValues = values;
        }

    }

}

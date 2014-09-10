package it.carlom.stikkyheader.core.animator;

import android.app.Activity;
import android.view.View;


public class IconActionBarAnimator extends HeaderStikkyAnimator {

    private final int resIdLayoutToAnimate;
    private final View homeActionBar;


    public IconActionBarAnimator(final Activity activity, int resIdLayoutToAnimate) {

        this.resIdLayoutToAnimate = resIdLayoutToAnimate;

        homeActionBar = activity.findViewById(android.R.id.home);

    }

    @Override
    protected void onAnimatorCreated() {
        super.onAnimatorCreated();

        View mViewToAnimate = getHeader().findViewById(resIdLayoutToAnimate);

        AnimatorBuilder animatorBuilder = AnimatorBuilder.create()
                .applyScale(mViewToAnimate, AnimatorBuilder.buildViewRect(homeActionBar))
                .applyTranslation(mViewToAnimate, AnimatorBuilder.buildViewRect(homeActionBar));

        setAnimatorBuilder(animatorBuilder);
    }

}

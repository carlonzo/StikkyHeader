package it.carlom.stickyheader.example.fragment;


import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import it.carlom.stickyheader.example.R;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;
import it.carlom.stikkyheader.core.animator.AnimatorBuilder;
import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class MaterialLikeHeaderFragment extends Fragment {

    private ListView mListView;
    private Toolbar mToolbar;
    private FrameLayout mHeader;
    private HeaderStikkyAnimator mMaterialLikeAnimator = new HeaderStikkyAnimator() {

        private ImageView mImage;
        private int colorToFade = Color.parseColor("5D3DBA");

        @Override
        public AnimatorBuilder getAnimatorBuilder() {

            Point translationHomeButton = new Point(100, 20);

            return AnimatorBuilder.create()
                    .applyTranslation(getHeader().findViewById(R.id.title), translationHomeButton)
                    .applyScale(getHeader().findViewById(R.id.title), 0.4f, 0.4f)
                    .applyFade(getHeader().findViewById(R.id.header_image), 0f)
                    .applyVerticalParallax(getHeader().findViewById(R.id.header_image), 0.9f);
        }

        @Override
        protected void onAnimatorReady() {
            super.onAnimatorReady();

            mImage = (ImageView) mHeader.findViewById(R.id.header_image);

        }

        public int crossFadeColors(final int colorFrom, final int colorTo, final float position, final Interpolator interpolator) {
            float colorFromRed = (float) Color.red(colorFrom);
            float colorFromGreen = (float) Color.green(colorFrom);
            float colorFromBlue = (float) Color.blue(colorFrom);

            float colorToRed = (float) Color.red(colorTo);
            float colorToGreen = (float) Color.green(colorTo);
            float colorToBlue = (float) Color.blue(colorTo);

            float interpolatedPosition = interpolator == null ? position : interpolator.getInterpolation(position);

            float red = colorFromRed + ((colorToRed - colorFromRed) * interpolatedPosition);
            float green = colorFromGreen + ((colorToGreen - colorFromGreen) * interpolatedPosition);
            float blue = colorFromBlue + ((colorToBlue - colorFromBlue) * interpolatedPosition);

            return Color.rgb((int) red, (int) green, (int) blue);
        }

        @Override
        public void onScroll(int scrolledY) {
            super.onScroll(scrolledY);


        }
    };


    public MaterialLikeHeaderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materiallike, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.listview);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mHeader = (FrameLayout) view.findViewById(R.id.header);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StikkyHeaderBuilder.stickTo(mListView)
                .setHeader(mHeader)
                .minHeightHeaderDim(R.dimen.toolbar_height)
                .animator(mMaterialLikeAnimator)
                .build();

    }


}

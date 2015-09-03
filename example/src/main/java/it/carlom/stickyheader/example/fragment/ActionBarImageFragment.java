package it.carlom.stickyheader.example.fragment;


import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import it.carlom.stickyheader.example.R;
import it.carlom.stickyheader.example.Utils;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;
import it.carlom.stikkyheader.core.animator.AnimatorBuilder;
import it.carlom.stikkyheader.core.animator.BaseStickyHeaderAnimator;
import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;

public class ActionBarImageFragment extends Fragment {

    private View mToolbar;
    private ListView mListView;

    public ActionBarImageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actionbarimage, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.listview);
        mToolbar = view.findViewById(R.id.toolbar);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaseStickyHeaderAnimator animator = new HeaderStikkyAnimator() {

            @Override
            public AnimatorBuilder getAnimatorBuilder() {

                View viewToAnimate = getHeader().findViewById(R.id.header_image);
                final View titleToolbar = mToolbar.findViewById(R.id.title_toolbar);
                final Rect squareSizeToolbar = new Rect(0, 0, mToolbar.getHeight(), mToolbar.getHeight());

                return AnimatorBuilder.create()
                        .applyScale(viewToAnimate, squareSizeToolbar)
                        .applyTranslation(viewToAnimate, new Point(titleToolbar.getRight(), 0))
                        .applyFade(viewToAnimate, 1f);
            }

        };

        StikkyHeaderBuilder.stickTo(mListView)
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeader(250)
                .animator(animator)
                .build();

        Utils.populateListView(mListView);
    }

}

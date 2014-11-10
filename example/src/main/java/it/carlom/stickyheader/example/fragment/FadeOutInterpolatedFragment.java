package it.carlom.stickyheader.example.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import it.carlom.stickyheader.example.R;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;
import it.carlom.stikkyheader.core.animator.AnimatorBuilder;
import it.carlom.stikkyheader.core.animator.BaseStickyHeaderAnimator;
import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;

public class FadeOutInterpolatedFragment extends Fragment {

    private ListView mListView;
    private View mHeader;
    private View mText;

    public FadeOutInterpolatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview_header, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.listview);
        mHeader = view.findViewById(R.id.header);
        mText = view.findViewById(R.id.header_text_layout);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().getActionBar().show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        BaseStickyHeaderAnimator animator = new HeaderStikkyAnimator() {

            @Override
            public AnimatorBuilder getAnimatorBuilder() {

                View mViewToAnimate = getHeader().findViewById(R.id.header_image);

                AnimatorBuilder animatorBuilder = AnimatorBuilder.create()
                    .applyTranslation(mViewToAnimate, 0, -getHeader().getHeight(), new DecelerateInterpolator())
                    .applyFade(mViewToAnimate, 0.9f, 0.15f, new AccelerateInterpolator(0.2f))
                    .applyFade(mText, 0f, 1f);

                return animatorBuilder;
            }

            @Override
            protected void onAnimatorReady() {
                super.onAnimatorReady();

                mText.setVisibility(View.VISIBLE);
                mText.setAlpha(0f);

            }
        };

        StikkyHeaderBuilder.stickTo(mListView)
            .setHeader(mHeader)
            .minHeightHeaderPixel(250)
            .animator(animator)
            .build();

        populateListView();

    }

    private void populateListView() {

        String[] elements = new String[500];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = "row " + i;
        }

        mListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, elements));

    }

}

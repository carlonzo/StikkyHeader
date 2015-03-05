package it.carlom.stickyheader.example.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import it.carlom.stickyheader.example.R;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;
import it.carlom.stikkyheader.core.animator.AnimatorBuilder;
import it.carlom.stikkyheader.core.animator.BaseStickyHeaderAnimator;
import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;

public class ActionBarImageFragment extends Fragment {

    private View mHomeView;
    private ListView mListView;

    public ActionBarImageFragment() {
        // Required empty public constructor
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
        mHomeView = getActivity().findViewById(android.R.id.home);
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
                    .applyScale(mViewToAnimate, AnimatorBuilder.buildViewRect(mHomeView))
                    .applyTranslation(mViewToAnimate, AnimatorBuilder.buildPointView(mHomeView));


                return animatorBuilder;
            }
        };

        StikkyHeaderBuilder.stickTo(mListView)
            .setHeader(R.id.header, (ViewGroup) getView())
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

        mListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, elements));

    }

}

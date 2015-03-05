package it.carlom.stickyheader.example.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import it.carlom.stickyheader.example.R;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class SimpleScrollViewFragment extends Fragment {

    private ScrollView mScrollView;

    public SimpleScrollViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simplescrollview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScrollView = (ScrollView) view.findViewById(R.id.scroll);
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

        StikkyHeaderBuilder.stickTo(mScrollView)
            .setHeader(R.id.header, (FrameLayout) getView())
            .minHeightHeaderPixel(250)
            .build();


    }


}

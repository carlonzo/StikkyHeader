package it.carlom.stickyheader.example.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StikkyHeaderBuilder.stickTo(mScrollView)
                .setHeader(R.id.header, (FrameLayout) getView())
                .minHeightHeader(250)
                .build();

    }


}

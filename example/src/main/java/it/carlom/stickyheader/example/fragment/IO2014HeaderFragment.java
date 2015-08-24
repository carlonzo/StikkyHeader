package it.carlom.stickyheader.example.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import it.carlom.stickyheader.example.R;
import it.carlom.stickyheader.example.Utils;
import it.carlom.stickyheader.example.animator.IO2014HeaderAnimator;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class IO2014HeaderFragment extends Fragment {

    private ListView mListView;

    public IO2014HeaderFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_io2014, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) getView().findViewById(R.id.listview);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        IO2014HeaderAnimator animator = new IO2014HeaderAnimator(getActivity());

        StikkyHeaderBuilder.stickTo(mListView)
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeaderDim(R.dimen.min_height_header_materiallike)
                .animator(animator)
                .build();

        Utils.populateListView(mListView);
    }

}

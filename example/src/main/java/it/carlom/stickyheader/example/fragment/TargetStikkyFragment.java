package it.carlom.stickyheader.example.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.carlom.stickyheader.example.R;
import it.carlom.stickyheader.example.Utils;
import it.carlom.stikkyheader.core.StikkyHeader;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;
import it.carlom.stikkyheader.core.StikkyHeaderUtils;

public class TargetStikkyFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public TargetStikkyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final StikkyHeader stikkyHeader = StikkyHeaderBuilder.stickTo(getActivity())
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeaderDim(R.dimen.min_height_header)
                .build();

        Utils.populateRecyclerView(mRecyclerView);

        // here I have to handle the space under the header
        mRecyclerView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.max_height_header), 0, 0);
        mRecyclerView.setClipToPadding(false);
        StikkyHeaderUtils.setOnTouchListenerOnHeader(stikkyHeader.getHeader(), mRecyclerView, null, false);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollY = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                stikkyHeader.onScroll(-scrollY);
            }
        });
    }
}

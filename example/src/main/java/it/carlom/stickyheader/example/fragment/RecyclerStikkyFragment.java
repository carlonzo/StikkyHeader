package it.carlom.stickyheader.example.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.carlom.stickyheader.example.R;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class RecyclerStikkyFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public RecyclerStikkyFragment() {
        // Required empty public constructor
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

        StikkyHeaderBuilder.stickTo(mRecyclerView)
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeaderDim(R.dimen.min_height_header)
                .build();

        populateListView();

    }

    private void populateListView() {

        List<String> elements = new ArrayList<>(500);
        for (int i = 0; i < 500; i++) {
            elements.add("row " + i);
        }

        SimpleRecyclerAdapter recyclerAdapter = new SimpleRecyclerAdapter(getActivity(), elements);
        mRecyclerView.setAdapter(recyclerAdapter);

    }


    private static class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder> {

        private final Context mContext;
        protected final List<String> mElements;

        SimpleRecyclerAdapter(final Context context, final List<String> elements) {
            mContext = context;
            mElements = elements;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.text.setText(mElements.get(i));
        }

        @Override
        public int getItemCount() {
            return mElements.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView text;

            public ViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(android.R.id.text1);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int position = getAdapterPosition();
                        mElements.add(position, "row " + position + " +");
                        notifyItemInserted(position);
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final int position = getAdapterPosition();
                        mElements.remove(position);
                        notifyItemRemoved(position);
                        return false;
                    }
                });
            }
        }
    }
}

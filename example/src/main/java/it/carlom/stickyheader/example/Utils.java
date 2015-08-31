package it.carlom.stickyheader.example;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final int COUNT_ITEMS = 500;

    public static void populateListView(ListView listView) {
        String[] elements = new String[COUNT_ITEMS];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = "row " + i;
        }

        listView.setAdapter(new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1, elements));
    }

    public static void populateRecyclerView(RecyclerView recyclerView) {
        List<String> elements = new ArrayList<>(COUNT_ITEMS);
        for (int i = 0; i < COUNT_ITEMS; i++) {
            elements.add("row " + i);
        }

        SimpleRecyclerAdapter recyclerAdapter = new SimpleRecyclerAdapter(recyclerView.getContext(), elements);
        recyclerView.setAdapter(recyclerAdapter);
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

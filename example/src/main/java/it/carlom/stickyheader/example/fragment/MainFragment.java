package it.carlom.stickyheader.example.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import it.carlom.stickyheader.example.MainActivity;
import it.carlom.stickyheader.example.R;

public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = (ListView) getView().findViewById(R.id.listview);

        String[] mFrags = {
            "Simple Stikky Header",
            "Parallax Simple Stikky Header",
            "ActionBarImage Header Animator",
            "I/O 2014 Header Animator",
            "Recycler View Header",
            "Scroll View Header"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mFrags);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment = null;

                switch (position) {

                    case 0:
                        fragment = new SimpleStikkyFragment();
                        break;

                    case 1:
                        fragment = new ParallaxStikkyFragment();
                        break;

                    case 2:
                        fragment = new ActionBarImageFragment();
                        break;

                    case 3:
                        fragment = new IO2014HeaderFragment();
                        break;

                    case 4:
                        fragment = new RecyclerStikkyFragment();
                        break;

                    case 5:
                        fragment = new SimpleScrollViewFragment();
                        break;

                }

                ((MainActivity) getActivity()).loadFragment(fragment);
            }
        });


    }
}

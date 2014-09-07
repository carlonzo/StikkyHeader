package it.carlom.stickyheader.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;
import it.carlom.stikkyheader.core.animator.IconActionBarAnimator;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listview);

        StikkyHeaderBuilder.stickTo(listView)
                .header(R.layout.header_iconactionbar_animator, (FrameLayout) findViewById(R.id.layout_container))
                .minHeightHeader(250)
                .animator(new IconActionBarAnimator(this, R.id.header_image))
                .build();

        String[] elements = new String[500];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = "row " + i;
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements));

    }


}

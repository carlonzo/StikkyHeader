package it.carlom.stikkyheader.core;

import android.content.Context;
import android.view.View;

/**
 * Generic implementation of the StikkyHeader that allows to manually handle the translation and the scroll of the header.
 * <p/>
 * Using this implementation you need to call the method {@link StikkyHeader#onScroll(int)} to send a scroll event to the {@link HeaderAnimator}
 * You will need to apply the TouchListener to handle the touch events on the header, using {@link StikkyHeaderUtils#setOnTouchListenerOnHeader(View, View, View.OnTouchListener, boolean)}
 * <p/>
 * The StikkyHeaderTarget can be useful when you want to apply the scroll from a your own custom view or from different scrolling views (RecyclerViews in a ViewPager for example)
 */
public class StikkyHeaderTarget extends StikkyHeader {

    protected StikkyHeaderTarget(Context context, View header, int minHeightHeader, HeaderAnimator headerAnimator) {
        super(context, header, minHeightHeader, headerAnimator);
    }

    @Override
    protected View getScrollingView() {
        return null;
    }

}

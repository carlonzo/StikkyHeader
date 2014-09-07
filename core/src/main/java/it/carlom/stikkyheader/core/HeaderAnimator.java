package it.carlom.stikkyheader.core;


import android.view.View;
import android.widget.ListView;

public abstract class HeaderAnimator {

    protected View mHeader;
    protected ListView mListView;
    protected int mMinHeightHeader;
    protected int mHeightHeader;
    protected int mMaxTransiction;

    public abstract void onScroll(final int scrolledY);

    /**
     * Called by the {@link it.carlom.stikkyheader.core.StikkyHeader} to set the {@link HeaderAnimator} up
     *
     * @param header
     * @param listView
     */
    void setupAnimator(final View header, final ListView listView, final int minHeightHeader, final int mHeightHeader, final int mMaxTransiction) {
        this.mHeader = header;
        this.mListView = listView;
        this.mMinHeightHeader = minHeightHeader;
        this.mHeightHeader = mHeightHeader;
        this.mMaxTransiction = mMaxTransiction;

        onAnimatorAttached();
    }

    protected void onAnimatorAttached() {
        // empty. Called when the animator is setup
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }
}

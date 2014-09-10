package it.carlom.stikkyheader.core;


import android.view.View;
import android.widget.ListView;

public abstract class HeaderAnimator {

    private View mHeader;
    private ListView mListView;
    private int mMinHeightHeader;
    private int mHeightHeader;
    private int mMaxTransiction;

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

        onAnimatorCreated();
    }

    /**
     * Called after that the animator is attached to the
     */
    protected abstract void onAnimatorCreated();

    /**
     * Called before the first onScroll is called and after the onAnimatorCreated
     */
    protected void onAnimatorAttached() {

    }

    public View getHeader() {
        return mHeader;
    }

    public ListView getListView() {
        return mListView;
    }

    public int getMinHeightHeader() {
        return mMinHeightHeader;
    }

    public int getHeightHeader() {
        return mHeightHeader;
    }

    public int getMaxTransiction() {
        return mMaxTransiction;
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }
}

package it.carlom.stikkyheader.core;


import android.view.View;
import android.widget.ListView;

public abstract class HeaderAnimator {

    private View mHeader;
    private ListView mListView;
    private int mMinHeightHeader;
    private int mHeightHeader;
    private int mMaxTransiction;

    public abstract void onScroll(final int scrolledY,final View header);

    /**
     * Called by the {@link it.carlom.stikkyheader.core.StikkyHeader} to set the {@link HeaderAnimator} up
     *
     * @param header
     * @param listView
     */
    void setupAnimator(final View header, final ListView listView, final int minHeightHeader, final int heightHeader, final int maxTransiction) {
        this.mHeader = header;
        this.mListView = listView;
        this.mMinHeightHeader = minHeightHeader;
        this.mHeightHeader = heightHeader;
        this.mMaxTransiction = maxTransiction;

        onAnimatorAttached();
        onAnimatorReady();
    }

    /**
     * Called after that the animator is attached to the header
     */
    protected abstract void onAnimatorAttached();

    /**
     * Called after that the animator is attached and ready
     */
    protected abstract void onAnimatorReady();

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

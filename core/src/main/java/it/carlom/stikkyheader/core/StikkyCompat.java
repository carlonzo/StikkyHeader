package it.carlom.stikkyheader.core;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class StikkyCompat {

    interface StikkyCompatImpl {
        float getTranslationX(View view);

        float getTranslationY(View view);

        void setTranslationX(View view, float value);

        void setTranslationY(View view, float value);

        void setScaleX(View view, float value);

        void setScaleY(View view, float value);

        float getScaleX(View view);

        float getScaleY(View view);

        void setAlpha(View view, float value);

        float getAlpha(View view);
    }

    private static StikkyCompatImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            IMPL = new HCImpl();
        } else if (StikkyHeaderUtils.hasNineOld()) {
            IMPL = new NineOldImpl();
        } else {
            throw new RuntimeException("StikkyHeader cannot be used on API < 11 without the NineOldAndroids library");
        }
    }

    public static float getTranslationX(View view) {
        return IMPL.getTranslationX(view);
    }

    public static float getTranslationY(View view) {
        return IMPL.getTranslationY(view);
    }

    public static void setTranslationX(View view, float value) {
        IMPL.setTranslationX(view, value);
    }

    public static void setTranslationY(View view, float value) {
        IMPL.setTranslationY(view, value);
    }

    public static void setScaleX(View view, float value) {
        IMPL.setScaleX(view, value);
    }

    public static void setScaleY(View view, float value) {
        IMPL.setScaleY(view, value);
    }

    public static void setAlpha(View view, float value) {
        IMPL.setAlpha(view, value);
    }

    public static float getScaleX(View view) {
        return IMPL.getScaleX(view);
    }

    public static float getScaleY(View view) {
        return IMPL.getScaleY(view);
    }

    public static float getAlpha(View view) {
        return IMPL.getAlpha(view);
    }

    private static class NineOldImpl implements StikkyCompatImpl {

        @Override
        public float getTranslationX(View view) {
            return ViewHelper.getTranslationX(view);
        }

        @Override
        public float getTranslationY(View view) {
            return ViewHelper.getTranslationY(view);
        }

        @Override
        public void setTranslationX(View view, float value) {
            ViewHelper.setTranslationX(view, value);
        }

        @Override
        public void setTranslationY(View view, float value) {
            ViewHelper.setTranslationY(view, value);
        }

        @Override
        public void setScaleX(View view, float value) {
            ViewHelper.setScaleX(view, value);
        }

        @Override
        public void setScaleY(View view, float value) {
            ViewHelper.setScaleY(view, value);
        }

        @Override
        public float getScaleX(View view) {
            return ViewHelper.getScaleX(view);
        }

        @Override
        public float getScaleY(View view) {
            return ViewHelper.getScaleY(view);
        }

        @Override
        public void setAlpha(View view, float value) {
            ViewHelper.setAlpha(view, value);
        }

        @Override
        public float getAlpha(View view) {
            return ViewHelper.getAlpha(view);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static class HCImpl implements StikkyCompatImpl {

        @Override
        public float getTranslationX(View view) {
            return view.getTranslationX();
        }

        @Override
        public float getTranslationY(View view) {
            return view.getTranslationY();
        }

        @Override
        public void setTranslationX(View view, float value) {
            view.setTranslationX(value);
        }

        @Override
        public void setTranslationY(View view, float value) {
            view.setTranslationY(value);
        }

        @Override
        public void setScaleX(View view, float value) {
            view.setScaleX(value);
        }

        @Override
        public void setScaleY(View view, float value) {
            view.setScaleY(value);
        }

        @Override
        public float getScaleX(View view) {
            return view.getScaleX();
        }

        @Override
        public float getScaleY(View view) {
            return view.getScaleY();
        }

        @Override
        public void setAlpha(View view, float value) {
            view.setAlpha(value);
        }

        @Override
        public float getAlpha(View view) {
            return view.getAlpha();
        }
    }

}

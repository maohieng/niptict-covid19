package edu.niptict.covid19.ui;

import androidx.databinding.BindingAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/30/2020
 */
public final class UIUtils {
    private UIUtils() {
        //no instance
    }

    @BindingAdapter("app:pagerAdapter")
    public static void setPagerAdapter(ViewPager2 pager2, FragmentStateAdapter adapter) {
        pager2.setAdapter(adapter);
    }

    @BindingAdapter("app:registerCallback")
    public static void registerPageChangeCallback(ViewPager2 pager2, ViewPager2.OnPageChangeCallback callback) {
        pager2.registerOnPageChangeCallback(callback);
    }
}

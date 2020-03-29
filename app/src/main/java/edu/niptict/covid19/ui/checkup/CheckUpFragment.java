package edu.niptict.covid19.ui.checkup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.jommobile.android.jomutils.ui.BaseFragment;

import java.util.List;

import edu.niptict.covid19.R;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpFragment extends BaseFragment {

    ViewPager2 pager2;
    QuestionPageAdapter pageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checkup, container, false);

        pager2 = root.findViewById(R.id.pager);
        pageAdapter = new QuestionPageAdapter(this);
        pager2.setAdapter(pageAdapter);

        return root;
    }

    public void updateUIs(List<CheckUpQuestion> questions) {
        if (isVisible() && pageAdapter != null) {
            if (pageAdapter.data != null) {
                pageAdapter.data.clear();
            }

            pageAdapter.data = questions;
            pageAdapter.notifyDataSetChanged();
        }
    }

    public static class QuestionPageAdapter extends FragmentStateAdapter {

        List<CheckUpQuestion> data;

        public QuestionPageAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return data == null ? null : QuestionPageFragment.newInstance(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }
    }
}

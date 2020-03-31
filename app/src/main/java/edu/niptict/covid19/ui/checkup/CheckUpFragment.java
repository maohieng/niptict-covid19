package edu.niptict.covid19.ui.checkup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.jommobile.android.jomutils.repository.Resource;
import com.jommobile.android.jomutils.ui.BaseFragment;

import java.util.List;

import edu.niptict.covid19.R;
import edu.niptict.covid19.databinding.FragmentCheckupBinding;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpFragment extends BaseFragment {

    private static final String TAG = "CheckUpFragment";
    public static final String EXTRA_TOTAL_SCORE = "extra-total-score";

    FragmentCheckupBinding mBinding;
    QuestionPageAdapter pageAdapter;

    CheckUpViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkup, container, false);

        ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mBinding.pageIndicator.setSelection(position);
            }
        };

        pageAdapter = new QuestionPageAdapter(this);

        mBinding.setAdapter(pageAdapter);
        mBinding.setPageChangeCallback(onPageChangeCallback);
        mBinding.pager.setUserInputEnabled(false);

        mViewModel = new ViewModelProvider(this).get(CheckUpViewModel.class);

        mViewModel.loadQuestions().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource != null && listResource.getStatus() != Resource.Status.LOADING) {
                if (listResource.getStatus() == Resource.Status.SUCCESS)
                    updatePagesUI(listResource.getData());
                else {
                    Toast.makeText(requireContext(), listResource.getMessage(), Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                }
            }
        });

        mViewModel.getCurrentCheckUp().observe(getViewLifecycleOwner(), checkUpAnswer -> {
            Log.i(TAG, "currentCheckUp->onChanged: " + checkUpAnswer);
            if (checkUpAnswer != null) {
                int position = checkUpAnswer.keyAt(0);
                updatePagePosition(position);
            }
        });

        return mBinding.getRoot();
    }

    private void updatePagesUI(List<CheckUpAnswer> questions) {
        Log.i(TAG, "updatePagesUI: " + questions);
        if (isVisible() && pageAdapter != null) {
            pageAdapter.notifyDataSetChanged(questions);
            mBinding.pageIndicator.setCount(questions.size());
        }
    }

    private void updatePagePosition(final int position) {
        Log.i(TAG, "updatePagePosition: " + position);
        if (isVisible() && mBinding != null) {
            mBinding.pager.post(() -> {
                mBinding.pager.setCurrentItem(position);
            });
        }
    }

    int getTotalScore() {
        int currentCheckUpPosition = getCurrentCheckUpPosition();
        if (currentCheckUpPosition == 0) {
            // didn't answer
            return -1;
        } else {
            return mViewModel.getTotalScore();
        }
    }

    boolean answerCurrentAndGoNext(final int score) {
        return mViewModel.answerCurrentAndGoNext(score);
    }

    CheckUpAnswer getCurrentCheckUpValue() {
        return mViewModel.getCurrentCheckUpValue();
    }

    int getCurrentCheckUpPosition() {
        return mViewModel.getCurrentCheckUpPosition();
    }

    public static class QuestionPageAdapter extends FragmentStateAdapter {

        List<CheckUpAnswer> data;

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

        public final void notifyDataSetChanged(List<CheckUpAnswer> newData) {
            if (this.data != null) {
                this.data.clear();
            }

            this.data = newData;
            notifyDataSetChanged();
        }
    }
}

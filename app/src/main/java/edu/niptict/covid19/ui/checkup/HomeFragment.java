package edu.niptict.covid19.ui.checkup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.niptict.covid19.R;
import edu.niptict.covid19.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding mBinding;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getIsCheckingUp().observe(getViewLifecycleOwner(), newValue -> {
            Log.i(TAG, "IsCheckingUp->onChange: " + newValue);

            if (newValue != null && newValue) {
                CheckUpActivity.startForResult(HomeFragment.this);
            }
        });
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setViewModel(homeViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CheckUpActivity.REQUEST_CODE_START_CHECKUP) {
            homeViewModel.setCheckUp(false);
            if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "onActivityResult: result ok.");
            } else {
                Log.e(TAG, "onActivityResult: result NOT ok");
            }
        }
    }
}

package edu.niptict.covid19.ui.checkup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jommobile.android.jomutils.repository.Resource;
import com.jommobile.android.jomutils.ui.AbstractToolbarActivity;

import edu.niptict.covid19.R;
import edu.niptict.covid19.databinding.ActivityCheckupBinding;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpActivity extends AbstractToolbarActivity {

    private static final String TAG = "CheckUpActivity";

    public static final int REQUEST_CODE_START_CHECKUP = 20020;

    public static void startForResult(Activity activity) {
        Intent starter = new Intent(activity, CheckUpActivity.class);
        activity.startActivityForResult(starter, REQUEST_CODE_START_CHECKUP);
    }

    public static void startForResult(Fragment fragment) {
        Intent starter = new Intent(fragment.requireContext(), CheckUpActivity.class);
        fragment.startActivityForResult(starter, REQUEST_CODE_START_CHECKUP);
    }

    private ActivityCheckupBinding mBinding;
    private CheckUpFragment checkUpFragment;
    private CheckUpViewModel mViewModel;

    @Override
    protected Toolbar setContentViewAndBindToolbar() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkup);
        return mBinding.appBar.toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpFragment = (CheckUpFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        String title = getString(R.string.checkup);
        setTitle(title);

        mViewModel = new ViewModelProvider(this).get(CheckUpViewModel.class);
        mViewModel.getCheckupQuestions().observe(this, listResource -> {
            if (listResource != null && listResource.getStatus() != Resource.Status.LOADING) {
                if (listResource.getStatus() == Resource.Status.SUCCESS) {
//                    Log.i(TAG, "CheckupQuestions->onChange(): " + listResource.getData());
                    checkUpFragment.updateUIs(listResource.getData());
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    CheckUpActivity.this.finish();
                }
            }
        });
    }
}

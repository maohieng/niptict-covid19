package edu.niptict.covid19.ui.checkup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

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
    }

    @Override
    public void onBackPressed() {
        int totalScore = checkUpFragment.getTotalScore();
        setScoreResult(totalScore);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            int totalScore = checkUpFragment.getTotalScore();
            setScoreResult(totalScore);
        }
        return super.onOptionsItemSelected(item);
    }

    void setScoreResult(int totalScore) {
        Intent data = new Intent();
        data.putExtra(CheckUpFragment.EXTRA_TOTAL_SCORE, totalScore);
        setResult(RESULT_OK, data);
    }
}

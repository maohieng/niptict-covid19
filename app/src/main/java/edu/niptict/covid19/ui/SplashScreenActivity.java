package edu.niptict.covid19.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.niptict.covid19.MainActivity;
import edu.niptict.covid19.R;

/**
 * Created by MAO Hieng on 1/7/19.
 */
public class SplashScreenActivity extends AppCompatActivity {

    private SplashScreenViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mViewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);
        mViewModel.getCountDownStatus().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean.equals(Boolean.TRUE)) {
                startNextScreen();
            }
        });
    }

    private void startNextScreen() {
        MainActivity.start(this);
        // Finish self
        finish();
    }
}

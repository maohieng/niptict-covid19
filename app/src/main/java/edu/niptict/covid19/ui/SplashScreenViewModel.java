package edu.niptict.covid19.ui;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jommobile.android.jomutils.Logs;

/**
 * Created by MAO Hieng on 1/7/19.
 */
public final class SplashScreenViewModel extends AndroidViewModel {

    private static final String TAG = Logs.makeTag(SplashScreenViewModel.class);
    private static final long DELAY_MILLIS = 2000;

    private final MutableLiveData<Boolean> mCountDownStatus = new MutableLiveData<>(false);
    // private final LiveData<Boolean> mFullyCountDown;

    public SplashScreenViewModel(@NonNull Application application) {
        super(application);

        // Init DB
        // ((LearnApp) application).getDatabase();
        // We don't observe database initialization as it runs on the same thread of this constructor (main thread)

        startCountDown();
    }

    private void startCountDown() {
        Logs.I(TAG, "start splash screen count down.");
        if (mCountDownStatus.getValue() != null && mCountDownStatus.getValue()) { // Already counted
            return;
        }

        mCountDownStatus.setValue(false);

        new CountDownTimer(DELAY_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Logs.I(TAG, "Seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mCountDownStatus.setValue(true);
            }
        }.start();
    }

    LiveData<Boolean> getCountDownStatus() {
        return mCountDownStatus;
    }

}

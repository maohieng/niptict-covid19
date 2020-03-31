package edu.niptict.covid19.ui.checkup;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.jommobile.android.jomutils.AbsentLiveData;
import com.jommobile.android.jomutils.repository.Resource;

import edu.niptict.covid19.MyApplication;

public class HomeViewModel extends AndroidViewModel {
    private static final String TAG = "HomeViewModel";

    private final CheckUpRepository mRepository;

    private final MutableLiveData<Boolean> mIsCheckingUp;

    private final MutableLiveData<Integer> mScore;
    private final LiveData<Resource<CheckUpResult>> mCheckUpResult;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CheckUpRepository((MyApplication) application);
        mIsCheckingUp = new MutableLiveData<>(false);

        mScore = new MutableLiveData<>(mRepository.getLatestScore());
        mCheckUpResult = Transformations.switchMap(mScore, input -> {
            if (input == null || input < 0) {
                return AbsentLiveData.create();
            } else {
                return mRepository.loadCheckUpResult(input);
            }
        });
    }

    public void setCheckingUp(boolean checking) {
        Log.i(TAG, "setCheckUp: " + checking);

        Boolean value = mIsCheckingUp.getValue();
        if (value == null || value != checking) {
            mIsCheckingUp.setValue(checking);
        }
    }

    void setCheckUpScore(int score) {
        if (score < 0)
            return;

        Integer value = mScore.getValue();
        if (value == null || value < 0 || value != score) {
            mScore.setValue(score);
            mRepository.saveLatestScore(score);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////////////////////////////////

    LiveData<Boolean> getIsCheckingUp() {
        return mIsCheckingUp;
    }

    public LiveData<Resource<CheckUpResult>> getCheckUpResult() {
        return mCheckUpResult;
    }

    public LiveData<Integer> getScore() {
        return mScore;
    }
}
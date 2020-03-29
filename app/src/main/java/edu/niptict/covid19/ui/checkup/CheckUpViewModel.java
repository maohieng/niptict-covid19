package edu.niptict.covid19.ui.checkup;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.jommobile.android.jomutils.repository.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.niptict.covid19.MyApplication;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpViewModel extends AndroidViewModel {

    private final CheckUpRepository mRepository;

    private final LiveData<Resource<List<CheckUpQuestion>>> mCheckupQuestions;
    private final MediatorLiveData<CheckUpQuestion> mCurrentCheckUp = new MediatorLiveData<>();
    private final MutableLiveData<List<CheckUpAnswer>> mDoneCheckUp = new MutableLiveData<>(new ArrayList<>());

    public CheckUpViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CheckUpRepository((MyApplication) application);

        // Load here
        LiveData<Resource<List<CheckUpQuestion>>> resourceLiveData = mRepository.loadCheckUpQuestions();
        mCheckupQuestions = Transformations.map(resourceLiveData, input -> input);
    }

    private void setCurrentCheckUp(CheckUpQuestion question) {
        CheckUpQuestion value = mCurrentCheckUp.getValue();
        if (value == null || !value.equals(question)) {
            mCurrentCheckUp.setValue(question);
        }
    }

    void startCheckUp(CheckUpQuestion question) {
        mCurrentCheckUp.removeSource(mCheckupQuestions);
        mCurrentCheckUp.addSource(mCheckupQuestions, listResource -> {
            if (listResource != null && listResource.getStatus() != Resource.Status.LOADING) {
                mCurrentCheckUp.removeSource(mCheckupQuestions);
                setCurrentCheckUp(question);
            }
        });
    }

    void answerCurrentCheckUp(int score, CheckUpQuestion nextQuestion) {
        CheckUpQuestion value = mCurrentCheckUp.getValue();
        if (score <= Objects.requireNonNull(value).maxScore) {
            // Update Done
            CheckUpAnswer answer = new CheckUpAnswer(value, score);
            List<CheckUpAnswer> answers = mDoneCheckUp.getValue();
            Objects.requireNonNull(answers).add(answer);
            mDoneCheckUp.setValue(answers);

            // next question
            new Handler().postDelayed(() -> setCurrentCheckUp(nextQuestion), 100);
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////////////////////////////////


    public LiveData<Resource<List<CheckUpQuestion>>> getCheckupQuestions() {
        return mCheckupQuestions;
    }
}

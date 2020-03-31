package edu.niptict.covid19.ui.checkup;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

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

    private static final String TAG = "CheckUpViewModel";

    private final CheckUpRepository mRepository;

    /**
     * Available questions
     */
    private final MediatorLiveData<Resource<List<CheckUpAnswer>>> mQuestions = new MediatorLiveData<>();

    private final MediatorLiveData<SparseArray<CheckUpAnswer>> mCurrentCheckUp = new MediatorLiveData<>();

    public CheckUpViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CheckUpRepository((MyApplication) application);
        startCheckUp(0);
    }

    LiveData<Resource<List<CheckUpAnswer>>> loadQuestions() {
        Resource<List<CheckUpAnswer>> value = mQuestions.getValue();
        Log.i(TAG, "loadQuestions: " + value);

        if (value == null || value.getStatus() != Resource.Status.LOADING || value.getData() == null) {
            final LiveData<Resource<List<CheckUpQuestion>>> resourceLiveData = mRepository.loadCheckUpQuestions();
            mQuestions.addSource(resourceLiveData, input -> {
                Resource<List<CheckUpAnswer>> answerResource = new ResourceExchange<List<CheckUpQuestion>, List<CheckUpAnswer>>(input1 -> {
                    List<CheckUpAnswer> answers = new ArrayList<>(input1.size());
                    for (CheckUpQuestion datum : input1) {
                        answers.add(new CheckUpAnswer(datum));
                    }

                    return answers;
                }).apply(input);

                if (answerResource != null && answerResource.getStatus() != Resource.Status.LOADING) {
                    mQuestions.removeSource(resourceLiveData);
                }
                mQuestions.setValue(answerResource);
            });
        }

        return mQuestions;
    }

    private void startCheckUp(final int position) {
        currentCheckUpObservesQuestions(checkUpAnswers -> {
            Log.i(TAG, "startCheckUp: " + position);
            CheckUpAnswer answer = checkUpAnswers.get(position);
            answer.setStatus(CheckUpAnswer.Status.ANSWERING);

            SparseArray<CheckUpAnswer> answerSparseArray = new SparseArray<>(1);
            answerSparseArray.put(position, answer);

            mCurrentCheckUp.setValue(answerSparseArray);
        });
    }

    private void currentCheckUpObservesQuestions(Observer<List<CheckUpAnswer>> observer) {
        Resource<List<CheckUpAnswer>> questions = mQuestions.getValue();
        Log.i(TAG, "currentCheckUpObservesQuestions: " + questions);

        List<CheckUpAnswer> data = null;
        if (questions != null && (data = questions.getData()) != null && !data.isEmpty()) {
            observer.onChanged(data);
        } else {
            mCurrentCheckUp.removeSource(mQuestions);
            mCurrentCheckUp.addSource(mQuestions, listResource -> {
                if (listResource == null || listResource.getStatus() == Resource.Status.LOADING) {
                    return;
                }

                mCurrentCheckUp.removeSource(mQuestions);

                List<CheckUpAnswer> data1 = listResource.getData();
                if (data1 != null && !data1.isEmpty()) {
                    observer.onChanged(data1);
                }
            });
        }
    }

    CheckUpAnswer getCurrentCheckUpValue() {
        SparseArray<CheckUpAnswer> value = mCurrentCheckUp.getValue();
        return value.get(getCurrentCheckUpPosition());
    }

    int getCurrentCheckUpPosition() {
        SparseArray<CheckUpAnswer> value = mCurrentCheckUp.getValue();
        return value.keyAt(0);
    }

    boolean answerCurrentAndGoNext(final int score) {
        Resource<List<CheckUpAnswer>> questions = mQuestions.getValue();
        Log.i(TAG, "currentCheckUpObservesQuestions: " + questions);

        List<CheckUpAnswer> checkUpAnswers = null;
        if (questions != null && (checkUpAnswers = questions.getData()) != null && !checkUpAnswers.isEmpty()) {
            Log.i(TAG, "answerCurrentAndGoNext: " + checkUpAnswers);

            SparseArray<CheckUpAnswer> value = mCurrentCheckUp.getValue();
            final int currentPos = value.keyAt(0);
            CheckUpAnswer answer = value.get(currentPos);

            Log.i(TAG, "answerCurrentAndGoNext: " + answer);

            if (score <= answer.getQuestion().maxScore) {
                // Update
                answer.setScore(score);
                answer.setStatus(CheckUpAnswer.Status.ANSWERED);

                // next question
                int nextPosition = currentPos + 1;
                if (nextPosition < Objects.requireNonNull(Objects.requireNonNull(mQuestions.getValue()).getData()).size()) {
                    new Handler().postDelayed(() -> startCheckUp(nextPosition), 100);
                    return true;
                }
            }
        }

        return false;
    }

    int getTotalScore() {
        int total = 0;
        List<CheckUpAnswer> data = mQuestions.getValue().getData();
        for (CheckUpAnswer datum : data) {
            total += datum.getScore().get();
        }

        return total;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////////////////////////////////

    LiveData<SparseArray<CheckUpAnswer>> getCurrentCheckUp() {
        return mCurrentCheckUp;
    }

//    LiveData<Resource<List<CheckUpAnswer>>> getQuestions() {
//        return mQuestions;
//    }

    ///////////////////////////////////////////////////////////////////////////
    // Others
    ///////////////////////////////////////////////////////////////////////////

    public static class ResourceExchange<T, R> implements Function<Resource<T>, Resource<R>> {

        @NonNull
        private final Function<T, R> sourceF;

        public ResourceExchange(@NonNull Function<T, R> sourceF) {
            this.sourceF = sourceF;
        }

        @Override
        public Resource<R> apply(Resource<T> source) {
            if (source == null)
                return null;

            Log.i(TAG, "apply: " + source);

            Resource.Status status = source.getStatus();
            if (status == Resource.Status.LOADING) {
                return Resource.Factory.loading();
            } else if (status == Resource.Status.SUCCESS) {
                return Resource.Factory.success(sourceF.apply(source.getData()));
            } else {
                return Resource.Factory.error(source.getMessage(), source.getCause());
            }
        }
    }
}

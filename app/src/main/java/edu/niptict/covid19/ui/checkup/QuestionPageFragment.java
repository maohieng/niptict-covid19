package edu.niptict.covid19.ui.checkup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.jommobile.android.jomutils.ui.BaseFragment;
import com.jommobile.android.jomutils.ui.JOMUIs;

import edu.niptict.covid19.R;
import edu.niptict.covid19.databinding.FragmentPageQuestionBinding;
import edu.niptict.covid19.databinding.ViewLevelScoreBinding;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class QuestionPageFragment extends BaseFragment {

    public class Handler {
        public void clickYes() {
            Log.i(TAG, "clickYes: ");
            CheckUpAnswer currentCheckUpValue = getCheckUpFragment().getCurrentCheckUpValue();
            if (currentCheckUpValue.getQuestion().getMaxScore() > 1) {
                JOMUIs.setBindingViewStubVisibility(mBinding.viewStub, true);
            } else {
                answer(1);
            }
        }

        public void clickOk() {
            int rating = mQuestion.getScore().get();
            Log.i(TAG, "clickOk: " + rating);
            answer(rating);
        }

        public void clickNo() {
            Log.i(TAG, "clickNo: ");
            JOMUIs.setBindingViewStubVisibility(mBinding.viewStub, false);
            answer(0);
        }
    }

    private static final String KEY_EXTRA_QUESTION = "EXTRA_QUESTION";
    private static final String TAG = "QuestionPageFragment";

    public static QuestionPageFragment newInstance(CheckUpAnswer question) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_EXTRA_QUESTION, question);
        QuestionPageFragment fragment = new QuestionPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    FragmentPageQuestionBinding mBinding;
    ViewLevelScoreBinding mViewScoreBinding;

    // TODO: 3/30/2020 this object is for display only
    CheckUpAnswer mQuestion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_page_question, container, false);

        mQuestion = getArguments().getParcelable(KEY_EXTRA_QUESTION);
        mBinding.setQuestion(mQuestion);

        final Handler actionsHandler = this.new Handler();
        mBinding.setHandler(actionsHandler);

        mBinding.viewStub.setOnInflateListener((viewStub, view) -> {
            mViewScoreBinding = (ViewLevelScoreBinding) mBinding.viewStub.getBinding();
            mViewScoreBinding.setQuestion(mQuestion.getQuestion());
            mViewScoreBinding.setHandler(actionsHandler);

            mViewScoreBinding.smileyRating.setSmileySelectedListener(type -> {
                Log.i(TAG, "onSmileySelected: " + type.getRating());
                mQuestion.setScore(type.getRating());
            });

            mViewScoreBinding.smileyRating.setMaxSmiley(mQuestion.getQuestion().maxScore);
            mViewScoreBinding.smileyRating.setReverseSmiley(true);
            mViewScoreBinding.smileyRating.setRating(1);

        });

        return mBinding.getRoot();
    }

    private void answer(int score) {
        boolean goNext = getCheckUpFragment().answerCurrentAndGoNext(score);
        if (!goNext) {
            FragmentActivity fragmentActivity = requireActivity();
            int totalScore = getCheckUpFragment().getTotalScore();
            getCheckUpActivity().setScoreResult(totalScore);
            fragmentActivity.finish();
        }
    }

    protected final CheckUpFragment getCheckUpFragment() {
        return (CheckUpFragment) getParentFragment();
    }

    protected final CheckUpActivity getCheckUpActivity() {
        return (CheckUpActivity) requireActivity();
    }
}

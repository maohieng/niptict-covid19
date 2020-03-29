package edu.niptict.covid19.ui.checkup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.jommobile.android.jomutils.ui.BaseFragment;

import java.util.Objects;

import edu.niptict.covid19.R;
import edu.niptict.covid19.databinding.FragmentPageQuestionBinding;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class QuestionPageFragment extends BaseFragment {

    private static final String KEY_EXTRA_QUESTION = "EXTRA_QUESTION";

    public static QuestionPageFragment newInstance(CheckUpQuestion question) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_EXTRA_QUESTION, question);
        QuestionPageFragment fragment = new QuestionPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    FragmentPageQuestionBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_page_question, container, false);
        CheckUpQuestion question = getArguments().getParcelable(KEY_EXTRA_QUESTION);
        mBinding.setQuestion(question);
        mBinding.ratingBar.setMax(Objects.requireNonNull(question).maxScore);

        return mBinding.getRoot();
    }
}

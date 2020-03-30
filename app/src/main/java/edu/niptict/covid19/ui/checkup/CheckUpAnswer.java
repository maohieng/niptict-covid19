package edu.niptict.covid19.ui.checkup;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpAnswer implements Parcelable {

    public static enum Status {
        UNDEFINED, ANSWERING, ANSWERED;
    }

    private final CheckUpQuestion question;
    private final ObservableInt score;
    private final ObservableField<Status> status;

    public CheckUpAnswer(CheckUpQuestion question) {
        this.question = question;
        score = new ObservableInt(0);
        status = new ObservableField<>(Status.UNDEFINED);
    }

    @SuppressWarnings("unchecked")
    protected CheckUpAnswer(Parcel in) {
        question = in.readParcelable(CheckUpQuestion.class.getClassLoader());
        score = in.readParcelable(ObservableInt.class.getClassLoader());
        status = (ObservableField<Status>) in.readSerializable();
    }

    public static final Creator<CheckUpAnswer> CREATOR = new Creator<CheckUpAnswer>() {
        @Override
        public CheckUpAnswer createFromParcel(Parcel in) {
            return new CheckUpAnswer(in);
        }

        @Override
        public CheckUpAnswer[] newArray(int size) {
            return new CheckUpAnswer[size];
        }
    };

    public void setScore(int score) {
        this.score.set(score);
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    public CheckUpQuestion getQuestion() {
        return question;
    }

    public ObservableInt getScore() {
        return score;
    }

    public ObservableField<Status> getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(question, i);
        parcel.writeParcelable(score, i);
        parcel.writeSerializable(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckUpAnswer answer = (CheckUpAnswer) o;

        if (!question.equals(answer.question)) return false;
        return score.equals(answer.score);
    }

    @Override
    public int hashCode() {
        int result = question.hashCode();
        result = 31 * result + score.hashCode();
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + question.toString() + ", " + status + "}";
    }
}

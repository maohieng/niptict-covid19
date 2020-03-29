package edu.niptict.covid19.ui.checkup;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpQuestion implements Parcelable {

    String question;
    int maxScore;
    int ordering;

    public CheckUpQuestion() {
    }

    public CheckUpQuestion(String question, int maxScore, int ordering) {
        this.question = question;
        this.maxScore = maxScore;
        this.ordering = ordering;
    }

    protected CheckUpQuestion(Parcel in) {
        question = in.readString();
        maxScore = in.readInt();
        ordering = in.readInt();
    }

    public static final Creator<CheckUpQuestion> CREATOR = new Creator<CheckUpQuestion>() {
        @Override
        public CheckUpQuestion createFromParcel(Parcel in) {
            return new CheckUpQuestion(in);
        }

        @Override
        public CheckUpQuestion[] newArray(int size) {
            return new CheckUpQuestion[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckUpQuestion that = (CheckUpQuestion) o;
        return maxScore == that.maxScore &&
                ordering == that.ordering &&
                question.equals(that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, maxScore, ordering);
    }

    public String getQuestion() {
        return question;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getOrdering() {
        return ordering;
    }

    @Override
    public String toString() {
        return "CheckUpQuestion{" +
                "question='" + question + '\'' +
                ", maxScore=" + maxScore +
                ", ordering=" + ordering +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeInt(maxScore);
        parcel.writeInt(ordering);
    }
}

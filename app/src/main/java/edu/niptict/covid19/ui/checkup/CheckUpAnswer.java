package edu.niptict.covid19.ui.checkup;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpAnswer {

    private final CheckUpQuestion question;
    private final int score;

    public CheckUpAnswer(CheckUpQuestion question, int score) {
        this.question = question;
        this.score = score;
    }

    public CheckUpQuestion getQuestion() {
        return question;
    }

    public int getScore() {
        return score;
    }
}

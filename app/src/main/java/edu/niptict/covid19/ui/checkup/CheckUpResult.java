package edu.niptict.covid19.ui.checkup;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpResult {

    private int minScore;
    private int maxScore;
    private String result;

    public CheckUpResult() {
    }

    public CheckUpResult(int minScore, int maxScore, String result) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.result = result;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckUpResult that = (CheckUpResult) o;

        if (minScore != that.minScore) return false;
        if (maxScore != that.maxScore) return false;
        return result.equals(that.result);
    }

    @Override
    public int hashCode() {
        int result1 = minScore;
        result1 = 31 * result1 + maxScore;
        result1 = 31 * result1 + result.hashCode();
        return result1;
    }

    @Override
    public String toString() {
        return "CheckUpResult{" +
                "minScore=" + minScore +
                ", maxScore=" + maxScore +
                ", result='" + result + '\'' +
                '}';
    }
}

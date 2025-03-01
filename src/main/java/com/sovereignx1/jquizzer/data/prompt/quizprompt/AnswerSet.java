package com.sovereignx1.jquizzer.data.prompt.quizprompt;

import java.util.List;

/**
 * This class holds a set of answer choices to an answer key. This class provides a facility to store these sets and
 * check if a user's answer is correct.
 */
public class AnswerSet {

    // List of possible answer choices
    private final List<String> mAnswers;

    // Answer key which indexes correspond to the array above.
    // i.e. the value of index 3 corresponds to the answer at index 3 of mAnswers
    private final List<Boolean> mAnswerKey;

    public AnswerSet(List<String> pAnswers, List<Boolean> pAnswerKey) {
        this.mAnswers = pAnswers;
        this.mAnswerKey = pAnswerKey;
    }

    /**
     * Check if chosen answer at given index is correct
     *
     * @param pIndex chosen answer to check if correct
     * @return if the chosen answer is correct
     */
    public boolean checkAnswer(int pIndex) {
        return mAnswerKey.get(pIndex);
    }

    /**
     * @return List of possible answer choices
     */
    public List<String> getAnswers() {
        return mAnswers;
    }
}

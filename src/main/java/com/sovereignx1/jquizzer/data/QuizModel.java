package com.sovereignx1.jquizzer.data;

import com.sovereignx1.jquizzer.data.prompt.QuizPrompt;

import java.util.List;

/**
 * This class is the data model which represents a "Quizzer." This includes whether it is a flash card set,
 * multi choice, etc.
 */
public class QuizModel {

    private final String mName;

    // True if Flash card set, false if traditional quiz
    private final boolean mIsFlashcards;

    private List<QuizPrompt> mQuestionList;

    public QuizModel(String pName, boolean pIsFlashcard) {
        mName = pName;
        mIsFlashcards = pIsFlashcard;
    }

    public boolean isFlashcards() {
        return mIsFlashcards;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return "QuizModel{" +
                "mName='" + mName + '\'' +
                ", mIsFlashcards=" + mIsFlashcards +
                '}';
    }
}

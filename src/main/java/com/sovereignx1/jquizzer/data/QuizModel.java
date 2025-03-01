package com.sovereignx1.jquizzer.data;

import com.sovereignx1.jquizzer.data.prompt.quizprompt.QuizPrompt;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the data model which represents a "Quizzer." This includes whether it is a flash card set,
 * multi choice, etc.
 */
public class QuizModel {

    private final String mName;

    // True if Flash card set, false if traditional quiz
    private final boolean mIsFlashcards;

    private final List<QuizPrompt> mQuestionList;

    public static final QuizModel DEFAULT_QUIZ = new QuizModelBuilder().build();

    private QuizModel(QuizModelBuilder pBldr) {
        mName = pBldr.mName;
        mIsFlashcards = pBldr.mIsFlashcards;
        mQuestionList = pBldr.mQuestionList;
    }

    public static class QuizModelBuilder {
        public String mName = "DEFAULT_NAME";

        // True if Flash card set, false if traditional quiz
        public boolean mIsFlashcards = false;

        public List<QuizPrompt> mQuestionList = new ArrayList<>();

        public QuizModelBuilder withName(String pName) {
            mName = pName;
            return this;
        }

        public QuizModelBuilder setFlashcards(boolean pIsFlashcards) {
            mIsFlashcards = pIsFlashcards;
            return this;
        }

        public QuizModelBuilder addQuestion(QuizPrompt pQuestion) {
            mQuestionList.add(pQuestion);
            return this;
        }

        public QuizModel build() {
            return new QuizModel(this);
        }
    }

    public boolean isFlashcards() {
        return mIsFlashcards;
    }

    public String getName() {
        return mName;
    }

    public List<QuizPrompt> getQuestionList() {
        return mQuestionList;
    }

    @Override
    public String toString() {
        return "QuizModel{" +
                "mName='" + mName + '\'' +
                ", mIsFlashcards=" + mIsFlashcards +
                ", mQuestionList=" + mQuestionList +
                '}';
    }
}

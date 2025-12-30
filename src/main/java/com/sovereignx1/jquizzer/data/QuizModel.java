package com.sovereignx1.jquizzer.data;

import com.sovereignx1.jquizzer.data.prompt.IQuizzerPrompt;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the data model which represents a "Quizzer." This includes whether it is a flash
 * card set, multi choice, etc.
 */
public class QuizModel {

    private final String mName;

    // True if Flash card set, false if traditional quiz
    private final boolean mIsFlashcards;

    private final List<IQuizzerPrompt> mPromptList;

    private QuizModel(QuizModelBuilder pBldr) {
        mName = pBldr.mName;
        mIsFlashcards = pBldr.mIsFlashcards;
        mPromptList = pBldr.mQuestionList;
    }

    public static class QuizModelBuilder {
        public String mName = "DEFAULT_NAME";

        // True if Flash card set, false if traditional quiz
        public boolean mIsFlashcards = false;

        /**
         * List of "questions." Questions can either be flashcard sets or actual quiz questions
         */
        public List<IQuizzerPrompt> mQuestionList = new ArrayList<>();

        public QuizModelBuilder withName(String pName) {
            mName = pName;
            return this;
        }

        public QuizModelBuilder setFlashcards(boolean pIsFlashcards) {
            mIsFlashcards = pIsFlashcards;
            return this;
        }

        public QuizModelBuilder addPrompt(IQuizzerPrompt pQuestion) {
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

    public List<IQuizzerPrompt> getPromptList() {
        return mPromptList;
    }

    @Override
    public String toString() {
        return "QuizModel{" +
               "mName='" + mName + '\'' +
               ", mIsFlashcards=" + mIsFlashcards +
               ", mQuestionList=" + mPromptList +
               '}';
    }
}

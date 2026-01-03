package com.sovereignx1.jquizzer.data.prompt.quizprompt;

import com.sovereignx1.jquizzer.data.prompt.IQuizzerPrompt;

public class QuizPrompt implements IQuizzerPrompt {

    private int mCorrectAnswerIndex;

    // Question to prompt the user
    private String mQuestion;
    // Set of answers for the given question
    private AnswerSet mAnswers;

    @Override
    public String getContent() {
        return "";
    }

    @Override
    public boolean doAction(int pSelectedAnsIndex) {
        // need to check pData to the correct answer, as pData will contain user answer
        return mAnswers.checkAnswer(pSelectedAnsIndex);

    }
}

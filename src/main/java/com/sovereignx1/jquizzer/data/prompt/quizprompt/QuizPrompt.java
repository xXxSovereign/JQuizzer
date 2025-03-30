package com.sovereignx1.jquizzer.data.prompt.quizprompt;

import com.sovereignx1.jquizzer.data.prompt.IQuizzerPrompt;

public class QuizPrompt implements IQuizzerPrompt {

    @Override
    public String getContent() {
        return "";
    }

    @Override
    public void doAction(int pData) {
        // need to check pData to the correct answer, as pData will contain user answer

    }
}

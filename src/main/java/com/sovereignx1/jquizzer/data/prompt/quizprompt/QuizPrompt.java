package com.sovereignx1.jquizzer.data.prompt.quizprompt;

import com.sovereignx1.jquizzer.data.prompt.IQuizPrompt;
import javafx.scene.Node;

public class QuizPrompt implements IQuizPrompt {

    // This represents the question to prompt the user with
    private String mQuestion;

    private AnswerSet mAnswerSet;

    public QuizPrompt(String question, AnswerSet answerSet) {}

    @Override
    public Node getRootNode() {
        return null;
    }
}

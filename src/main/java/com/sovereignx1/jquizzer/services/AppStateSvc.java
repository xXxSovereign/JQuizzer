package com.sovereignx1.jquizzer.services;

import com.sovereignx1.jquizzer.data.QuizModel;

/**
 * This class represents a service which holds information about the current state of the quizzer
 */
public class AppStateSvc {

    private static QuizModel sCurrentQuiz;

    public static QuizModel getsCurrentQuiz() {
        return sCurrentQuiz;
    }

    public static void setsCurrentQuiz(QuizModel sCurrentQuiz) {
        AppStateSvc.sCurrentQuiz = sCurrentQuiz;
    }

}

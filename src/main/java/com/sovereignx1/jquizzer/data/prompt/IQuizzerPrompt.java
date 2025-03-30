package com.sovereignx1.jquizzer.data.prompt;

/**
 * Interface abstracting the interface to Flashcards and Quizzes
 */
public interface IQuizzerPrompt {

    /**
     * This will return the primary content (Front text for Flashcard, and Question text for Quiz)
     */
    String getContent();

    /**
     * This will flip a flashcard, or check an answer for quizzes.
     *
     * @param pData Data to pass to the action method, will be unused for flashcards, but will be a
     * user entered answer index for quizzes
     */
    void doAction(int pData);
}

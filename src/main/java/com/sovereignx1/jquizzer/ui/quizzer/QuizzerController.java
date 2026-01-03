package com.sovereignx1.jquizzer.ui.quizzer;

import com.sovereignx1.jquizzer.data.QuizModel;
import com.sovereignx1.jquizzer.data.prompt.IQuizzerPrompt;
import com.sovereignx1.jquizzer.services.AppStateSvc;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * This class handles managing the Quizzer environment. I.e. handling if we are doing flash cards vs
 * quiz and other stuff
 */
public class QuizzerController {
    private final ILogger mLog = LoggerManager.getLogger();

    @FXML
    private VBox mQuizzerRoot;
    @FXML
    private Label mTitleLabel;
    // Label inside content box, used for flashcard text and main question text on quizzers
    @FXML
    private Label mContentLabel;
    @FXML
    private VBox mContentBox;
    @FXML
    private HBox mQuizzerActionsBox;

    private QuizModel mQuizModel;

    private int selectedPromptIndex = 0;

    @FXML
    private void initialize() {
        mLog.info("QuizzerController initialized");

        // Load current quiz from AppState, then populate the view from the model
        mQuizModel = AppStateSvc.getLoadedQuiz();

        List<IQuizzerPrompt> quizModelPrompts = mQuizModel.getPromptList();

        if (mQuizModel.isFlashcards()) {
            // configure as flashcards

            mTitleLabel.setText("Flashcard Set: " + mQuizModel.getName());

            // Populate with first Flashcard's content
            mContentLabel.setText(quizModelPrompts.get(selectedPromptIndex).getContent());

            Button flashcardFlipBtn = new Button("Flip");
            // User data is unneeded for flashcards, it is used for quizzes and the correct answer
            flashcardFlipBtn.setUserData(-1);
            flashcardFlipBtn.setOnAction(this::handleAction);

            mQuizzerActionsBox.getChildren().add(flashcardFlipBtn);

        } else {
            // configure as quiz
            // do nothing for now

        }
    }

    /*
     * This method will be called by either the flip button for Flashcards, or answer buttons for
     *  quizzes. The buttons will have user data inside them. This data is ignored for flashcards,
     *  and used to check for correct answers with quizzes
     */
    private void handleAction(ActionEvent pActionEvent) {
        // We manually create these buttons, and int will only ever be put in.
        // Currently, user data is only ever set to -1, but when I add quiz support in this will
        // just work.


        int btnData = (int) ((Button) pActionEvent.getSource()).getUserData();
        getCurrentQuizPrompt().doAction(btnData);

        if (mQuizModel.isFlashcards()) {
            // Flashcards flip, so update text
            mContentLabel.setText(getCurrentQuizPrompt().getContent());
        }
    }

    // Switch to next prompt, if there is one
    @FXML
    private void onNext() {
        if (selectedPromptIndex == mQuizModel.getPromptList().size() - 1) {
            // Do nothing if we are at the last prompt
            return;
        }
        selectedPromptIndex++;
        updateContentFromCurrentPrompt();
    }

    // Switch to previous prompt, if there is one
    @FXML
    private void onBack() {
        if (selectedPromptIndex == 0) {
            // Do nothing if we are at the last prompt
            return;
        }
        selectedPromptIndex--;
        updateContentFromCurrentPrompt();
    }

    private IQuizzerPrompt getCurrentQuizPrompt() {
        return mQuizModel.getPromptList().get(selectedPromptIndex);
    }

    private void updateContentFromCurrentPrompt() {
        mContentLabel.setText(getCurrentQuizPrompt().getContent());
    }
}

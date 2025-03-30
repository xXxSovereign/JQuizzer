package com.sovereignx1.jquizzer.ui.mainmenu.newquiz;

import com.sovereignx1.jquizzer.data.QuizModel;
import com.sovereignx1.jquizzer.services.AppStateSvc;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NewQuizzerDialogController {

    private static final PseudoClass ERROR_CLASS = PseudoClass.getPseudoClass("error");

    private static final String FLASHCARD_CREATOR_FXML = "FlashcardCreator.fxml";

    private static final String QUESTION_CREATOR_FXML = "QuestionCreator.fxml";

    private static final ILogger sLog = LoggerManager.getLogger();

    @FXML
    public VBox mNewDialogRoot;

    @FXML
    private ToggleGroup mToggleGroup;

    @FXML
    private TextField mNameField;

    @FXML
    private HBox mRadioButtonBox;

    @FXML
    public void initialize() {

        sLog.info("init new quizzer dialog bruv");

        // Setup actions on textbox change
        mNameField.textProperty().addListener((pObs, pOld, pNew) -> {
            if (!pNew.isEmpty()) {
                mNameField.pseudoClassStateChanged(ERROR_CLASS, false);
            }
        });

        // Setup actions on toggle change
        mToggleGroup.selectedToggleProperty().addListener((pObs, pOld, pNew) ->
                                                                  mRadioButtonBox.getStyleClass()
                                                                          .removeAll("error"));
    }

    /**
     * This method is called when the user clicks the "Create Answers" button. This will change the
     * current window
     *
     * @throws IOException Will throw IOException if the fxml is failed to load for any reason
     */
    @FXML
    private void submitAction(ActionEvent event) throws IOException {


        if (mNameField.getText().isEmpty()) {
            mNameField.pseudoClassStateChanged(ERROR_CLASS, true);
            return;
        } else if (mToggleGroup.getSelectedToggle() == null) {
            mRadioButtonBox.getStyleClass().add("error");
            return;
        }

        // In the fxml file, the user data value is set to either true or false
        boolean isFlashcards =
                Boolean.parseBoolean((String) mToggleGroup.getSelectedToggle().getUserData());

        QuizModel.QuizModelBuilder bldr = new QuizModel.QuizModelBuilder()
                .withName(mNameField.getText())
                .setFlashcards(isFlashcards);

        mNewDialogRoot.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                isFlashcards ? FLASHCARD_CREATOR_FXML : QUESTION_CREATOR_FXML));
        Parent root = loader.load();
        mNewDialogRoot.getChildren().setAll(root);
        IQuizzerCreator ctrl = loader.getController();
        ctrl.setBuilder(bldr);
    }

    /**
     * This method will be called by either the Flashcard or Quiz creation dialog. We pass the bldr
     * to the creation dialogs in the method above, and they will call this method upon submission.
     *
     * @param pQuizModel the QuizModel to load in the Quizzer View
     */
    public static void submitQuizzerPrompts(QuizModel pQuizModel) {
        AppStateSvc.loadQuiz(pQuizModel);
    }

}

package com.sovereignx1.jquizzer.ui.mainmenu.newquiz;

import com.sovereignx1.jquizzer.JQuizzerAppMain;
import com.sovereignx1.jquizzer.data.QuizModel;
import com.sovereignx1.jquizzer.services.AppStateSvc;
import com.sovereignx1.jquizzer.ui.quizzer.QuizzerController;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.css.PseudoClass;

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
                mRadioButtonBox.getStyleClass().removeAll("error"));
    }

    /**
     * This method is called when the user clicks the "Create Answers" button.
     * This will change the current window
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
        boolean isFlashcards = Boolean.parseBoolean((String) mToggleGroup.getSelectedToggle().getUserData());

        // TODO: Still need to implement actually creating the questions. Will likely be a different popup which
        // will populate some fields, and then we will retrieve the results here.

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

        // All this code will transform the main window into the quizzer window
        // it is dead for now until I am done with implementing question creation
        // This will all get moved to the individual CreatorControllers
        if (false) {

            QuizModel newQuiz = bldr.build();

            // Since this is the new quiz dialog, the next quiz will be the new one
            AppStateSvc.setsCurrentQuiz(newQuiz);

            System.out.println(newQuiz);


            // Load the Quizzer fxml, QuizzerController will then query the AppStateSvc to get the quiz details
            // Set up new window for the QuizzerController
            FXMLLoader testloader = new FXMLLoader(QuizzerController.class.getResource("Quizzer.fxml"));

            // load root
            Parent testroot = loader.load();

            // Create the scene
            Scene scene = new Scene(testroot);

            JQuizzerAppMain.changeScene(scene);

            // Close the current stage (popup window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }

    }

}

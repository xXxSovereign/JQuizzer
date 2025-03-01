package com.sovereignx1.jquizzer.ui.mainmenu;

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
import javafx.stage.Stage;
import javafx.css.PseudoClass;

import java.io.IOException;

public class NewQuizzerDialogController {

    private static final PseudoClass ERROR_CLASS = PseudoClass.getPseudoClass("error");

    private static final ILogger sLog = LoggerManager.getLogger();

    @FXML
    private ToggleGroup mToggleGroup;

    @FXML
    private TextField mNameField;

    @FXML
    private HBox mRadioButtonBox;

    @FXML
    public void initialize() {

        sLog.info("init new quizzer dialog");

        mNameField.textProperty().addListener((pObs, pOld, pNew) -> {
            if (!pNew.isEmpty()) {
                mNameField.pseudoClassStateChanged(ERROR_CLASS, false);
            }
        });

        mToggleGroup.selectedToggleProperty().addListener((pObs, pOld, pNew) ->
                mRadioButtonBox.getStyleClass().removeAll("error"));
    }

    /**
     *
     * @throws IOException Will throw IOException if the fxml is failed to load for any reason
     */
    @FXML
    private void submitAction(ActionEvent event) throws IOException {


        if (mNameField.getText().isEmpty()) {
            mNameField.pseudoClassStateChanged(ERROR_CLASS, true);
            return;
        } else if (mToggleGroup.getSelectedToggle() == null){
            mRadioButtonBox.getStyleClass().add("error");
            return;
        }

        boolean isFlashcards = Boolean.parseBoolean((String) mToggleGroup.getSelectedToggle().getUserData());
        QuizModel newQuiz = new QuizModel(mNameField.getText(), isFlashcards);

        // TODO: Still need to implement actually creating the questions. Will likely be a different popup which
        // will populate some fields, and then we will retrieve the results here.

        // Since this is the new quiz dialog, the next quiz will be the new one
        AppStateSvc.setsCurrentQuiz(newQuiz);

        System.out.println(newQuiz);

        // Load the Quizzer fxml, QuizzerController will then query the AppStateSvc to get the quiz details


        // Set up new window for the QuizzerController
        FXMLLoader loader = new FXMLLoader(QuizzerController.class.getResource("Quizzer.fxml"));

        // load root
        Parent root = loader.load();

        // Create the scene
        Scene scene = new Scene(root);

        JQuizzerAppMain.changeScene(scene);

        // Close the current stage (popup window)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

package com.sovereignx1.jquizzer.ui.mainmenu;

import com.sovereignx1.jquizzer.JQuizzerAppMain;
import com.sovereignx1.jquizzer.ui.quizzer.QuizzerController;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class NewQuizzerDialogController {

    private static final ILogger sLog = LoggerManager.getLogger();

    @FXML
    private ToggleGroup mToggleGroup;

    @FXML
    public void initialize(){

        sLog.info("NEW QUIZZER DIALOG ACTIVATEDDDDDDDDDD");

    }

    /**
     *
     * @throws IOException Will throw IOException if the fxml is failed to load for any reason
     */
    @FXML
    private void submitAction(ActionEvent event) throws IOException {

        // TODO:
        // Either need to implement updates on the radio button and text field to only unlock this button when they
        // are filled out, or add logic here to check and show an alert if missing fields


        FXMLLoader loader = new FXMLLoader(QuizzerController.class.getResource("Quizzer.fxml"));

        // load root
        Parent root = loader.load();

        // Create the scene
        Scene scene = new Scene(root);

        JQuizzerAppMain.changeScene(scene);

        // Close the current stage (popup window)
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

package com.sovereignx1.jquizzer.ui.mainmenu;

import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class NewQuizzerDialogController {

    private static final ILogger sLog = LoggerManager.getLogger();

    @FXML
    public void initialize(){

        sLog.info("NEW QUIZZER DIALOG ACTIVATEDDDDDDDDDD");

    }

    public void closePopup(ActionEvent event) {
        // Close the current stage (popup window)
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

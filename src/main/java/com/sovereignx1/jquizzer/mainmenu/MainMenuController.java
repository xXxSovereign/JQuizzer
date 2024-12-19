package com.sovereignx1.jquizzer.mainmenu;

import com.sovereignx1.jquizzer.JQuizzerAppMain;
import com.sovereignx1.jquizzer.logger.ILogger;
import com.sovereignx1.jquizzer.logger.LoggerManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class MainMenuController {
    @FXML
    private VBox mRoot;

    private final ILogger mLog = LoggerManager.getLogger();

    @FXML
    private void newDialog(){
        mLog.info("executing new");

    }

    @FXML
    private void loadDialog(){
        mLog.info("executing load");

    }

    @FXML
    private void optionDialog(){
        mLog.info("executing options");

    }

    @FXML
    private void exitDialog(){
        mLog.info("executing exit dialog");

        // Build small alert popup, and if confirmed exit application
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit Confirmation");
        exitAlert.setHeaderText(null);
        exitAlert.setContentText("Do you really need to exit ?");

        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);

        exitAlert.getButtonTypes().setAll(yesBtn, noBtn);
        exitAlert.initOwner(JQuizzerAppMain.mStage);
        exitAlert.showAndWait();
        if (exitAlert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
            mLog.info("Exit Acknowledged, proceeding...");
            JQuizzerAppMain.exit();
        }

    }


}

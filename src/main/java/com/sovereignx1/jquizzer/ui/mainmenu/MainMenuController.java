package com.sovereignx1.jquizzer.ui.mainmenu;

import com.sovereignx1.jquizzer.JQuizzerAppMain;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenuController {

    @FXML
    private VBox mRoot;

    private final ILogger mLog = LoggerManager.getLogger();

    private static final String NEW_DIALOG_FXML = "NewQuizzerDialog.fxml";


    @FXML
    private void newDialog() {
        try {

            mLog.info("executing substitution test {} {} {} ------", "LOL", "LMAO");
            mLog.info("executing new {} ------:", "test", "test23");
            mLog.info("yoooo", "error lol", "extra values");
            mLog.info("yooo {}");

            FXMLLoader loader = new FXMLLoader(getClass().getResource(NEW_DIALOG_FXML));
            Parent popupRoot = loader.load();

            // Create the popup stage
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            popupStage.setTitle("Popup Window");

            Scene popupScene = new Scene(popupRoot);
            popupStage.setScene(popupScene);
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void loadDialog() {
        mLog.info("executing load");

    }

    @FXML
    private void optionDialog() {
        mLog.info("executing options");

    }

    @FXML
    private void exitDialog() {
        mLog.info("executing exit dialog");

        // TODO: Overhaul this gay ass exit dialog with a custom FXML that does the same stuff. should be ez

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

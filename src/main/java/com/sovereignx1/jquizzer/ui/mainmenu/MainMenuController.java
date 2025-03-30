package com.sovereignx1.jquizzer.ui.mainmenu;

import com.sovereignx1.jquizzer.JQuizzerAppMain;
import com.sovereignx1.jquizzer.util.ExitManager;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private VBox mRoot;

    @FXML
    private Label mTitleLbl;

    private static final long TITLE_FLASH_RATE_MILLIS = 100;

    private final ILogger mLog = LoggerManager.getLogger();

    private static final String NEW_DIALOG_FXML = "newquiz/NewQuizzerDialog.fxml";

    @FXML
    private void initialize() {

        // Spawn a new simple thread to flash the title like a neon sign
        Thread titleBlinker = new Thread(() -> {
            try {
                for (long i = 0; i < Long.MAX_VALUE; i++) {
                    Thread.sleep(TITLE_FLASH_RATE_MILLIS);
                    mTitleLbl.pseudoClassStateChanged(PseudoClass.getPseudoClass("hover"), true);
                    Thread.sleep(TITLE_FLASH_RATE_MILLIS / 4);
                    mTitleLbl.pseudoClassStateChanged(PseudoClass.getPseudoClass("hover"), false);
                }
            } catch (InterruptedException e) {
                mLog.debug(
                        "MainMenuTitleBlinker has been interrupted, usually due to application " +
                        "exit");
            }
        });

        titleBlinker.setDaemon(true);
        titleBlinker.start();

        // Register this thread to die when we exit
        ExitManager.register(titleBlinker);


    }

    @FXML
    private void newDialog() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(NEW_DIALOG_FXML));
            Parent popupRoot = loader.load();

            // Create the popup stage
            Stage popupStage = new Stage();
            popupStage.initModality(
                    Modality.APPLICATION_MODAL); // Block interaction with other windows
            popupStage.setTitle("New Quizzer Dialog");

            Scene popupScene = new Scene(popupRoot);
            popupStage.setMinHeight(500);
            popupStage.setMinWidth(600);
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

        // TODO: Overhaul this exit dialog with a custom FXML that does the same stuff. should be ez
        // Related to above, maybe make a cool generic Alert/Confirmation Dialog factory, might
        // be useful tbh

        // Build small alert popup, and if confirmed exit application
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit Confirmation");
        exitAlert.setHeaderText(null);
        exitAlert.setContentText("Do you really want to exit ?");


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

package com.sovereignx1.jquizzer.services;

import com.sovereignx1.jquizzer.JQuizzerAppMain;
import com.sovereignx1.jquizzer.data.QuizModel;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class represents a service which holds information about the current state of the quizzer
 */
public class AppStateSvc {

    private static final ILogger sLog = LoggerManager.getLogger();

    private static QuizModel sCurrentQuiz;

    private static Stage mStage;

    /**
     * This will be called by the new quiz dialog or quiz loader, will change the main window to the
     * Quiz View as defined by Quizzer.fxml
     */
    public static void loadQuiz(QuizModel pQuiz) {
        sLog.info("Loading Quiz View...");
        sLog.debug("Quiz Model: " + pQuiz);

        // Set the quiz before changing scene, as the Quizzer view will load the current quiz
        // on initialization
        sCurrentQuiz = Objects.requireNonNull(pQuiz);
        changeScene("./ui/quizzer/Quizzer.fxml");


    }

    /**
     * Changes the scene to the scene as described by the given fxml file.
     *
     * @param fxml String path to the fxml to load the new scene from
     */
    public static void changeScene(String fxml) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(Objects.requireNonNull(JQuizzerAppMain.class.getResource(fxml)));
            Parent pane = loader.load();

            mStage.getScene().setRoot(pane);
        } catch (Exception e) {
            sLog.error("Something went wrong changing scenes... " + e);
            throw new RuntimeException(e);
        }
    }

    public static void changeScene(Scene pScene) {
        mStage.setScene(pScene);
    }

    public static void setStage(Stage pStage) {
        mStage = pStage;
    }

    public static QuizModel getLoadedQuiz() {
        if (sCurrentQuiz == null) {
            throw new RuntimeException("Tried to load Quiz Model but it has not yet been loaded");
        }
        return sCurrentQuiz;
    }


}

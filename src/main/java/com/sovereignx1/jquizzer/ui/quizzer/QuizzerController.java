package com.sovereignx1.jquizzer.ui.quizzer;

import com.sovereignx1.jquizzer.data.QuizModel;
import com.sovereignx1.jquizzer.services.AppStateSvc;
import io.reactivex.Observable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * This class handles managing the Quizzer environment. I.e. handling if we are doing flash cards vs quiz and other stuff
 */
public class QuizzerController {

    @FXML
    public VBox mQuizzerRoot;

    @FXML
    private ScrollPane mTestPane;

    @FXML
    private VBox mHistory;

    @FXML
    private void initialize() {
        mTestPane.setFitToWidth(true);

        // Load current quiz from AppState, then populate the view from the model
        QuizModel currentQuiz = AppStateSvc.getsCurrentQuiz();

        System.out.println("QuizzerController: " + currentQuiz);


        Observable.interval(1, TimeUnit.SECONDS).subscribe(pTick -> {
            Platform.runLater(() -> {
                mHistory.getChildren().add(0, new Separator());
                mHistory.getChildren().add(0, new Label("Test penis Message"));
            });
        });
    }
}

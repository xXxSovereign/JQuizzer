package com.sovereignx1.jquizzer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sovereignx1.jquizzer.util.ExitManager;
import com.sovereignx1.jquizzer.util.appctx.ApplicationContext;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

/**
 * Main application class for JQuizzer
 * <p>
 * This class handles setting up the JavaFX environment and FXMLLoader. Additionally, this class will start all
 * services
 */
public class JQuizzerAppMain extends Application {

    private static final ILogger sLog;
    public static Stage mStage;

    static {
        ApplicationContext.initialize(JQuizzerAppCtx.class, "JQuizzerAppCtx.json");
        sLog = LoggerManager.getLogger();
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            mStage = primaryStage;

            // Load the FXML file and set a Guice Injector to create controller classes (for DI)
            Injector injector = Guice.createInjector(new JQuizzerModule());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JQuizzerApp.fxml"));

            loader.setControllerFactory(injector::getInstance);

            // load root
            Parent root = loader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Set up the stage
            mStage.setTitle("JQuizzer");

            mStage.setScene(scene);

            // Im not a good enough dev yet to allow this
            // once im good with layout managers frfr ong no cap this can be unlocked
            mStage.setResizable(false);
            mStage.show();

            // Clean all threads on exit
            mStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    exit();
                }
            });

            sLog.info("Parent fxml loaded...");


        } catch (Exception e) {
            sLog.error("Error encountered in initializing JQuizzer: ", e);
        }
    }

    // Utility Methods

    public static void changeScene(String fxml) {
        try {
            Parent pane = FXMLLoader.load(Objects.requireNonNull(JQuizzerAppMain.class.getResource(fxml)));
            mStage.getScene().setRoot(pane);
        } catch (Exception e) {
            sLog.error("OOPS CHANGING SCENE MESS UP !!! " + e);
        }


    }

    public static void changeScene(Scene pScene) {
        mStage.setScene(pScene);
    }

    public static void exit() {

        sLog.info("exiting gracefully...");
        ExitManager.exit();
        Platform.exit();
    }
}

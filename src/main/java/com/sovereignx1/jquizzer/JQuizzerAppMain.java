package com.sovereignx1.jquizzer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sovereignx1.jquizzer.services.AppStateSvc;
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

/**
 * Main application class for JQuizzer
 * <p>
 * This class handles setting up the JavaFX environment and FXMLLoader. Additionally, this class
 * will start all services
 */
public class JQuizzerAppMain extends Application {

    private static final ILogger sLog;
    public static Stage mStage;

    private static final int WINDOW_MIN_HEIGHT = 600;
    private static final int WINDOW_MIN_WIDTH = 400;

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

            primaryStage.setMinWidth(WINDOW_MIN_WIDTH);
            primaryStage.setMinHeight(WINDOW_MIN_HEIGHT);


            mStage.show();

            // Give stage to AppStateSvc to handle changing main window to quizzer view
            AppStateSvc.setStage(mStage);

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

    public static void exit() {

        sLog.info("exiting gracefully...");
        ExitManager.exit();
        Platform.exit();
        System.exit(0);
    }
}

package com.sovereignx1.jquizzer.ui.mainmenu.newquiz.question;

import com.sovereignx1.jquizzer.data.QuizModel;
import com.sovereignx1.jquizzer.ui.mainmenu.newquiz.IQuizzerCreator;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class QuestionCreatorController implements IQuizzerCreator {

    private static final ILogger sLog = LoggerManager.getLogger();

    @FXML
    private VBox mRoot;

    @FXML
    public void initialize() {

    }

    @Override
    public void setBuilder(QuizModel.QuizModelBuilder pBuilder) {
        sLog.info("setBuilder for Quiz");
    }
}

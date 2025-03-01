package com.sovereignx1.jquizzer.ui.mainmenu.newquiz;

import com.sovereignx1.jquizzer.data.QuizModel;
import com.sovereignx1.jquizzer.ui.fx.FlashcardDataLabel;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.util.Objects;

public class FlashcardCreatorController implements IQuizzerCreator {

    private static final ILogger sLog = LoggerManager.getLogger();

    @FXML
    private VBox mRoot;
    @FXML
    private Label mFlashcardTitle;
    @FXML
    private ScrollPane mFlashcardScrollPane;
    @FXML
    private VBox mFlashcardScrollPaneContents;
    @FXML
    private TextArea mFrontTextArea;
    @FXML
    private TextArea mBackTextArea;
    @FXML
    private Button mAddBtn;
    @FXML
    private Button mSubmitBtn;

    private QuizModel.QuizModelBuilder mQuizBldr;

    private final ObservableList<FlashcardDataLabel> mFlashcardDataLabels = FXCollections.observableArrayList();

    private int mFlashcardCounter = 0;

    @FXML
    public void initialize() {
        sLog.info("FlashcardCreatorController initialized");

        mFlashcardScrollPane.setContent(mFlashcardScrollPaneContents);

        // Initially disable add and submit, then unlock when the second text field changes.
        // Poor mans check of if both boxes have text. Will double-check later too
        mAddBtn.setDisable(true);
        mSubmitBtn.setDisable(true);

        mBackTextArea.textProperty().addListener(pUnused -> {
            mAddBtn.setDisable(false);
        });

        mFlashcardDataLabels.addListener((ListChangeListener<? super FlashcardDataLabel>) pChange -> {
            // Have to get next change
            if (pChange.next()) {
                mFlashcardScrollPaneContents.getChildren().addAll(pChange.getAddedSubList());
            }
        });
    }

    @Override
    public void setBuilder(QuizModel.QuizModelBuilder pBuilder) {
        sLog.info("setBuilder for Flashcard");
        mQuizBldr = Objects.requireNonNull(pBuilder);

        Platform.runLater(() -> mFlashcardTitle.setText("Flashcard set: " + mQuizBldr.mName));
    }

    @FXML
    private void onAdd(){
        String name, front, back;
        mFlashcardCounter++;

        name = "Flashcard " + mFlashcardCounter;
        front = mFrontTextArea.getText();
        back = mBackTextArea.getText();
        mFlashcardDataLabels.add(new FlashcardDataLabel(name, front, back, this::onFlashcardSelect));

        mFrontTextArea.clear();
        mBackTextArea.clear();

        // Now that we have at least one entry, unlock submit
        mSubmitBtn.setDisable(false);
    }

    @FXML
    public void submit() {
        // TODO: implement
    }

    private void onFlashcardSelect(MouseEvent pEvent) {
        // This is always bound on a FlashcardDataLabel, so this is safe
        FlashcardDataLabel t = (FlashcardDataLabel)(pEvent.getSource());
        System.out.println("Front: " + t.getFrontText() + " / Back: " + t.getBackText());
    }
}

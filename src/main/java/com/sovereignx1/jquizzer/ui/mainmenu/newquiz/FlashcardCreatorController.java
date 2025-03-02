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
    private Button mSaveBtn;
    @FXML
    private Button mSubmitBtn;

    private QuizModel.QuizModelBuilder mQuizBldr;

    private String mFlashcardTitleText;

    private final ObservableList<FlashcardDataLabel> mFlashcardDataLabels = FXCollections.observableArrayList();

    private FlashcardDataLabel mSelectedFlashcard;

    private int mFlashcardCounter = 0;

    @FXML
    public void initialize() {
        sLog.info("FlashcardCreatorController initialized");

        mFlashcardScrollPane.setContent(mFlashcardScrollPaneContents);

        // Initially disable add and submit, then unlock when the second text field changes.
        // Poor mans check of if both boxes have text. Will double-check later too
        mSaveBtn.setDisable(true);
        mSubmitBtn.setDisable(true);

        mBackTextArea.textProperty().addListener(pUnused -> {
            mSaveBtn.setDisable(false);
        });

        // Setup listener to update the scroll pane when new flash cards are added
        mFlashcardDataLabels.addListener((ListChangeListener<? super FlashcardDataLabel>) pChange -> {
            // Have to get next change
            if (pChange.next()) {
                mFlashcardScrollPaneContents.getChildren().addAll(pChange.getAddedSubList());
                mFlashcardScrollPaneContents.getChildren().removeAll(pChange.getRemoved());


                mSubmitBtn.setDisable(mFlashcardDataLabels.isEmpty());

            }
        });

        // Construct default flash card, and manually trigger all selection logic to populate gui
        FlashcardDataLabel defaultFlashcard = getNewFlashcard();

        mFlashcardDataLabels.add(defaultFlashcard);
        populateTextFromFlashcard();
        mSelectedFlashcard = defaultFlashcard;
        mSelectedFlashcard.setSelected(true);
    }

    /**
     * This method is called by NewQuizzerDialogController immediately after this class is constructed. This allows this
     * class to get the starter quiz information, and to add the flashcards to once finished creating.
     *
     * @param pBuilder QuizModelBuilder to add completed flashcards to
     */
    @Override
    public void setBuilder(QuizModel.QuizModelBuilder pBuilder) {
        sLog.info("setBuilder for Flashcard");
        mQuizBldr = Objects.requireNonNull(pBuilder);
        mFlashcardTitleText = "Flashcard set: " + mQuizBldr.mName;

        Platform.runLater(() -> mFlashcardTitle.setText(mFlashcardTitleText));
    }

    @FXML
    public void onNew() {
        FlashcardDataLabel newFlashcard = getNewFlashcard();

        saveFromText();

        mSelectedFlashcard.setSelected(false);
        mSelectedFlashcard = newFlashcard;
        mFlashcardDataLabels.add(mSelectedFlashcard);
        mSelectedFlashcard.setSelected(true);

        populateTextFromFlashcard();
    }

    @FXML
    public void onSubmit() {
        // TODO: implement
    }

    // Callback and utility Methods ==================

    private void onFlashcardSelect(MouseEvent pEvent) {
        // This is always bound on a FlashcardDataLabel, so this is safe
        FlashcardDataLabel newFlashcard = (FlashcardDataLabel) (pEvent.getSource());

        // If new flashcard is different than current
        if (!(newFlashcard.getFlashcardIndex() == mSelectedFlashcard.getFlashcardIndex())) {
            // save current flashcard
            saveFromText();
            // Unselect old
            mSelectedFlashcard.setSelected(false);

            // Select new
            mSelectedFlashcard = newFlashcard;
            mSelectedFlashcard.setSelected(true);
            populateTextFromFlashcard();
        }
    }

    private void onFlashcardDelete(MouseEvent pEvent) {
        // This always comes from a delete button, so this is safe
        Button btn = (Button) (pEvent.getSource());
        // Delete buttons are initialized to have the flashcard index stored in them
        int index = (int) btn.getUserData();

        // If we delete the current one, clear the text fields
        if (index == mSelectedFlashcard.getFlashcardIndex()) {
            Platform.runLater(() -> {
                mFrontTextArea.setText("");
                mBackTextArea.setText("");
            });
        }

        mFlashcardDataLabels.remove(index);

        // rename all flash cards to avoid jumps in naming
        for (int i = 0; i < mFlashcardDataLabels.size(); i++) {
            mFlashcardDataLabels.get(i).setName("Flashcard " + (i + 1));
            mFlashcardDataLabels.get(i).setFlashcardIndex(i);
        }
        mFlashcardCounter = mFlashcardDataLabels.size();
    }

    private void populateTextFromFlashcard() {
        Platform.runLater(() -> {
            mFrontTextArea.setText(mSelectedFlashcard.getFrontText());
            mBackTextArea.setText(mSelectedFlashcard.getBackText());
        });
    }

    private FlashcardDataLabel getNewFlashcard() {
        // Increment counter, and offset by 1 for name (there is no flashcard 0)
        return new FlashcardDataLabel("Flashcard " + (mFlashcardCounter + 1), "Sample Front Text", "Sample Back Text",
                                      mFlashcardCounter++,
                                      this::onFlashcardSelect, this::onFlashcardDelete);
    }

    // This method can be called either from the save button or internal logic. This is primarily for utility needs.
    @FXML
    private void saveFromText() {
        String front, back;

        front = mFrontTextArea.getText();
        back = mBackTextArea.getText();

        mSelectedFlashcard.setFrontText(front);
        mSelectedFlashcard.setBackText(back);

        // Now that we have at least one entry, unlock submit
        mSubmitBtn.setDisable(false);
    }
}

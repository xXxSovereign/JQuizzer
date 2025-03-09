package com.sovereignx1.jquizzer.ui.mainmenu.newquiz.flashcard;

import com.sovereignx1.jquizzer.data.QuizModel;
import com.sovereignx1.jquizzer.ui.mainmenu.newquiz.IQuizzerCreator;
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
    private Button mSubmitBtn;

    private QuizModel.QuizModelBuilder mQuizBldr;

    private String mFlashcardTitleText;

    private final ObservableList<FlashcardDataLabel> mFlashcardDataLabels =
            FXCollections.observableArrayList();

    private FlashcardDataLabel mSelectedFlashcard;

    private int mFlashcardCounter = 0;

    // This allows us to know if we just deleted the selected flashcard, and allows us to select
    // the same (really new) flashcard. If we didn't use this, and you delete card 3, then try to
    // select the new card 3, it wouldn't work.
    private boolean mDeleteSelectedFlag = false;

    @FXML
    public void initialize() {
        sLog.info("FlashcardCreatorController initialized");

        mFlashcardScrollPane.setContent(mFlashcardScrollPaneContents);

        // Initially disable submit, unlock when the save button is clicked
        mSubmitBtn.setDisable(true);

        // Setup listener to update the scroll pane when new flash cards are added
        mFlashcardDataLabels.addListener(
                (ListChangeListener<? super FlashcardDataLabel>) pChange -> {
                    // Have to get next change
                    if (pChange.next()) {
                        mFlashcardScrollPaneContents.getChildren()
                                .addAll(pChange.getAddedSubList());
                        mFlashcardScrollPaneContents.getChildren().removeAll(pChange.getRemoved());

                        // If all flashcards are deleted, disable submit
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
     * This method is called by NewQuizzerDialogController immediately after this class is
     * constructed. This allows this class to get the starter quiz information, and to add the
     * flashcards to once finished creating.
     *
     * @param pBuilder QuizModelBuilder to add completed flashcards to
     */
    @Override
    public void setBuilder(QuizModel.QuizModelBuilder pBuilder) {
        sLog.info("setting quiz model builder");
        mQuizBldr = Objects.requireNonNull(pBuilder);
        mFlashcardTitleText = "Flashcard set: " + mQuizBldr.mName;

        Platform.runLater(() -> mFlashcardTitle.setText(mFlashcardTitleText));
    }

    @FXML
    public void onNew() {
        FlashcardDataLabel newFlashcard = getNewFlashcard();

        sLog.info("Created new flashcard");
        saveFromText();

        mSelectedFlashcard.setSelected(false);
        mSelectedFlashcard = newFlashcard;
        mFlashcardDataLabels.add(mSelectedFlashcard);
        mSelectedFlashcard.setSelected(true);

        populateTextFromFlashcard();
    }

    @FXML
    public void onSubmit() {
        sLog.info("Starting submission process. Saving flashcards and changing the scene...");
        // TODO: implement. This should call a method present in the NewQuizController, or it could
        // take control of the scene and clear this window itself. not sure which is better yet. :/
    }

    // Callback and utility Methods ==================

    private void onFlashcardSelect(MouseEvent pEvent) {
        // This is always bound on a FlashcardDataLabel, so this is safe
        FlashcardDataLabel newFlashcard = (FlashcardDataLabel) (pEvent.getSource());

        // If new flashcard is different than current. mDeleteSelectedFlag allows us to select a
        // card that took the
        // place of the selected card if we deleted the selected card
        if (!(newFlashcard.getFlashcardIndex() == mSelectedFlashcard.getFlashcardIndex()) ||
            mDeleteSelectedFlag) {
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

        // Increment since flashcards are 1 based
        sLog.info(
                "User Deleting Flashcard " + (index + 1) + ": " + mFlashcardDataLabels.get(index));

        // If we delete the current one, clear the text fields
        if (index == mSelectedFlashcard.getFlashcardIndex()) {
            mDeleteSelectedFlag = true;
            Platform.runLater(() -> {
                mFrontTextArea.setText("");
                mBackTextArea.setText("");
            });
        }

        // Discard deleted flashcard
        mFlashcardDataLabels.remove(index);

        // rename all flash cards to avoid jumps in naming, and correct their index variables. We
        // do this since the
        // List auto sizes itself, but the flashcards don't update themselves
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
        // Offset counter by 1 for name (there is no flashcard 0)
        return new FlashcardDataLabel("Flashcard " + (mFlashcardCounter + 1),
                                      mFlashcardCounter++, // then increment
                                      this::onFlashcardSelect, this::onFlashcardDelete);
    }

    /**
     * This method can be called either from the save button or internal logic. This is primarily
     * for utility needs.
     * <p>
     * Saves the current text in each text area to the currently selected flashcard.
     */
    @FXML
    private void saveFromText() {

        // If were empty, and either of the text areas are empty, do nothing.
        // If both text areas have text, create a new flashcard and set it selected
        if (mFlashcardDataLabels.isEmpty()) {
            if (mFrontTextArea.getText().isEmpty() || mBackTextArea.getText().isEmpty()) {
                return;
            } else {
                FlashcardDataLabel newCard = getNewFlashcard();
                newCard.setFrontText(mFrontTextArea.getText());
                newCard.setBackText(mBackTextArea.getText());
                mSelectedFlashcard = newCard;
                mSelectedFlashcard.setSelected(true);
                mFlashcardDataLabels.add(mSelectedFlashcard);
            }
        }
        String front, back;

        front = mFrontTextArea.getText();
        back = mBackTextArea.getText();

        mSelectedFlashcard.setFrontText(front);
        mSelectedFlashcard.setBackText(back);

        // Now that we have at least one entry, unlock submit
        mSubmitBtn.setDisable(false);
    }
}

package com.sovereignx1.jquizzer.ui.mainmenu.newquiz.flashcard;

import com.sovereignx1.jquizzer.ui.fx.SelectableLabel;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * This class represents the data and label of a flashcard in the flashcard list for the flashcard
 * creator
 *
 * @see FlashcardCreatorController
 */
public class FlashcardDataLabel extends SelectableLabel {

    private String mFrontText;

    private String mBackText;

    // This relies on the .label.selected style class in the css file
    private static final String SELECTED_STYLE_CLASS = "selected";

    private int mFlashcardIndex;

    private final Button mDeleteButton = new Button("-");

    /**
     * Creates a Label to act as a flashcard selection. Accepts a callback to allows a controller to
     * listen in on when this label is clicked
     *
     * @param pName Text for this label to display
     * @param pFrontText Text for the front of the flashcard
     * @param pBackText Text for the back of the flashcard
     * @param pClickCallback Callback for a controller class to listen for mouse events with
     */
    public FlashcardDataLabel(String pName, String pFrontText, String pBackText,
                              int pFlashcardIndex,
                              EventHandler<? super MouseEvent> pClickCallback,
                              EventHandler<? super MouseEvent> pBtnCallback) {
        super(pName, pClickCallback);
        mFrontText = pFrontText;
        mBackText = pBackText;
        mFlashcardIndex = pFlashcardIndex;

        // Create a layout container (HBox in this case) to hold both the Label and Button
        HBox hbox = new HBox(10); // 10px spacing between label and button
        hbox.getChildren().add(mDeleteButton);
        hbox.getChildren().add(this);

        // Set the HBox as the graphic for this custom label
        setGraphic(hbox);

        mDeleteButton.setOnMouseClicked(pBtnCallback);
        mDeleteButton.setUserData(mFlashcardIndex);
    }

    public FlashcardDataLabel(String pName, int pFlashcardIndex,
                              EventHandler<? super javafx.scene.input.MouseEvent> pClickCallback,
                              EventHandler<? super javafx.scene.input.MouseEvent> pBtnCallback) {
        // Set default flashcard text
        this(pName, "Front Text", "Back Text", pFlashcardIndex, pClickCallback, pBtnCallback);
    }

    public String getBackText() {
        return mBackText;
    }

    public String getFrontText() {
        return mFrontText;
    }

    public int getFlashcardIndex() {
        return mFlashcardIndex;
    }

    public void setFrontText(String pFrontText) {
        mFrontText = Objects.requireNonNull(pFrontText);
    }

    public void setBackText(String pBackText) {
        mBackText = Objects.requireNonNull(pBackText);
    }

    public void setFlashcardIndex(int pIndex) {
        mFlashcardIndex = pIndex;
        mDeleteButton.setUserData(mFlashcardIndex);
    }

    /**
     * When called, this will either set the Label's text blue or white depending on if it is
     * selected or not
     *
     * @param pSelected Whether to turn the label blue
     */
    public void setSelected(boolean pSelected) {
        if (pSelected) {
            getStyleClass().add(SELECTED_STYLE_CLASS);
        } else {
            getStyleClass().removeAll(SELECTED_STYLE_CLASS);
        }
    }

    @Override
    public String toString() {
        return "FlashcardDataLabel{" +
               "mFrontText='" + mFrontText + '\'' +
               ", mBackText='" + mBackText + '\'' +
               ", mFlashcardIndex=" + mFlashcardIndex +
               '}';
    }
}

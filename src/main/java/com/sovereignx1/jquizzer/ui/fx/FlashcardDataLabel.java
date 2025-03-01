package com.sovereignx1.jquizzer.ui.fx;

import com.sovereignx1.jquizzer.ui.mainmenu.newquiz.FlashcardCreatorController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * This class represents the data and label of a flashcard in the flashcard list for the flashcard creator
 *
 * @see FlashcardCreatorController
 */
public class FlashcardDataLabel extends SelectableLabel {

    private final String mFrontText;

    private final String mBackText;

    /**
     * Creates a Label to act as a flashcard selection. Accepts a callback to allows a controller
     * to listen in on when this label is clicked
     *
     * @param pName Text for this label to display
     * @param pFrontText Text for the front of the flashcard
     * @param pBackText Text for the back of the flashcard
     * @param pControllerCallback Callback for a controller class to listen for mouse events with
     */
    public FlashcardDataLabel(String pName, String pFrontText, String pBackText,
                              EventHandler<? super javafx.scene.input.MouseEvent> pControllerCallback) {
        super(pName, pControllerCallback);
        mFrontText = pFrontText;
        mBackText = pBackText;

        Button deleteButton = new Button("-");

        // Create a layout container (HBox in this case) to hold both the Label and Button
        HBox hbox = new HBox(10); // 10px spacing between label and button
        hbox.getChildren().add(deleteButton);
        hbox.getChildren().add(this);

        // Set the HBox as the graphic for this custom label
        setGraphic(hbox);
    }

    public String getBackText() {
        return mBackText;
    }

    public String getFrontText() {
        return mFrontText;
    }
}

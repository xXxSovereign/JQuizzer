package com.sovereignx1.jquizzer.ui.fx;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SelectableLabel extends Label {

    /**
     * @param pName Text for this label to display
     * @param pControllerCallback Callback for a controller class to listen for mouse events with
     */
    public SelectableLabel(String pName, EventHandler<? super MouseEvent> pControllerCallback) {
        super(pName);
        setOnMouseClicked(pControllerCallback);
    }
}

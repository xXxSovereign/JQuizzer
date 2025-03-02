package com.sovereignx1.jquizzer.ui.fx;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class SelectableLabel extends Label {

    protected String mName;

    /**
     * @param pName Text for this label to display
     * @param pControllerCallback Callback for a controller class to listen for mouse events with
     */
    public SelectableLabel(String pName, EventHandler<? super MouseEvent> pControllerCallback) {
        super(pName);
        mName = pName;
        setOnMouseClicked(pControllerCallback);
    }

    public void setName(String pName) {
        mName = Objects.requireNonNull(pName);
        setText(mName);
    }
}

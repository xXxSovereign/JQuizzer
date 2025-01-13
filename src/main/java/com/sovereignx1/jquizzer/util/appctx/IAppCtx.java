package com.sovereignx1.jquizzer.util.appctx;

/**
 * Marker interface for default methods
 *
 * Objects instantiated as this type can then be cast to the actual app ctx for the custom data
 */
public interface IAppCtx {

    // TODO: Do we need to add the json field variables here? They would be forced to be public though...

    String getDebugLvl();
    boolean getDebugMode();
    String getDebugFile();
}

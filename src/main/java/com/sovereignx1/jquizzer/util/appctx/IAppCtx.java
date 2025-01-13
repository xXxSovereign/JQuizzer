package com.sovereignx1.jquizzer.util.appctx;

/**
 * Marker interface for default methods
 *
 * Objects instantiated as this type can then be cast to the actual app ctx for the custom data
 */
public interface IAppCtx {

    String getDebugLvl();
    boolean getDebugMode();
}

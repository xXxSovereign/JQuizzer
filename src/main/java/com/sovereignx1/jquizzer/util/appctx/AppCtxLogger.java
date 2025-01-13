package com.sovereignx1.jquizzer.util.appctx;

/**
 * This logger is unique to the Application Context classes, as they cannot rely on the Logger implementation.
 * The Logger implementation depends on values present in the app ctx, hence why we have this class.
 * <p>
 * This logger will create its own logging file, but under normal circumstances this file should not be created.
 * This logger will only be used to report errors for debugging purposes.
 */
class AppCtxLogger {

    protected void log(String pStr){
        System.err.println("APPCTX: " + pStr);
    }
}

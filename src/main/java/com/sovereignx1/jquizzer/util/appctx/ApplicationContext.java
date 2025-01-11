package com.sovereignx1.jquizzer.util.appctx;

import com.sovereignx1.jquizzer.util.loader.LoaderImpl;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;

/**
 * This class acts as an interface to extract data from the Application Context. This class uses AppCtxModel
 * to encapsulate data in a way that makes reading the json easy. We just create an object of the model from the file.
 */
public class ApplicationContext {

    // This requires the json to be in the same directory that JQuizzer is running in, I think at least.
    private static final String CONTEXT_FILE = "JQuizzerAppCtx.json";

    private static boolean isInit = false;

    private static final ILogger sLog = LoggerManager.getLogger();
    public static void initialize() {

        if (!isInit){

            String ctxContents = LoaderImpl.readFile(CONTEXT_FILE);
            isInit = true;
        } else {
            sLog.error("Application Context is already initialized, can only be initialized once");
            throw new RuntimeException("Application Context is already initialized");
        }

    }
}

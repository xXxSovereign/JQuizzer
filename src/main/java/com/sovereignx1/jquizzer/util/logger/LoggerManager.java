package com.sovereignx1.jquizzer.util.logger;

import com.sovereignx1.jquizzer.util.ExitManager;
import com.sovereignx1.jquizzer.util.IClosable;

import java.io.IOException;

public class LoggerManager implements IClosable {

    private static LoggerImpl LOGGER = null;

    private static final String LOG_FILE = "JQuizzerLog.txt"; // TODO: Load this from application context

    public static ILogger getLogger() {
        if (LOGGER == null){
            LOGGER = new LoggerImpl();
            LOGGER.setup(LOG_FILE);
            ExitManager.registerAsLast(LoggerManager.class);
        }
        return LOGGER;

    }

    /**
     * Implemented from IClosable but needs to be static; called via reflection from ExitManager
     */
    @SuppressWarnings("unused")
    public static void close() throws IOException {
        LOGGER.info("Closing all LoggerManager resources");
        LoggerImpl.close();
    }
}

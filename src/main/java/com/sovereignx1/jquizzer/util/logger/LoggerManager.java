package com.sovereignx1.jquizzer.util.logger;

import com.sovereignx1.jquizzer.util.ExitManager;
import com.sovereignx1.jquizzer.util.IClosable;

import java.io.IOException;

public class LoggerManager implements IClosable {

    private static LoggerImpl LOGGER = null;


    public static ILogger getLogger() {
        if (LOGGER == null){
            LOGGER = new LoggerImpl();
            LOGGER.setup();
            ExitManager.registerAsLast(LoggerManager.class);
        }
        return LOGGER;

    }

    /**
     * Implemented from IClosable but needs to be static; called via reflection from ExitManager
     * TODO: Maybe find a better way to handle this?
     */
    @SuppressWarnings("unused")
    public static void close() throws IOException {
        LOGGER.info("Closing all LoggerManager resources");
        LoggerImpl.close();
    }
}

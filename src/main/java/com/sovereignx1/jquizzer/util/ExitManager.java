package com.sovereignx1.jquizzer.util;

import com.sovereignx1.jquizzer.logger.LoggerManager;
import com.sovereignx1.jquizzer.logger.ILogger;

import java.util.ArrayList;
import java.util.List;

public final class ExitManager {

    /**
     * Internal list of all services needed to close
     */
    private static final List<Class<? extends IClosable>> sToClose = new ArrayList<>();

    private static Class<? extends IClosable> sLastToClose;

    private static final ILogger sLog = LoggerManager.getLogger();


    public static void exit(){
        sLog.info("Starting exit sequence...");
        closeAll();
    }

    /**
     * Exit with a message
     *
     * @param pMsg message to print on exit
     */
    public static void exit(String pMsg){
        System.out.println(pMsg);
        exit();
    }

    private static void closeAll() {
        for (Class<? extends IClosable> clazz : sToClose){
            try {
                clazz.getMethod("close").invoke(clazz);
            } catch (Exception e) {
                sLog.error("failed to exit from class " + clazz.getName() + "... " + e);
            }
        }

        try {
            if (sLastToClose != null) {
                sLastToClose.getMethod("close").invoke(sLastToClose);
            }
        } catch (Exception e) {
            sLog.error("Failed to close " + sLastToClose.getName() + " - " + e);
        }

    }

    /**
     * Register a class to close when JQuizzer exits
     *
     * @param clazz Service to close
     */
    public static void register(Class<? extends IClosable> clazz){
        sToClose.add(clazz);
    }

    /**
     * Register a service to close after every other class. Normally a logger should close last
     *
     * @param clazz Class to close last
     */
    public static void registerAsLast(Class<? extends IClosable> clazz){
        if (sLastToClose == null){
            sLastToClose = clazz;
        }
    }

}

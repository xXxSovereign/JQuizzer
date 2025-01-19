package com.sovereignx1.jquizzer.util;

import com.sovereignx1.jquizzer.util.logger.LoggerManager;
import com.sovereignx1.jquizzer.util.logger.ILogger;

import java.util.ArrayList;
import java.util.List;

public final class ExitManager {

    /**
     * Internal list of all services needed to close
     */
    private static final List<Class<? extends IClosable>> sToClose = new ArrayList<>();

    private static final List<Thread> sThreadsToClose = new ArrayList<>();

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

        for (Thread thread : sThreadsToClose) {
            // The individual thread can catch the InterruptException and close its resources appropriately
            thread.interrupt();
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
     * Register a class to close when the application exits
     *
     * @param pClazz Service to close
     */
    public static void register(Class<? extends IClosable> pClazz){
        sToClose.add(pClazz);
    }

    /**
     * Register a Thread to interrupt when the application exits
     *
     * @param pThread Service to close
     */
    public static void register(Thread pThread){
        sThreadsToClose.add(pThread);
    }

    /**
     * This method is for internal use only to the XUtils tools. Calling this method will have no effect on closing
     * as this will always be called first by the logger impl
     */
    public static void registerAsLast(Class<? extends IClosable> clazz){
        if (sLastToClose == null){
            sLastToClose = clazz;
        }
    }

}

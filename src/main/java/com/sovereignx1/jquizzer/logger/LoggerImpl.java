package com.sovereignx1.jquizzer.logger;

import com.sovereignx1.jquizzer.util.ExitManager;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

class LoggerImpl implements ILogger {
    private static final boolean DEBUG_ON = false; // TODO: change this before production build

    private static TeeOutputStream sErrAndFileStream;
    private static PrintStream sOut;


    @Override
    public void verbose(String pStr) {
        String debug_info = checkStackTrace();

        sOut.println("VERBOSE: " + pStr + " (" + debug_info +")");
    }

    @Override
    public void debug(String pStr) {
        String debug_info = checkStackTrace();

        sOut.println("DEBUG: " + pStr + " (" + debug_info +")");
    }

    @Override
    public void info(String pStr) {
        String debug_info = checkStackTrace();


        sOut.println("INFO: " + pStr + " (" + debug_info +")");
    }

    @Override
    public void warn(String pStr) {
        String debug_info = checkStackTrace();

        sOut.println("WARN: " + pStr + " (" + debug_info +")");
    }

    @Override
    public void error(String pStr) {
        String debug_info = checkStackTrace();

        sOut.println("ERROR: " + pStr + " (" + debug_info +")");
    }

    @Override
    public void setup(String pPath) {
        try {
            File logFile = new File(pPath);

            sErrAndFileStream = new TeeOutputStream(System.err, Files.newOutputStream(logFile.toPath()));
            sOut = new PrintStream(sErrAndFileStream);
        } catch (Exception e) {
            ExitManager.exit("ERROR: FAILED TO INITIALIZE LOG FILE - " + e);
        }

    }

    public static void close() throws IOException {
        sOut.println("INFO: Closing Print Stream and the Error/File Stream");
        sOut.println("\n\n===============================END OF JQUIZZER RUNTIME===============================\n\n");
        sOut.flush();
        sErrAndFileStream.flush();
        sOut.close();
        sErrAndFileStream.close();
    }

    private String checkStackTrace() {
        // Only show the class that is logging if debug is on
        if (DEBUG_ON) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            // The element at index 3 is the caller of the log method, since this method is also called
            StackTraceElement caller = stackTrace[3];
            return caller.getClassName();
        } return "";
    }

}

package com.sovereignx1.jquizzer.util.logger;

import com.sovereignx1.jquizzer.JQuizzerAppCtx;
import com.sovereignx1.jquizzer.util.ExitManager;
import com.sovereignx1.jquizzer.util.appctx.ApplicationContext;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

class LoggerImpl implements ILogger {

    // This value is loaded from app ctx
    private static boolean DEBUG_ON;

    // This value is loaded from app ctx
    private static ELogLevel LOG_LEVEL;

    private static TeeOutputStream sErrAndFileStream;
    private static PrintStream sOut;


    @Override
    public void verbose(String pFormat, String... pValues) {
        log(ELogLevel.VERBOSE, pFormat, pValues);
    }

    @Override
    public void debug(String pFormat, String ... pValues) {
        log(ELogLevel.DEBUG, pFormat, pValues);
    }

    @Override
    public void info(String pFormat, String ... pValues) {
        log(ELogLevel.INFO, pFormat, pValues);
    }

    @Override
    public void warn(String pFormat, String ... pValues) {
        log(ELogLevel.WARN, pFormat, pValues);
    }

    @Override
    public void error(String pFormat, String ... pValues) {
        log(ELogLevel.ERROR, pFormat, pValues);
    }

    @Override
    public void setup(String pPath) {
        try {
            File logFile = new File(pPath);

            sErrAndFileStream = new TeeOutputStream(System.err, Files.newOutputStream(logFile.toPath()));
            sOut = new PrintStream(sErrAndFileStream);

            JQuizzerAppCtx ctx = ApplicationContext.getAppCtx();
            DEBUG_ON = ctx.extra_debug_info;
            LOG_LEVEL = ELogLevel.valueOf(ctx.debug_level.toUpperCase());


        } catch (Exception e) {
            ExitManager.exit("ERROR: FAILED TO INITIALIZE LOG FILE - " + e);
        }
    }

    @Override
    public void setDebug(boolean pDebugEnabled) {
        DEBUG_ON = pDebugEnabled;
    }

    private void log(ELogLevel pLvl, String pFormat, String ... pValues){

        if (pLvl.compareTo(LOG_LEVEL) >= 0) {
            String debug_info = checkStackTrace();
            String formattedStr = braceReplace(pFormat, pValues);

            // LogLvl: log_info (debug info, class and caller class
            sOut.println(pLvl + ": " + formattedStr + (DEBUG_ON ? " (" + debug_info + ")" : ""));
        }
    }

    /**
     * Will substitute n number of braces with n number of vaules. The number of values MUST match the number of braces
     * <p>
     * i.e. braceReplace("Test {} test2 {}", 6, "LOL") = "Test 6 test2 LOL"
     * <p>
     * If there is a mismatch in the number of braces and values, an error will be thrown.
     * However, if there are no braces present, the values will be added to the end of the format string, separated
     * by spaces
     *
     * @param pFormat Format string with the brace placeholders
     * @param pValues n number of values to replace, must match the number of braces
     *
     * @return The format with the filled in values from pValues
     * <p>
     *
     *  "0123{}67{}89".length = 12
     *  "01236789".length = 8
     *
     *  12 - 8 = 4 / 2 = 2 curly brace pair
     */
    private String braceReplace(String pFormat, String ... pValues) {
        // Prepare result to be formatted. If no values are placed then the placeholders will not be filled
        String res = pFormat;
        int countVals = pValues.length;

        // Count how many {} are present by difference in characters when they are removed
        int substituteCount = (pFormat.length() - pFormat.replace("{}", "").length()) / "{}".length();

        // If no placeholders, add everything to the end
        if (substituteCount == 0 && countVals > 0) {
            for (String str : pValues) {
                res += " " + str;
            }
            return res;
        }

        if (countVals >= substituteCount) {
            boolean extraVals = countVals > substituteCount;
            int mainIter;
            for (mainIter = 0; mainIter < substituteCount; mainIter++){
                res = res.replaceFirst("\\{\\}", pValues[mainIter]);
            }
            if (extraVals) {
                for (int j = mainIter; j < countVals; j++){
                    res += " " + pValues[j];
                }
            }
        }
        return res;
    }

    public static void close() throws IOException {
        // Manually log as info to ensure this is not eaten by a log level limiter
        sOut.println("INFO: Closing Logger Print Stream and Error/File Stream");
        sOut.println("\n===============================END OF JQUIZZER RUNTIME===============================\n");
        sOut.flush();
        sErrAndFileStream.flush();
        sOut.close();
        sErrAndFileStream.close();
    }

    private String checkStackTrace() {
        // Only show the class that is logging if debug is on
        if (DEBUG_ON) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            // The element at index 4 is the caller of the log method, since this method is also called
            // This is a magic number that is related to how many method calls are chained in this class.
            // (i.e. logging pipeline)

            StackTraceElement caller = stackTrace[4];
            return caller.getClassName();
        } return "";
    }



}

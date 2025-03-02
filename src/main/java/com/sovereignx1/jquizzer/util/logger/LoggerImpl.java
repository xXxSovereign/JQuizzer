package com.sovereignx1.jquizzer.util.logger;

import com.sovereignx1.jquizzer.util.ExitManager;
import com.sovereignx1.jquizzer.util.appctx.ApplicationContext;
import com.sovereignx1.jquizzer.util.appctx.IAppCtx;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.Instant;

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
    public void debug(String pFormat, String... pValues) {
        log(ELogLevel.DEBUG, pFormat, pValues);
    }

    @Override
    public void info(String pFormat, String... pValues) {
        log(ELogLevel.INFO, pFormat, pValues);
    }

    @Override
    public void warn(String pFormat, String... pValues) {
        log(ELogLevel.WARN, pFormat, pValues);
    }

    @Override
    public void error(String pFormat, String... pValues) {
        log(ELogLevel.ERROR, pFormat, pValues);
    }

    public void error(String pStr, Exception pEx) {
        log(ELogLevel.ERROR, pStr, pEx.getMessage());
        pEx.printStackTrace(sOut);
    }

    @Override
    public void setup() {
        try {
            IAppCtx ctx = ApplicationContext.getAppCtx();
            DEBUG_ON = ctx.getDebugMode();
            LOG_LEVEL = ELogLevel.valueOf(ctx.getDebugLvl().toUpperCase());
            File logFile = new File(ctx.getDebugFile());

            sErrAndFileStream = new TeeOutputStream(System.err, Files.newOutputStream(logFile.toPath()));
            sOut = new PrintStream(sErrAndFileStream);

        } catch (Exception e) {
            ExitManager.exit("ERROR: FAILED TO INITIALIZE LOG FILE - " + e);
        }
    }

    private void log(ELogLevel pLvl, String pFormat, String... pValues) {

        if (pLvl.compareTo(LOG_LEVEL) >= 0) {
            String debug_info = checkStackTrace();
            String formattedStr = braceReplace(pFormat, pValues);

            // LogLvl: log_info (debug info, class and caller class
            sOut.println(Timestamp.from(Instant.now()) + " " + pLvl + ": " + formattedStr +
                         (DEBUG_ON ? " (" + debug_info + ")" : ""));
        }
    }

    /**
     * Will substitute n number of braces with n number of vaules. The number of values MUST match the number of braces
     * <p>
     * i.e. braceReplace("Test {} test2 {}", 6, "LOL") = "Test 6 test2 LOL"
     * <p>
     * This will replace any brace pairs with a value if supplied. If braces are present without enough values, they
     * will remain a brace. If there are more values than braces, the extra are added onto the end of the string
     *
     * @param pFormat Format string with the brace placeholders
     * @param pValues n number of values to replace
     * @return The format with the filled in values from pValues
     * <p>
     * <p>
     * "0123{}67{}89".length = 12 "01236789".length = 8
     * <p>
     * 12 - 8 = 4 / 2 = 2 curly brace pair
     */
    private String braceReplace(String pFormat, String... pValues) {
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

        // If we have more values than braces, fill braces and put values at the end
        if (countVals >= substituteCount) {
            boolean extraVals = countVals > substituteCount;
            int mainIter;
            for (mainIter = 0; mainIter < substituteCount; mainIter++) {
                res = res.replaceFirst("\\{\\}", pValues[mainIter]);
            }
            if (extraVals) {
                for (int j = mainIter; j < countVals; j++) {
                    res += " " + pValues[j];
                }
            }
            // At this point if we have values, we have fewer values that braces so fill what we can and leave the rest
        } else if (countVals > 0) {
            for (String pValue : pValues) {
                res = res.replaceFirst("\\{\\}", pValue);
            }
        }
        return res;
    }

    public static void close() throws IOException {
        // Manually log as info to ensure this is not eaten by a log level limiter
        sOut.println(Timestamp.from(Instant.now()) + " " + "INFO: Closing Logger Print Stream and Error/File Stream");
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
        }
        return "";
    }


}

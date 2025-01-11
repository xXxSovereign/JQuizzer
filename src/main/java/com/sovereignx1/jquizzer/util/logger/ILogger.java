package com.sovereignx1.jquizzer.util.logger;

import java.nio.file.Path;

public interface ILogger {

    /**
     * @param pFormat String with curly braces denoting placeholders for values
     *                Number of curly braces must match number of values passed
     */
    void verbose(String pFormat, String ... pValues);

    /**
     * @param pFormat String with curly braces denoting placeholders for values
     *                Number of curly braces must match number of values passed
     */
    void debug(String pFormat, String ... pValues);

    /**
     * @param pFormat String with curly braces denoting placeholders for values
     *                Number of curly braces must match number of values passed
     */
    void info(String pFormat, String ... pValues);

    /**
     * @param pFormat String with curly braces denoting placeholders for values
     *                Number of curly braces must match number of values passed
     */
    void warn(String pFormat, String ... pValues);

    /**
     * @param pFormat String with curly braces denoting placeholders for values
     *                Number of curly braces must match number of values passed
     */
    void error(String pFormat, String ... pValues);

    void setup(String pPath);

    /**
     * Sets the debug flag in the underlying logger. This will add extra class details to the logging output
     *
     * @param pDebugEnabled whether to enable Debug
     */
    void setDebug(boolean pDebugEnabled);
}

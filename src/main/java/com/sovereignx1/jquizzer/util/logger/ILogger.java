package com.sovereignx1.jquizzer.util.logger;

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

    /**
     * Version of error logging but just for a message and error info
     * @param pStr Message to log
     * @param pEx Exception to get info from
     */
    void error(String pStr, Exception pEx);

    void setup();

}

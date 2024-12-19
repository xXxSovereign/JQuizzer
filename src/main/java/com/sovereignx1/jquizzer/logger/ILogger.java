package com.sovereignx1.jquizzer.logger;

import java.nio.file.Path;

public interface ILogger {

    /**
     * @param pStr String to log at verbose level
     */
    void verbose(String pStr);

    /**
     * @param pStr String to log at debug level
     */
    void debug(String pStr);

    /**
     * @param pStr String to log at info level
     */
    void info(String pStr);

    /**
     * @param pStr String to log at warning level
     */
    void warn(String pStr);

    /**
     * @param pStr String to log at error level
     */
    void error(String pStr);

    void setup(String pPath);
}

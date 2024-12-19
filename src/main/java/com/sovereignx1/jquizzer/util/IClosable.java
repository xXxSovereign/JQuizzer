package com.sovereignx1.jquizzer.util;

/**
 * Marks a service as needing resource closing.
 * I.e. a logger will need to close the file stream
 *
 */
public interface IClosable {
    /*
     * An implementing class MUST implement this method.
     * ExitManager depends on this method, or resources will not be closed
     *
     * Closes all resources in use
     *
     * public static void close();
     *
     */

}

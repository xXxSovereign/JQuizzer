package com.sovereignx1.jquizzer.util.loader;

import com.google.gson.Gson;
import com.sovereignx1.jquizzer.util.logger.ILogger;
import com.sovereignx1.jquizzer.util.logger.LoggerManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class LoaderImpl {

    private static final ILogger sLog = LoggerManager.getLogger();

    public void load(){
        Gson gson = new Gson();

    }

    /**
     * Generically read a file and return its contents
     *
     * @param filePath path to file to read
     * @return file contents as a String
     */
    public static String readFile(String filePath) {
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            sLog.error("Encountered an error reading file path: {} ", filePath, e.toString());
        }
        return result;
    }
}

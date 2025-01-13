package com.sovereignx1.jquizzer.util.appctx;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class acts as an interface to extract data from the Application Context. This class uses the user provided class
 * (to initialize()) to create an object that encapsulates the values stored in the AppCtx JSON
 * <p>
 *
 * This class should always be initialized before any other service. It is recommended to call initialize() in a static
 * block in the class with the main method
 */
public class ApplicationContext {

    private static final AppCtxLogger sLog = new AppCtxLogger();

    private static boolean isInit = false;

    private static Class<?> mAppCtxClass;

    private static Object mAppCtxObj;

    public static <T> void initialize(Class<T> pAppCtxClass, String pAppCtxFile) {

        if (!isInit) {
            mAppCtxClass = pAppCtxClass;
            String ctxContents = "";
            try {
                // This requires the json to be in the same directory that this app is running in, I think at least.
                ctxContents = new String(Files.readAllBytes(Paths.get(pAppCtxFile)));
            } catch (IOException e) {
                sLog.log("Encountered an error reading file path: " + pAppCtxFile + " : " + e);
            }

            Gson gson = new Gson();

            mAppCtxObj = gson.fromJson(ctxContents, pAppCtxClass);

            isInit = true;
        } else {
            sLog.log("Application Context is already initialized, can only be initialized once");
            throw new RuntimeException("Application Context is already initialized");
        }

    }

    /**
     * Returns an object of the application context created from the AppCtx JSON
     * <p>
     * When this method is called, its return value must be assigned to a variable of the App Context Model Class passed
     * to {@link ApplicationContext#initialize(Class, String)}
     *
     * @return An object of the App Context Model
     * @param <T> Application Model Class
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAppCtx() {
        return (T) mAppCtxClass.cast(mAppCtxObj);
    }
}

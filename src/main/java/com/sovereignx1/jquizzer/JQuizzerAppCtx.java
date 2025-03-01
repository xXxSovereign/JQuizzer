package com.sovereignx1.jquizzer;

import com.sovereignx1.jquizzer.util.appctx.ApplicationContext;
import com.sovereignx1.jquizzer.util.appctx.IAppCtx;

public class JQuizzerAppCtx implements IAppCtx {

    // These names must match the fields in JQuizzerAppCtx.json, or the program will not load
    private String debug_level;
    private boolean extra_debug_info;
    private String debug_file;
    private String test_extra_val;

    /**
     * Do not allow instantiation. This class will be instantiated through GSON by loading a JSON in
     * {@link ApplicationContext}
     */
    private JQuizzerAppCtx(){
        // no impl, constructed through Gson via reflection
        // see com.sovereignx1.jquizzer.util.appctx.ApplicationContext
    }

    @Override
    public String getDebugLvl() {
        return debug_level;
    }

    @Override
    public boolean getDebugMode() {
        return extra_debug_info;
    }

    @Override
    public String getDebugFile() {
        return debug_file;
    }

    public String getExtraVal(){
        return test_extra_val;
    }
}

package com.sovereignx1.jquizzer;

import com.sovereignx1.jquizzer.util.appctx.ApplicationContext;

public class JQuizzerAppCtx {

    /**
     * Do not allow instantiation. This class will be instantiated through GSON by loading a JSON in
     * {@link ApplicationContext}
     */
    private JQuizzerAppCtx(){

    }
    public String debug_level;
    public boolean extra_debug_info;
}

package com.sovereignx1.jquizzer.util.logger;

enum ELogLevel {

    VERBOSE("VERBOSE", 0),
    DEBUG("DEBUG", 1),
    INFO("INFO", 2),
    WARN("WARN", 3),

    // Using the text ERROR causes intellij to capture them in a separate log. Console output will be normal, but
    // the ide will show an extra capture for errors using this log level. If you change the text to something
    // other than ERROR, it no longer works
    ERROR("ERROR", 4);

    private final String mStr;
    private final int mLvl;
    ELogLevel(String pStr, int mLvl){
        mStr = pStr;
        this.mLvl = mLvl;
    }

    @Override
    public String toString(){
        return mStr;
    }

}

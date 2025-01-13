package com.sovereignx1.jquizzer.util.logger;

enum ELogLevel {

    VERBOSE("VERBOSE"),
    DEBUG("DEBUG"),
    INFO("INFO"),
    WARN("WARN"),

    // Using the text ERROR causes intellij to capture them in a separate log. Console output will be normal, but
    // the ide will show an extra capture for errors using this log level. If you change the text to something
    // other than ERROR, it no longer works
    ERROR("ERROR");

    private final String mStr;

    ELogLevel(String pStr){
        mStr = pStr;
    }

    @Override
    public String toString(){
        return mStr;
    }

}

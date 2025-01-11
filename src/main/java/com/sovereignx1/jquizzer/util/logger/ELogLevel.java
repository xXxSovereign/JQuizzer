package com.sovereignx1.jquizzer.util.logger;

enum ELogLevel {

    VERBOSE("VERBOSE"),
    DEBUG("DEBUG"),
    INFO("INFO"),
    WARN("WARN"),
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

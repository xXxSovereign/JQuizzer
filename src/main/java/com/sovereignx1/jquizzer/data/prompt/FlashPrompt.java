package com.sovereignx1.jquizzer.data.prompt;

public class FlashPrompt implements IQuizzerPrompt {

    private final String mFront;

    private final String mBack;

    private boolean mFrontFocused = true;

    public FlashPrompt(String front, String back) {
        mFront = front;
        mBack = back;
    }


    @Override
    public String toString() {
        return "FlashPrompt{" +
               "mFront='" + mFront + '\'' +
               ", mBack='" + mBack + '\'' +
               '}';
    }

    @Override
    public String getContent() {
        return mFrontFocused ? mFront : mBack;
    }

    @Override
    public void doAction(int pUnused) {
        mFrontFocused = !mFrontFocused;
    }
}

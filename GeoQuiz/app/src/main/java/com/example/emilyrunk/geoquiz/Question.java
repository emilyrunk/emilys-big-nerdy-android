package com.example.emilyrunk.geoquiz;

/**
 * Created by emilyrunk on 5/11/17.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private int mQuestionStatus = 0;
    private boolean mHasBeenAnswered = false;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    //0 = unanswered
    //1 = correct
    //2 = incorrect
    public void setQuestionStatus(int status) {
        mQuestionStatus = status;
    }

    public int getQuestionStatus() {
        return mQuestionStatus;
    }

    public void setHasBeenAnswered(boolean b) {
        mHasBeenAnswered = b;
    }

    public boolean getHasBeenAnswered() {
        return mHasBeenAnswered;
    }


    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}

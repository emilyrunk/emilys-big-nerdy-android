package com.example.emilyrunk.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String QUESTIONS_STATUS = "Questions Status";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private ArrayList<Integer> mQuestionsStatus;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true),
    };



    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        mQuestionsStatus = new ArrayList<>();

        for (int i = 0; i < mQuestionBank.length; i++) {
            mQuestionsStatus.add(mQuestionBank[i].getQuestionStatus());
        }


        //if there is a previously savedInstanceState, then set mCurrentIndex to saved value
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mQuestionsStatus = savedInstanceState.getIntegerArrayList(QUESTIONS_STATUS);
        }

        if (mQuestionBank[mCurrentIndex].getHasBeenAnswered() == true) {
            //Set Visibility of Buttons to invisible once answered
            mFalseButton.setVisibility(View.INVISIBLE);
            mTrueButton.setVisibility(View.INVISIBLE);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                checkAnswer(true);
                //Set Visibility of Buttons to invisible once answered
                mFalseButton.setVisibility(View.INVISIBLE);
                mTrueButton.setVisibility(View.INVISIBLE);
            }

        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                checkAnswer(false);
                //Set Visibility of Buttons to invisible once answered
                mFalseButton.setVisibility(View.INVISIBLE);
                mTrueButton.setVisibility(View.INVISIBLE);
            }

        });


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mFalseButton.setVisibility(View.INVISIBLE);
                mTrueButton.setVisibility(View.INVISIBLE);
                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    //Store the currentindex into the saved index state
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putIntegerArrayList(QUESTIONS_STATUS, mQuestionsStatus);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {

        mQuestionsStatus.clear();
        for (int i = 0; i < mQuestionBank.length; i++) {
            mQuestionsStatus.add(mQuestionBank[i].getQuestionStatus());
        }
        Log.d(TAG, mQuestionsStatus.toString());

        if (mQuestionBank[mCurrentIndex].getHasBeenAnswered() == true) {
            //Set Visibility of Buttons to invisible once answered
            mFalseButton.setVisibility(View.INVISIBLE);
            mTrueButton.setVisibility(View.INVISIBLE);
        } else {
            mFalseButton.setVisibility(View.VISIBLE);
            mTrueButton.setVisibility(View.VISIBLE);
        }

        if (mQuestionsStatus.contains(0)) {
            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
        } else {
            double correct = 0;
            double incorrect = 0;
            for (int i = 0; i<mQuestionsStatus.size(); i++ ) {
                if (mQuestionsStatus.get(i) == 1) {
                    correct += 1;
                }
                if (mQuestionsStatus.get(i) == 2) {
                    incorrect += 1;
                }
            }
            double numberOfQuestions = mQuestionBank.length;
            double finalScore = (correct/numberOfQuestions) * 100;
            Log.d(TAG, "correct: " + correct +"\nnumberOfQuestions: " + numberOfQuestions + "\nfinalscore: " + finalScore);

            Toast.makeText(this, "You got a score of: " + finalScore +"%!",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        mQuestionBank[mCurrentIndex].setHasBeenAnswered(true);

        int messageResId = 0;

        //0 = unanswered
        //1 = correct
        //2 = incorrect
        if (userPressedTrue == answerIsTrue) {
            mQuestionBank[mCurrentIndex].setQuestionStatus(1);
            messageResId = R.string.correct_toast;
        } else {
            mQuestionBank[mCurrentIndex].setQuestionStatus(2);
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}

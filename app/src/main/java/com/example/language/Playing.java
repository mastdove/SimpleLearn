package com.example.language;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.language.Common.Common;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class Playing extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL = 100000;
    final static long TIMEOUT = 100000;
    int progressValue = 0;
    CountDownTimer mCountDown;
    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer, wrongAnswer;

    ProgressBar progressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD, btnE;
    TextView txtScore, txtQuestionNum, question_text;
    TextToSpeech textToSpeech;
    ImageButton b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        txtScore = findViewById(R.id.txtScore);
        txtQuestionNum = findViewById(R.id.txtTotalQuestion);
        question_text = findViewById(R.id.question_text);
        question_image = findViewById(R.id.question_image);

        progressBar = findViewById(R.id.progressBar);

        btnA = findViewById(R.id.btnAnswerA);
        btnB = findViewById(R.id.btnAnswerB);
        btnC = findViewById(R.id.btnAnswerC);
        btnD = findViewById(R.id.btnAnswerD);
        btnE = findViewById(R.id.btnAnswerE);
        b1 = findViewById(R.id.btnPlaySound);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        btnE.setOnClickListener(this);
        b1.setOnClickListener(this);


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ToSpeak = question_text.getText().toString();
                Toast.makeText(getApplicationContext(),ToSpeak,Toast.LENGTH_SHORT).show();
                textToSpeech.speak(ToSpeak,TextToSpeech.QUEUE_FLUSH,null);}

        });
}


    @Override
    public void onClick(View v) {
        mCountDown.cancel();
//        if (v == b1){
//        String ToSpeak = question_text.getText().toString();
//        Toast.makeText(getApplicationContext(),ToSpeak,Toast.LENGTH_SHORT).show();
//        textToSpeech.speak(ToSpeak,TextToSpeech.QUEUE_FLUSH,null);}
        // if still has question in list
        if(index < totalQuestion) {
            Button clickedButton = (Button) v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())) {
                //Choose correct answer
                score++;
                correctAnswer++;
            } else {
                wrongAnswer++;
            }
//            else {
//                Intent intent = new Intent(this, Done.class);
//                Bundle dataSend = new Bundle();
//                dataSend.putInt("SCORE", score);
//                dataSend.putInt("TOTAL", totalQuestion);
//                dataSend.putInt("CORRECT", correctAnswer);
//                intent.putExtras(dataSend);
//                startActivity(intent);
//                finish();
//            }
            showQuestion(++index); // next question
            txtScore.setText(String.format("%d", score));
        }
    }


    private void showQuestion(int index) {
        if(index < totalQuestion) {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;

            if(Common.questionList.get(index).getIsImageQuestion().equals("true")) {
                //If image
                Picasso.with(getBaseContext())
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            } else {
                question_text.setText(Common.questionList.get(index).getQuestion());
                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);
            }
            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());
            btnE.setText(Common.questionList.get(index).getAnswerE());

            mCountDown.start(); //Start timer
        } else {
            //If it is final question
            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            dataSend.putInt("WRONG", wrongAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalQuestion = Common.questionList.size();
        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }
}

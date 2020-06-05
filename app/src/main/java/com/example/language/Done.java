package com.example.language;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.language.Common.Common;
import com.example.language.Model.QuestionScore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Done extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtCorrectAnswers, txtWrongAnswers;

    FirebaseDatabase database;
    DatabaseReference question_score;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");


        txtCorrectAnswers = findViewById(R.id.txtCorrectAnswers);
        txtWrongAnswers = findViewById(R.id.txtWrongAnswers);
        btnTryAgain = findViewById(R.id.btnTryAgain);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Done.this, Home.class);
                startActivity(intent);

                finish();
            }
        });

//        bottomNavigationView = findViewById(R.id.navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//                switch (item.getItemId()){
//                    case R.id.action_category:
//                        setContentView(R.layout.category_layout);
//                        break;
//                    case R.id.action_ranking:
//                        setContentView(R.layout.activity_score_detail);
//                        break;
//                }
//
//                return true;
//            }
//        });

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");
            int wrongAnswer = extra.getInt("WRONG");
            txtCorrectAnswers.setText(String.format("CORRECT ANSWERS : %d", correctAnswer));
            txtWrongAnswers.setText(String.format("WRONG ANSWERS : %d", wrongAnswer));


            //Upload point to DB
            question_score.child(String.format("%s_%s", Common.currentUser.getUserName(), Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getUserName(),
                            Common.categoryId),
                            Common.currentUser.getUserName(),
                            String.valueOf(score),
                            Common.categoryId,
                            Common.categoryName));
        }

    }
}

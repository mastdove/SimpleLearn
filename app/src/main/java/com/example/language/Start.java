package com.example.language;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.language.Common.Common;
import com.example.language.Model.Question;
import com.example.language.Model.QuestionScore;
import com.example.language.Model.TheoryText;
import com.example.language.ViewHolder.ScoreDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start extends AppCompatActivity {
    Button btnPlay, btnRead, btnVoc;
    FirebaseDatabase database;
    DatabaseReference questions, theory,vocabulary, abstractWords,phrasal;
    DatabaseReference question_score;
    ImageView id,grammar, abWords, phWords;
    TextView Score;
    FirebaseRecyclerAdapter<QuestionScore, ScoreDetailViewHolder> adapter;
    BottomNavigationView bottomNavigationView;
    String SubId;



public void ShowNew(View view){
    //loadVocabulary(Common.categoryId);
    Intent intent = new Intent(Start.this, Playing.class);
    loadVocabulary(Common.categoryId);
    startActivity(intent);
    finish();
    //loadVocabulary(Common.categoryId);

};
    public void ShowNew2(View view){
        Intent intent = new Intent(Start.this, Playing.class);
        loadQuestions(Common.categoryId);
        startActivity(intent);
        finish();

    };
    public void ShowNew3(View view){
        Intent intent = new Intent(Start.this, Playing.class);
        loadPhrasal(Common.categoryId);
        startActivity(intent);
        finish();

    };
    public void ShowNew4(View view){
        Intent intent = new Intent(Start.this, Playing.class);
        loadAbstract(Common.categoryId);
        startActivity(intent);
        finish();

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
//        loadVocabulary(Common.categoryId);
//        loadQuestions(Common.categoryId);
        database = FirebaseDatabase.getInstance();

        vocabulary = database.getReference("Vocabulary");
        phrasal = database.getReference("Phrasal");
        abstractWords = database.getReference("Abstract");
        questions = database.getReference("Questions");

      id = findViewById(R.id.imageView1);
        loadVocabulary(Common.categoryId);
        loadQuestions(Common.categoryId);
      grammar= findViewById(R.id.imageView2);

        abWords= findViewById(R.id.imageView4);
        loadAbstract(Common.categoryId);
        phWords= findViewById(R.id.imageView3);
        loadPhrasal(Common.categoryId);
//        loadVocabulary(Common.categoryId);
//        loadQuestions(Common.categoryId);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.action_category:
                        setContentView(R.layout.category_layout);
                        break;
                    case R.id.action_ranking:
                        setContentView(R.layout.activity_score_detail);
                        break;
                }

                return true;
            }
        });


        //questions = database.getReference("Questions");
//        btnPlay = findViewById(R.id.btnPlay);
//        loadQuestions(Common.categoryId);
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Start.this, Playing.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        question_score = database.getReference("Question_Score");
        theory = database.getReference("Theory");
        loadTheoryText(Common.categoryId);
        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Theory.class);
                startActivity(intent);
                finish();
            }
        });


//        vocabulary = database.getReference("Vocabulary");
//        btnVoc= findViewById(R.id.btnVoc);
//        loadVocabulary(Common.categoryId);
//        btnVoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Start.this, Playing.class);
//                startActivity(intent);
//                finish();
//            }
//        });



    }



    private  void loadQuestions(String categoryId) {

        //First clear List if have old question
        if (Common.questionList.size() > 0) {
            Common.questionList.clear();
        }

        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Question ques = postSnapshot.getValue(Question.class);
                            Common.questionList.add(ques);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        //Random list
        Collections.shuffle(Common.questionList);
    }

    private void loadTheoryText(String categoryId) {
        theory.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            TheoryText theory = postSnapshot.getValue(TheoryText.class);
                            Common.theoryText = theory.getText();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    private  void loadVocabulary( String categoryId) {
        Common.questionList.clear();
//        //First clear List if have old question
//        if (Common.questionList.size() > 0) {
//            Common.questionList.clear();
//        }

        vocabulary.orderByChild("CatId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Question ques1 = postSnapshot.getValue(Question.class);
                            Common.questionList.add(ques1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



};
    private  void loadPhrasal( String categoryId) {
        Common.questionList.clear();
//        //First clear List if have old question
//        if (Common.questionList.size() > 0) {
//            Common.questionList.clear();
//        }

        phrasal.orderByChild("CatId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Question ques1 = postSnapshot.getValue(Question.class);
                            Common.questionList.add(ques1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    };
    private  void loadAbstract( String categoryId) {
        Common.questionList.clear();
//        //First clear List if have old question
//        if (Common.questionList.size() > 0) {
//            Common.questionList.clear();
//        }

        abstractWords.orderByChild("CatId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Question ques1 = postSnapshot.getValue(Question.class);
                            Common.questionList.add(ques1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



    }

    }





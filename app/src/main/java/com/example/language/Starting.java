package com.example.language;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.language.Common.Common;
import com.example.language.Model.Question;
import com.example.language.Model.TheoryText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Starting extends AppCompatActivity {

    Button btnPlay, btnRead , voc;

    FirebaseDatabase database;
    DatabaseReference questions, theory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");



        loadQuestions(Common.categoryId, "1");
        btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starting.this, Playing.class);
                startActivity(intent);
                finish();
            }
        });

        theory = database.getReference("Theory");
        loadTheoryText(Common.categoryId);
        btnRead = findViewById(R.id.btnRead);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starting.this, Theory.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestions(String categoryId, String sub) {

        //First clear List if have old question
        if (Common.questionList.size() > 0) {
            Common.questionList.clear();
        }
        questions.orderByChild("SubCategoryId").equalTo(sub)
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
}

package com.example.language;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.language.Common.Common;

public class Theory extends AppCompatActivity {

    TextView theory_text;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        theory_text = findViewById(R.id.theory_text);
        btnOk = findViewById(R.id.btnOk);
        theory_text.setText(Common.theoryText);
        theory_text.setVisibility(View.VISIBLE);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Theory.this, Start.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

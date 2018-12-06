package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ConstraintLayout cl_intro = findViewById(R.id.cl_intro);

        cl_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToLogin();
            }
        });
    }

    private void proceedToLogin() {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}

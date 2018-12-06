package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class Counter1 extends Counter{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter1);

        lbl_status = findViewById(R.id.lbl_status);
        lbl_last = findViewById(R.id.lbl_last);

        btn_result = findViewById(R.id.btn_result);
        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endCount();
            }
        });

        setPatientNameAndDiagnosis();

        applyOnClickListenersToCard();

        adjustImageResources();

        adjustCardLabels();

        implementFABClicks();
    }
}

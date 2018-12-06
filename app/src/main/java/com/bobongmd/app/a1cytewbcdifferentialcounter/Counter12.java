package com.bobongmd.app.a1cytewbcdifferentialcounter;


import android.os.Bundle;
import android.view.View;

public class Counter12 extends Counter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter12);

        lbl_status = findViewById(R.id.lbl_status);
        lbl_last = findViewById(R.id.lbl_last);

        btn_result = findViewById(R.id.btn_result);
        btn_result.setVisibility(View.GONE);
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

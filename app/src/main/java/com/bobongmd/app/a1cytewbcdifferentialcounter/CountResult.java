package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class CountResult extends AppCompatActivity {

    private Session session;
    private Patient patient;
    private DifferntialCount differntialCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_result);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        session = new Session(this);
        patient = session.getPatient();
        differntialCount = session.getDifferentialCount();

        ArrayList<ResultItem> results = new ArrayList<ResultItem>();

        for(DifferentialCountResult r : differntialCount.getCellsForCounting()){
            int x = new Integer(differntialCount.getCellsToBeCounted());
            float y = new Float(r.getCountValue());
            float percentage = y/x;
            float percentage2 = percentage*100;
            float absolute = percentage*new Float(patient.getWbc());
            results.add(new ResultItem(
                    r.getBloodCell().getName(),
                    r.getCountValue(),
                    Math.round(percentage2),
                    Math.round(absolute)
            ));
        }

        ResultRowAdapter resultRowAdapter = new ResultRowAdapter(this,results);
        ListView lv_result = findViewById(R.id.lv_result);
        lv_result.setAdapter(resultRowAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}

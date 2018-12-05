package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class Counter8 extends Counter implements View.OnClickListener {

    private Session session;
    private ApiCallManager api;
    private Patient patient;
    private DifferntialCount differntialCount;
    private int taps = 0;
    private TextView lbl_patient_name;
    private TextView lbl_diagnosis;
    private TextView lbl_status;
    private TextView lbl_last;
    private Button btn_result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter8);

        session = new Session(this);
        api = new ApiCallManager(this);
        patient = session.getPatient();
        differntialCount = session.getDifferentialCount();

        lbl_patient_name = findViewById(R.id.lbl_patient_id);
        lbl_diagnosis = findViewById(R.id.lbl_diagnosis);
        lbl_status = findViewById(R.id.lbl_status);
        lbl_last = findViewById(R.id.lbl_last);

        lbl_patient_name.setText(patient.getFirstname() + " " + patient.getSurname());
        lbl_diagnosis.setText(patient.getDiagnosis());

        btn_result = findViewById(R.id.btn_result);
        btn_result.setVisibility(View.GONE);
        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToResult();
            }
        });

        applyOnClickListenersToCard();

        adjustImageResources();

        adjustCardLabels();

        FloatingActionButton fab_close = findViewById(R.id.fab_close);
        fab_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        FloatingActionButton fab_undo = findViewById(R.id.fab_undo);
//        fab_undo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                undo();
//            }
//        });

        FloatingActionButton fab_reference = findViewById(R.id.fab_reference);
        fab_reference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BloodCellReferences.class);
                startActivity(intent);
            }
        });
    }

    private void undo() {
    }

    private void proceedToResult() {
        endCount();
    }

    private void adjustCardLabels() {
        DifferentialCountResult[] results = differntialCount.getCellsForCounting();
        TextView lbl1 = findViewById(R.id.lbl1);
        lbl1.setText(results[0].getBloodCell().getName());
        TextView lbl2 = findViewById(R.id.lbl2);
        lbl2.setText(results[1].getBloodCell().getName());
        TextView lbl3 = findViewById(R.id.lbl3);
        lbl3.setText(results[2].getBloodCell().getName());
        TextView lbl4 = findViewById(R.id.lbl4);
        lbl4.setText(results[3].getBloodCell().getName());
        TextView lbl5 = findViewById(R.id.lbl5);
        lbl5.setText(results[4].getBloodCell().getName());
        TextView lbl6 = findViewById(R.id.lbl6);
        lbl6.setText(results[5].getBloodCell().getName());
        TextView lbl7 = findViewById(R.id.lbl7);
        lbl7.setText(results[6].getBloodCell().getName());
        TextView lbl8 = findViewById(R.id.lbl8);
        lbl8.setText(results[7].getBloodCell().getName());
    }

    private void adjustImageResources() {
        DifferentialCountResult[] results = differntialCount.getCellsForCounting();
        ImageView img1 = findViewById(R.id.img1);
        img1.setImageResource(getCellPicId(results[0].getBloodCell().getName()));
        ImageView img2 = findViewById(R.id.img2);
        img2.setImageResource(getCellPicId(results[1].getBloodCell().getName()));
        ImageView img3 = findViewById(R.id.img3);
        img3.setImageResource(getCellPicId(results[2].getBloodCell().getName()));
        ImageView img4 = findViewById(R.id.img4);
        img4.setImageResource(getCellPicId(results[3].getBloodCell().getName()));
        ImageView img5 = findViewById(R.id.img5);
        img5.setImageResource(getCellPicId(results[4].getBloodCell().getName()));
        ImageView img6 = findViewById(R.id.img6);
        img6.setImageResource(getCellPicId(results[5].getBloodCell().getName()));
        ImageView img7 = findViewById(R.id.img7);
        img7.setImageResource(getCellPicId(results[6].getBloodCell().getName()));
        ImageView img8 = findViewById(R.id.img8);
        img8.setImageResource(getCellPicId(results[7].getBloodCell().getName()));
    }

    private void applyOnClickListenersToCard() {
        CardView btn_cell1 = findViewById(R.id.btn_cell1);
        btn_cell1.setOnClickListener(this);
        CardView btn_cell2 = findViewById(R.id.btn_cell2);
        btn_cell2.setOnClickListener(this);
        CardView btn_cell3 = findViewById(R.id.btn_cell3);
        btn_cell3.setOnClickListener(this);
        CardView btn_cell4 = findViewById(R.id.btn_cell4);
        btn_cell4.setOnClickListener(this);
        CardView btn_cell5 = findViewById(R.id.btn_cell5);
        btn_cell5.setOnClickListener(this);
        CardView btn_cell6 = findViewById(R.id.btn_cell6);
        btn_cell6.setOnClickListener(this);
        CardView btn_cell7 = findViewById(R.id.btn_cell7);
        btn_cell7.setOnClickListener(this);
        CardView btn_cell8 = findViewById(R.id.btn_cell8);
        btn_cell8.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int max = Integer.parseInt(differntialCount.getCellsToBeCounted());
        if(taps < max){
            DifferentialCountResult[] diffResults = differntialCount.getCellsForCounting();
            switch (view.getId()){
                case R.id.btn_cell1:
                    lbl_last.setText(diffResults[0].getBloodCell().getName().toUpperCase());
                    diffResults[0].setCountValue(diffResults[0].getCountValue()+1);
                    showCellTapEffects(R.id.lbl_counter1,diffResults[0].getBloodCell().getShortName());
                    break;
                case R.id.btn_cell2:
                    lbl_last.setText(diffResults[1].getBloodCell().getName().toUpperCase());
                    diffResults[1].setCountValue(diffResults[1].getCountValue()+1);
                    showCellTapEffects(R.id.lbl_counter2,diffResults[1].getBloodCell().getShortName());
                    break;
                case R.id.btn_cell3:
                    lbl_last.setText(diffResults[2].getBloodCell().getName().toUpperCase());
                    diffResults[2].setCountValue(diffResults[2].getCountValue()+1);
                    showCellTapEffects(R.id.lbl_counter3,diffResults[2].getBloodCell().getShortName());
                    break;
                case R.id.btn_cell4:
                    lbl_last.setText(diffResults[3].getBloodCell().getName().toUpperCase());
                    diffResults[3].setCountValue(diffResults[3].getCountValue()+1);
                    showCellTapEffects(R.id.lbl_counter4,diffResults[3].getBloodCell().getShortName());
                    break;
                case R.id.btn_cell5:
                    lbl_last.setText(diffResults[4].getBloodCell().getName().toUpperCase());
                    diffResults[4].setCountValue(diffResults[4].getCountValue()+1);
                    showCellTapEffects(R.id.lbl_counter5,diffResults[4].getBloodCell().getShortName());
                    break;
                case R.id.btn_cell6:
                    lbl_last.setText(diffResults[5].getBloodCell().getName().toUpperCase());
                    diffResults[5].setCountValue(diffResults[5].getCountValue()+1);
                    showCellTapEffects(R.id.lbl_counter6,diffResults[5].getBloodCell().getShortName());
                    break;
                case R.id.btn_cell7:
                    lbl_last.setText(diffResults[6].getBloodCell().getName().toUpperCase());
                    diffResults[6].setCountValue(diffResults[6].getCountValue()+1);
                    showCellTapEffects(R.id.lbl_counter7,diffResults[6].getBloodCell().getShortName());
                    break;
                case R.id.btn_cell8:
                    lbl_last.setText(diffResults[7].getBloodCell().getName().toUpperCase());
                    diffResults[7].setCountValue(diffResults[7].getCountValue()+1);
                    showCellTapEffects(R.id.lbl_counter8,diffResults[7].getBloodCell().getShortName());
                    break;
            }
            differntialCount.setCellsForCounting(diffResults);
            taps++;
            lbl_status.setText(taps + "/" + max);
        }
        if(taps >= max){
            btn_result.setVisibility(View.VISIBLE);
        }
    }

    private void showCellTapEffects(int counter_id,String cell){
        TextView c = findViewById(counter_id);
        int n = Integer.parseInt(c.getText().toString());
        n++;
        c.setText(new Integer(n).toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            tts.speak(cell,TextToSpeech.QUEUE_ADD,null);
        }
        doVibrate();
    }

    private void endCount(){
        if(patient.getDifferntialCounts() == null){
            patient.setDifferntialCounts(new ArrayList<DifferntialCount>());
        }

        patient.getDifferntialCounts().add(differntialCount);
        session.setPatient(patient);
        session.setDifferentialCount(differntialCount);
        api.updatePatient(patient, new CallbackWithResponse() {
            @Override
            public void execute(JSONObject response) {
                Intent intent = new Intent(getApplicationContext(),CountResult.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

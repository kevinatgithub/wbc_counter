package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.ATYPICAL_LYMPHOCYTE;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.BLOOD_CELLS;

public class CounterParameters extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Session session;
    private Patient patient;

    private Spinner spinner_typeOfCounter;
    private Spinner spinner_cellsToBeCounted;

    private CheckBox cb_atypical_lymphocyte;
    private CheckBox cb_band;
    private CheckBox cb_basophil;
    private CheckBox cb_eosinophil;
    private CheckBox cb_lymphoblast;
    private CheckBox cb_lymphocyte;
    private CheckBox cb_metamyelocyte;
    private CheckBox cb_monocyte;
    private CheckBox cb_myeloblast;
    private CheckBox cb_myelocyte;
    private CheckBox cb_neutrophil;
    private CheckBox cb_nrbc;
    private CheckBox cb_promyelocyte;
    private TextView lbl_order;

    private Button btn_save;

    private ArrayList<String> selections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_parameters);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        session = new Session(this);
        patient = session.getPatient();

        spinner_typeOfCounter = findViewById(R.id.spinner_typeOfCounter);
        String[] items = new String[]{"1","4","6","8"};
        ArrayAdapter<String> typeofCounterAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        spinner_typeOfCounter.setAdapter(typeofCounterAdapter);

//        spinner_typeOfCounter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                clearSelections();
//            }
//        });

        spinner_cellsToBeCounted = findViewById(R.id.spinner_cellsToBeCounted);
        String[] items2 = new String[]{"10","20","50","100","200","400"};
        ArrayAdapter<String> cellsToBeCountedAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items2);
        spinner_cellsToBeCounted.setAdapter(cellsToBeCountedAdapter);

        cb_atypical_lymphocyte = findViewById(R.id.cb_atypical_lymphocyte);
        cb_band = findViewById(R.id.cb_band);
        cb_basophil = findViewById(R.id.cb_basophil);
        cb_eosinophil = findViewById(R.id.cb_eosinophil);
        cb_lymphoblast = findViewById(R.id.cb_lymphoblast);
        cb_lymphocyte = findViewById(R.id.cb_lymphocyte);
        cb_metamyelocyte = findViewById(R.id.cb_metamyelocyte);
        cb_monocyte = findViewById(R.id.cb_monocyte);
        cb_myeloblast = findViewById(R.id.cb_myeloblast);
        cb_myelocyte = findViewById(R.id.cb_myelocyte);
        cb_neutrophil = findViewById(R.id.cb_neutrophil);
        cb_nrbc = findViewById(R.id.cb_nrbc);
        cb_promyelocyte = findViewById(R.id.cb_promyelocyte);

        lbl_order = findViewById(R.id.lbl_order);

        selections = new ArrayList<String>();

        cb_atypical_lymphocyte.setOnCheckedChangeListener(this);
        cb_band.setOnCheckedChangeListener(this);
        cb_basophil.setOnCheckedChangeListener(this);
        cb_eosinophil.setOnCheckedChangeListener(this);
        cb_lymphoblast.setOnCheckedChangeListener(this);
        cb_lymphocyte.setOnCheckedChangeListener(this);
        cb_metamyelocyte.setOnCheckedChangeListener(this);
        cb_monocyte.setOnCheckedChangeListener(this);
        cb_myeloblast.setOnCheckedChangeListener(this);
        cb_myelocyte.setOnCheckedChangeListener(this);
        cb_neutrophil.setOnCheckedChangeListener(this);
        cb_nrbc.setOnCheckedChangeListener(this);
        cb_promyelocyte.setOnCheckedChangeListener(this);

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSave();
            }
        });
    }

    private void clearSelections() {
        selections = new ArrayList<String>();
        cb_atypical_lymphocyte.setChecked(false);
        cb_band.setChecked(false);
        cb_basophil.setChecked(false);
        cb_eosinophil.setChecked(false);
        cb_lymphoblast.setChecked(false);
        cb_lymphocyte.setChecked(false);
        cb_metamyelocyte.setChecked(false);
        cb_monocyte.setChecked(false);
        cb_myeloblast.setChecked(false);
        cb_myelocyte.setChecked(false);
        cb_neutrophil.setChecked(false);
        cb_nrbc.setChecked(false);
        cb_promyelocyte.setChecked(false);
        showAllCells();
    }

    private void doSave() {

        if(selections.size() != Integer.parseInt(spinner_typeOfCounter.getSelectedItem().toString())){
            return;
        }

        ArrayList<DifferentialCountResult> diffResults = new ArrayList<DifferentialCountResult>();

        for(String cell : selections){

            // TODO: 01/12/2018 This should be avoided
            BloodCell c = ATYPICAL_LYMPHOCYTE;

            for(BloodCell bloodCell : BLOOD_CELLS){
                if(cell.toUpperCase().equals(bloodCell.getName())){
                    c = bloodCell;
                }
            }
            diffResults.add(new DifferentialCountResult(
                    c, 0
            ));
        }

        DifferntialCount differntialCount = new DifferntialCount(
                spinner_typeOfCounter.getSelectedItem().toString(),
                spinner_cellsToBeCounted.getSelectedItem().toString(),
                diffResults.toArray(new DifferentialCountResult[diffResults.size()])
        );


        session.setDifferentialCount(differntialCount);

        Intent intent;
        switch (differntialCount.getTypeOfCounter()){
            case "1":
                intent = new Intent(getApplicationContext(),Counter1.class);
                break;
            case "4":
                intent = new Intent(getApplicationContext(),Counter4.class);
                break;
            case "6":
                intent = new Intent(getApplicationContext(),Counter6.class);
                break;
            case "8":
                intent = new Intent(getApplicationContext(),Counter8.class);
                break;
                default:
                    intent = new Intent(getApplicationContext(),Counter1.class);
        }

        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            selections.add(compoundButton.getText().toString());


        }else{
            selections.remove(compoundButton.getText().toString());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(String cell : selections){
            stringBuilder.append(cell + " , ");
        }
        lbl_order.setText("The order of clicking will be in the following:\n" +stringBuilder.toString());

        int typeofcounter = Integer.parseInt(spinner_typeOfCounter.getSelectedItem().toString());
        if(selections.size() >= typeofcounter){
            hideUnselectedCells();
        }else{
            showAllCells();
        }
    }

    private void hideUnselectedCells() {
        if(!cb_atypical_lymphocyte.isChecked()){
            cb_atypical_lymphocyte.setEnabled(false);
        }

        if(!cb_band.isChecked()){
            cb_band.setEnabled(false);
        }

        if(!cb_basophil.isChecked()){
            cb_basophil.setEnabled(false);
        }

        if(!cb_eosinophil.isChecked()){
            cb_eosinophil.setEnabled(false);
        }

        if(!cb_lymphoblast.isChecked()){
            cb_lymphoblast.setEnabled(false);
        }

        if(!cb_lymphocyte.isChecked()){
            cb_lymphocyte.setEnabled(false);
        }

        if(!cb_metamyelocyte.isChecked()){
            cb_metamyelocyte.setEnabled(false);
        }

        if(!cb_monocyte.isChecked()){
            cb_monocyte.setEnabled(false);
        }

        if(!cb_myeloblast.isChecked()){
            cb_myeloblast.setEnabled(false);
        }

        if(!cb_myelocyte.isChecked()){
            cb_myelocyte.setEnabled(false);
        }

        if(!cb_neutrophil.isChecked()){
            cb_neutrophil.setEnabled(false);
        }

        if(!cb_nrbc.isChecked()){
            cb_nrbc.setEnabled(false);
        }

        if(!cb_promyelocyte.isChecked()){
            cb_promyelocyte.setEnabled(false);
        }
    }

    private void showAllCells(){
        cb_atypical_lymphocyte.setEnabled(true);
        cb_band.setEnabled(true);
        cb_basophil.setEnabled(true);
        cb_eosinophil.setEnabled(true);
        cb_lymphoblast.setEnabled(true);
        cb_lymphocyte.setEnabled(true);
        cb_metamyelocyte.setEnabled(true);
        cb_monocyte.setEnabled(true);
        cb_myeloblast.setEnabled(true);
        cb_myelocyte.setEnabled(true);
        cb_neutrophil.setEnabled(true);
        cb_nrbc.setEnabled(true);
        cb_promyelocyte.setEnabled(true);
    }
}

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CounterParameters extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Session session;
    private Patient patient;

    private Spinner spinner_typeOfCounter;
    private Spinner spinner_cellsToBeCounted;

    private LinearLayout ll_cells;
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
        String[] items = new String[]{"1","4","6","8","12"};
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

        ll_cells = findViewById(R.id.ll_cells);
        for(BloodCell bc : session.getCellCollection().getCells()){
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(bc.getName());
            cb.setOnCheckedChangeListener(this);
            ll_cells.addView(cb);
        }

        lbl_order = findViewById(R.id.lbl_order);

        selections = new ArrayList<String>();


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
        for(int i = 0; i < ll_cells.getChildCount(); i++){
            CheckBox cb = (CheckBox) ll_cells.getChildAt(i);
            cb.setChecked(false);
        }
        showAllCells();
    }

    private void doSave() {

        if(selections.size() != Integer.parseInt(spinner_typeOfCounter.getSelectedItem().toString())){
            return;
        }

        ArrayList<DifferentialCountResult> diffResults = new ArrayList<DifferentialCountResult>();

        for(String cell : selections){

            // TODO: 01/12/2018 This should be avoided
            BloodCell c = session.getCellCollection().getCells()[0];

            for(BloodCell bloodCell : session.getCellCollection().getCells()){
                if(cell.equals(bloodCell.getName())){
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
            case "12":
                intent = new Intent(getApplicationContext(),Counter12.class);
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
        for(int i = 0; i < ll_cells.getChildCount(); i++){
            CheckBox cb = (CheckBox) ll_cells.getChildAt(i);
            if(!cb.isChecked()){
                cb.setEnabled(false);
            }
        }
    }

    private void showAllCells(){
        for(int i = 0; i < ll_cells.getChildCount(); i++){
            CheckBox cb = (CheckBox) ll_cells.getChildAt(i);
            cb.setEnabled(true);
        }
    }
}

package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class PatientPreview extends AppCompatActivity {

    private ApiCallManager api;
    private Gson gson;
    private Session session;
    private Patient patient;
    private ListView lv_counts;
    private TextView lbl_patient_id;
    private TextView lbl_patient_name;
    private TextView lbl_diagnosis;
    private TextView lbl_dob;
    private TextView lbl_wbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_preview);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        api = new ApiCallManager(this);
        gson = new Gson();
        session = new Session(this);
        patient = session.getPatient();

        lbl_patient_id = findViewById(R.id.lbl_patient_id);
        lbl_patient_name = findViewById(R.id.lbl_patient_name);
        lbl_diagnosis = findViewById(R.id.lbl_diagnosis);
        lbl_dob = findViewById(R.id.lbl_dob);
        lbl_wbc = findViewById(R.id.lbl_wbc);

        api.getPatientInfo(patient.getPatient_id(), new CallbackWithResponse(){


            @Override
            public void execute(JSONObject response) {
                ApiResult result = gson.fromJson(response.toString(),ApiResult.class);
                patient = result.patient;

                if(patient.getDifferntialCounts() != null){
                    ArrayList<DifferntialCount> differntialCounts = patient.getDifferntialCounts();
                    CountRowAdapter countRowAdapter = new CountRowAdapter(getApplicationContext(),differntialCounts);

                    lv_counts = findViewById(R.id.lv_counts);
                    lv_counts.setAdapter(countRowAdapter);
                    lv_counts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            DifferntialCount diff = (DifferntialCount) lv_counts.getItemAtPosition(i);
                            session.setDifferentialCount(diff);
                            Intent intent = new Intent(getApplicationContext(),CountResult.class);
                            startActivity(intent);
                        }
                    });

                }



                lbl_patient_id.setText(patient.getPatient_id());
                lbl_patient_name.setText(patient.getFirstname() + " "+ patient.getSurname());
                lbl_diagnosis.setText(patient.getDiagnosis());
                lbl_dob.setText(patient.getDob());
                lbl_wbc.setText(patient.getWbc());

            }
        });



        FloatingActionButton fab_new_count = findViewById(R.id.fab_new_count);
        fab_new_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CounterParameters.class  );
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fab_edit = findViewById(R.id.fab_edit);
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterPatient.class);
                intent.putExtra("update","yes");
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fab_delete = findViewById(R.id.fab_delete);
        fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
    }

    private void confirmDelete() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Delete Patient Record?");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                api.deletePatient(patient.getPatient_id(), new CallbackWithResponse() {
                    @Override
                    public void execute(JSONObject response) {
                        Toast.makeText(PatientPreview.this, "Patient has been deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private class ApiResult{
        private Patient patient;

        public ApiResult(Patient patient) {
            this.patient = patient;
        }

        public Patient getPatient() {
            return patient;
        }

        public void setPatient(Patient patient) {
            this.patient = patient;
        }
    }

    private class CountRowAdapter extends ArrayAdapter<DifferntialCount>{

        public CountRowAdapter(@NonNull Context context, ArrayList<DifferntialCount> items) {
            super(context, 0,items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            DifferntialCount d = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.count_row, parent,false);
            }

            TextView lbl_cells_to_count = convertView.findViewById(R.id.lbl_cells_to_count);
            TextView lbl_type = convertView.findViewById(R.id.lbl_type);
            TextView lbl_cells = convertView.findViewById(R.id.lbl_cells);

            lbl_type.setText(d.getTypeOfCounter());
            lbl_cells_to_count.setText(d.getCellsToBeCounted());
            StringBuilder cells_str = new StringBuilder();
            for(DifferentialCountResult c : d.getCellsForCounting()){
                cells_str.append(c.getBloodCell().getName() + "\t");
            }
            lbl_cells.setText(cells_str.toString());

            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}

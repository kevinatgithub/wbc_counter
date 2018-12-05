
package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class PatientList extends AppCompatActivity {

    private ApiCallManager api;
    private Session session;
    private Gson gson;
    private ListView lv_patients;
    private ImageView img_no_patients;
    private TextView lbl_no_patients;
    private FloatingActionButton fab_new_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        api = new ApiCallManager(this);
        session = new Session(this);
        gson = new Gson();
        lv_patients = findViewById(R.id.lv_patients);
        lv_patients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patient patient = (Patient) lv_patients.getItemAtPosition(i);
                session.setPatient(patient);
                Patient p = session.getPatient();
                Intent intent = new Intent(getApplicationContext(),PatientPreview.class);
                startActivity(intent);
            }
        });
        img_no_patients = findViewById(R.id.img_no_patient);
        lbl_no_patients = findViewById(R.id.lbl_no_patients);
        fab_new_patient = findViewById(R.id.fab_new_patient);

        fab_new_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterPatient.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        session.removeUser();
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        fetchPatients();
        super.onResume();
    }

    private void fetchPatients() {
        api.getPatientList(new CallbackWithResponse() {
            @Override
            public void execute(JSONObject response) {
                Response r = gson.fromJson(response.toString(),Response.class);
                if(r.patients.length > 0){
                    ArrayList<Patient> patients = new ArrayList<Patient>(Arrays.asList(r.patients));
                    PatientListAdapter adapter = new PatientListAdapter(getApplicationContext(),patients);
                    lv_patients.setAdapter(adapter);
                    lv_patients.setVisibility(View.VISIBLE);
                    img_no_patients.setVisibility(View.GONE);
                    lbl_no_patients.setVisibility(View.GONE);
                }else{
                    lv_patients.setVisibility(View.GONE);
                    img_no_patients.setVisibility(View.VISIBLE);
                    lbl_no_patients.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private class Response{
        Patient[] patients;
    }
}

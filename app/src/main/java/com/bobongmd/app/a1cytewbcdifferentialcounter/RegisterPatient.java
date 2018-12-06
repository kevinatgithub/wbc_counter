package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.Period;

public class RegisterPatient extends AppCompatActivity {

    private Session session;
    private ApiCallManager api;
    private Gson gson;

    private TextInputLayout tl_patient_id;
    private EditText txt_patient_id;
    private TextInputLayout tl_firstname;
    private EditText txt_firstname;
    private TextInputLayout tl_surname;
    private EditText txt_surname;
    private TextView txt_dob;
    private TextInputLayout tl_diagnosis;
    private EditText txt_diagnosis;
    private TextInputLayout tl_wbc;
    private EditText txt_wbc;
    private TextView lbl_age;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        session = new Session(this);
        api = new ApiCallManager(this);
        gson = new Gson();

        lbl_age = findViewById(R.id.lbl_age);
        tl_patient_id = findViewById(R.id.tl_patient_id);
        txt_patient_id = findViewById(R.id.txt_patient_id);
        tl_firstname = findViewById(R.id.tl_firstname);
        txt_firstname = findViewById(R.id.txt_firstname);
        tl_surname = findViewById(R.id.tl_surname);
        txt_surname = findViewById(R.id.txt_surname);
        txt_dob = findViewById(R.id.txt_dob);
        tl_diagnosis = findViewById(R.id.tl_diagnosis);
        txt_diagnosis = findViewById(R.id.txt_diagnosis);
        tl_wbc = findViewById(R.id.tl_wbc);
        txt_wbc = findViewById(R.id.txt_wbc);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                lbl_age.setText(getAge(i,i1,i2) + " years old");
                String m = i1 < 10 ? "0" + i1 : "" +i1;
                String d = i2 < 10 ? "0" + i2 : "" + i2;
                String dob = i+"-"+m+"-"+d;
                txt_dob.setText(dob);
//                lbl_age.setText(calculateAge());

            }
        });
        ImageView img_dob = findViewById(R.id.img_dob);
        img_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        Button btn_clear = findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doClear();
            }
        });

        if(getIntent().getStringExtra("update") != null){
            populateForm();
        }
    }

    private void populateForm() {
        Patient patient = session.getPatient();
        txt_patient_id.setText(patient.getPatient_id());
        txt_patient_id.setEnabled(false);
        txt_firstname.setText(patient.getFirstname());
        txt_surname.setText(patient.getSurname());
        txt_dob.setText(patient.getDob());
        txt_diagnosis.setText(patient.getDiagnosis());
        txt_wbc.setText(patient.getWbc());
    }

    private void doClear() {
        if(getIntent().getStringExtra("update") == null){
            txt_patient_id.setText("");
            txt_firstname.setText("");
            txt_surname.setText("");
            txt_dob.setText("Tap the Calendar to Select Date");
            txt_diagnosis.setText("");
            txt_wbc.setText("");
        }else{
            populateForm();
        }
    }

    private void validateForm() {

        if(txt_patient_id.getText().length() == 0){
            tl_patient_id.setError("Patient ID is required");
        }

        if(txt_firstname.getText().length() == 0){
            tl_firstname.setError("First Name is required");
        }

        if(txt_surname.getText().length() == 0){
            tl_surname.setError("Sur Name is required");
        }

        if(txt_dob.getText().length() == 0){
            Toast.makeText(this, "Date of Birth is required", Toast.LENGTH_LONG).show();
        }

        if(txt_diagnosis.getText().length() ==0){
            tl_diagnosis.setError("Diagnosis is required");
        }

        if(txt_wbc.getText().length() ==0){
            tl_wbc.setError("WBC Count is required");
        }

        if(txt_patient_id.getText().length() > 0 && txt_firstname.getText().length() > 0 && txt_surname.getText().length() > 0 && txt_dob.getText().length() > 0 && txt_diagnosis.getText().length() > 0 && txt_wbc.getText().length() > 0){
            Intent i = getIntent();
            String update = i.getStringExtra("update");
            if(i.getStringExtra("update") != null){
                doUpdate();
            }else{
                doRegister();
            }
        }
    }

    private void doUpdate() {
        final Patient patient = new Patient(
                txt_patient_id.getText().toString(),
                txt_firstname.getText().toString(),
                txt_surname.getText().toString(),
                txt_dob.getText().toString(),
                txt_diagnosis.getText().toString(),
                txt_wbc.getText().toString(),null
        );

        api.updatePatientProfile(patient, new CallbackWithResponse() {
            @Override
            public void execute(JSONObject response) {
                session.setPatient(patient);
                Intent intent = new Intent(getApplicationContext(),PatientPreview.class);
                startActivity(intent);
                Toast.makeText(RegisterPatient.this, "Patient record has been updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void doRegister() {

        final Patient patient = new Patient(
                txt_patient_id.getText().toString(),
                txt_firstname.getText().toString(),
                txt_surname.getText().toString(),
                txt_dob.getText().toString(),
                txt_diagnosis.getText().toString(),
                txt_wbc.getText().toString(),null
        );

        api.registerPatient(patient, new CallbackWithResponse() {
            @Override
            public void execute(JSONObject response) {
                session.setPatient(patient);
                Intent intent = new Intent(getApplicationContext(),CounterParameters.class);
                intent.putExtra("patient",gson.toJson(patient));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}

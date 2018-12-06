
package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.Reference;
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

    private DrawerLayout dl_patient_list;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_list_container);

        api = new ApiCallManager(this);
        session = new Session(this);
        gson = new Gson();

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        dl_patient_list = findViewById(R.id.dl_patient_list);
        toggle = new ActionBarDrawerToggle(this,dl_patient_list,toolbar, R.string.drawer_open,R.string.drawer_closed);
        dl_patient_list.addDrawerListener(toggle);

        provideDrawerOnClickListeners();


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

    private void provideDrawerOnClickListeners() {
        NavigationView navView = findViewById(R.id.nv_drawer);
        View headerView = navView.getHeaderView(0);

        TextView txt_drawer_user_fullname = headerView.findViewById(R.id.txt_drawer_user_fullname);
        TextView txt_drawer_user_designation = headerView.findViewById(R.id.txt_drawer_user_designation);
        User user = session.getUser();
        txt_drawer_user_fullname.setText(user.getFull_name());
        txt_drawer_user_designation.setText(user.getDesignation());

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.nav_references:
                        Intent intent = new Intent(getApplicationContext(), BloodCellReferences.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        confirmLogout();
                        break;
                }
                return false;
            }
        });
    }

    private void confirmLogout() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Logout");
        dialog.setMessage("Do you want to logout?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                performLogout();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void performLogout() {
        session.removeUser();
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
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

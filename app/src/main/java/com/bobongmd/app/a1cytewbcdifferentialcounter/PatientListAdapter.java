package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PatientListAdapter extends ArrayAdapter<Patient> {

    public PatientListAdapter(@NonNull Context context, ArrayList<Patient> patients) {
        super(context, 0, patients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Patient patient = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.patient_list_row, parent,false);
        }

        TextView lbl_fullname = convertView.findViewById(R.id.lbl_fullname);
        TextView lbl_diagnosis = convertView.findViewById(R.id.lbl_diagnosis);
        lbl_fullname.setText(patient.getFirstname() + " " + patient.getSurname());
        lbl_diagnosis.setText(patient.getDiagnosis());

        return convertView;
    }
}

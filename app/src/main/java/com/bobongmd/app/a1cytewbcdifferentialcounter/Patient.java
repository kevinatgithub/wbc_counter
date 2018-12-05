package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class Patient {

    private String patient_id;
    private String firstname;
    private String surname;
    private String dob;
    private String diagnosis;
    private String wbc;
    private ArrayList<DifferntialCount> differntialCounts;

    public Patient(String patient_id, String firstname, String surname, String dob, String diagnosis, String wbc, @Nullable ArrayList<DifferntialCount> differntialCounts) {
        this.patient_id = patient_id;
        this.firstname = firstname;
        this.surname = surname;
        this.dob = dob;
        this.diagnosis = diagnosis;
        this.wbc = wbc;
        this.differntialCounts = differntialCounts;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getWbc() {
        return wbc;
    }

    public void setWbc(String wbc) {
        this.wbc = wbc;
    }

    public ArrayList<DifferntialCount> getDifferntialCounts() {
        return differntialCounts;
    }

    public void setDifferntialCounts(ArrayList<DifferntialCount> differntialCounts) {
        this.differntialCounts = differntialCounts;
    }
}

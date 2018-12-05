package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class Session {

    private SharedPreferences prefs;
    private Gson gson;

    public Session(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    public void setUser(User user){
        String jsonValue = gson.toJson(user);
        prefs.edit().putString("user",jsonValue).commit();
    }

    public User getUser(){
        String jsonValue = prefs.getString("user","");
        if(jsonValue.equals("")){
            return null;
        }
        return gson.fromJson(jsonValue,User.class);
    }

    public void removeUser(){
        prefs.edit().remove("user").commit();
    }

    public void setPatient(Patient patient){
        String jsonValue = gson.toJson(patient);
        prefs.edit().putString("patient",jsonValue).commit();
    }

    public Patient getPatient(){
        String jsonValue = prefs.getString("patient","");
        if(jsonValue.equals("")){
            return null;
        }
        return gson.fromJson(jsonValue,Patient.class);
    }

    public void removePatient(){
        prefs.edit().remove("patient").commit();
    }

    public void setDifferentialCount(DifferntialCount diff){
        String jsonValue = gson.toJson(diff);
        prefs.edit().putString("diff",jsonValue).commit();
    }

    public DifferntialCount getDifferentialCount(){
        String jsonValue = prefs.getString("diff","");
        if(jsonValue.equals("")){
            return null;
        }
        return gson.fromJson(jsonValue,DifferntialCount.class);
    }

    public void removeDifferentialCount(){
        prefs.edit().remove("diff").commit();
    }

}

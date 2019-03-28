package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ApiCallManager {

    private final String API_LOGIN = "http://b0b0ng.getsandbox.com/login";
    private final String API_REGISTER = "http://b0b0ng.getsandbox.com/register";
    private final String API_CHECK_USER_ID = "http://b0b0ng.getsandbox.com/checkuserid/[USER_ID]";
    private final String API_PATIENTS = "http://b0b0ng.getsandbox.com/patients";
    private final String API_PATIENT_INFO = "http://b0b0ng.getsandbox.com/patient/info/[PATIENT_ID]";
    private final String API_PATIENT_REGISTER = "http://b0b0ng.getsandbox.com/patient/register";
    private final String API_UPDATE_PATIENT = "http://b0b0ng.getsandbox.com/patient/update";
    private final String API_DELETE_PATIENT = "http://b0b0ng.getsandbox.com/patient";
    private final String API_GET_CELLS = "http://b0b0ng.getsandbox.com/cells";
    private final String API_CELL = "http://b0b0ng.getsandbox.com/cell";

    private Activity activity;
    private RequestQueue requestQueue;
    private Gson gson;

    ApiCallManager(Activity activity) {
        this.activity = activity;
        this.gson = new Gson();
        this.requestQueue = Volley.newRequestQueue(activity);
    }

    void login(CallbackWithResponse callback,String user_id, String password){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("user_id",user_id);
        headers.put("password",password);
        JSONObject jsonObject = new JSONObject();

        call(API_LOGIN,Request.Method.POST,headers,callback,null);
    }

    void register(CallbackWithResponse callback,User user){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("user_id",user.getUser_id());
        headers.put("password",user.getPassword());
        headers.put("full_name",user.getFull_name());
        headers.put("designation",user.getDesignation());

        call(API_REGISTER,Request.Method.POST,headers,callback,null);
    }

    void checkUserId(CallbackWithResponse callback,String user_id){
        String url = API_CHECK_USER_ID.replace("[USER_ID]",user_id);
        get(url,callback,null);
    }

    void getPatientList(CallbackWithResponse callback){
        get(API_PATIENTS,callback,null);
    }

    void registerPatient(Patient patient,CallbackWithResponse callback){

        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("patient_id",patient.getPatient_id());
        headers.put("firstname",patient.getFirstname());
        headers.put("surname",patient.getSurname());
        headers.put("dob",patient.getDob());
        headers.put("diagnosis",patient.getDiagnosis());
        headers.put("wbc",patient.getWbc());

        call(API_PATIENT_REGISTER,Request.Method.POST,headers,callback ,null);
    }

    void updatePatientProfile(Patient patient,CallbackWithResponse callback){

        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("patient_id",patient.getPatient_id());
        headers.put("firstname",patient.getFirstname());
        headers.put("surname",patient.getSurname());
        headers.put("dob",patient.getDob());
        headers.put("diagnosis",patient.getDiagnosis());
        headers.put("wbc",patient.getWbc());

        call(API_PATIENT_REGISTER,Request.Method.PUT,headers,callback ,null);
    }

    void getPatientInfo(String patientId, CallbackWithResponse callback){
        String url = API_PATIENT_INFO.replace("[PATIENT_ID]",patientId);

        get(url,callback,null);
    }

    void updatePatient(final Patient patient, final CallbackWithResponse callback){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("patient", gson.toJson(patient));

        call(API_UPDATE_PATIENT, Request.Method.PUT, headers, callback,null);

    }

    void deletePatient(final String patient_id, final CallbackWithResponse callback){
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("patient_id",patient_id);

        call(API_DELETE_PATIENT, Request.Method.DELETE,headers,callback,null);
    }
    
    void getCells(CallbackWithResponse callback){
        get(API_GET_CELLS,callback,null);
    }

    void registerCell(BloodCell cell, CallbackWithResponse callback){
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("name",cell.getName());
        headers.put("shortname",cell.getShortname());
        headers.put("img",cell.getImg());

        call(API_CELL,Request.Method.POST,headers,callback,null);
    }

    void updateCell(BloodCell cell, CallbackWithResponse callback){
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("name",cell.getName());
        headers.put("shortname",cell.getShortname());
        headers.put("img",cell.getImg());

        call(API_CELL,Request.Method.PUT,headers,callback,null);
    }

    void deleteCell(String cellName, CallbackWithResponse callback){
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("name",cellName);

        call(API_CELL,Request.Method.DELETE,headers,callback,null);
    }

    private void get(final String URL, final CallbackWithResponse CALLBACK, @Nullable final Callback ON_ERROR_CALLBACK){
        checkConnection(new Callback() {
            @Override
            public void execute() {

                requestQueue.add(
                        new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                CALLBACK.execute(response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                handleAPIExceptionResponse(error);
                                if(ON_ERROR_CALLBACK != null){
                                    ON_ERROR_CALLBACK.execute();
                                }
                            }
                        }
                        )
                );
            }
        });
    }

    private void call(final String URL, final int METHOD, final HashMap<String,String> HEADERS, final CallbackWithResponse CALLBACK, @Nullable final Callback ON_ERROR_CALLBACK){
        checkConnection(new Callback() {
            @Override
            public void execute() {

                requestQueue.add(
                        new JsonObjectRequest(METHOD, URL, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                CALLBACK.execute(response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                handleAPIExceptionResponse(error);
                                if(ON_ERROR_CALLBACK != null){
                                    ON_ERROR_CALLBACK.execute();
                                }
                            }
                        }
                        ){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                return HEADERS;
                            }
                        }
                );
            }
        });
    }

    private void handleAPIExceptionResponse(VolleyError error){
        NetworkResponse networkResponse = error.networkResponse;

        Intent intent = new Intent(activity,ShowServerResponse.class);
        if(networkResponse == null){
            intent.putExtra("message","NETWORK ERROR \nPlease check your Internet/Wifi settings.");
        }else if(networkResponse.statusCode == 400){
            intent.putExtra("message","NETWORK ERROR 400 \nPlease contact administrator.");
        }else{
            intent.putExtra("message","ERROR      "+networkResponse.statusCode+" " + "Please contact administrator.");        }
        activity.startActivity(intent);
    }

    private void checkConnection(final Callback CALLBACK){

        ConnectivityManager conMgr =  (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){

            LayoutInflater inflater = LayoutInflater.from(activity);
            final AlertDialog DIALOG = new AlertDialog.Builder(activity).create();
            DIALOG.setTitle("Can't connect to Server");
            View customView = inflater.inflate(R.layout.network_fail,null);
            DIALOG.setView(customView);
            DIALOG.setCancelable(false);
            DIALOG.setButton(AlertDialog.BUTTON_POSITIVE, "TRY AGAIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DIALOG.dismiss();
                    checkConnection(CALLBACK);
                }
            });

            DIALOG.setOnShowListener(new DialogInterface.OnShowListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    DIALOG.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimary);
                }
            });
            DIALOG.show();
        }else{
            CALLBACK.execute();
        }
    }
}

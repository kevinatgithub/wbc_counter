package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private Session session;
    private Gson gson;
    private ApiCallManager api;

    private TextInputLayout tl_user_id;
    private TextInputLayout tl_password;
    private TextInputLayout tl_confirm_password;
    private TextInputLayout tl_full_name;
    private TextInputLayout tl_designation;
    private EditText txt_user_id;
    private EditText txt_password;
    private EditText txt_confirm_password;
    private EditText txt_full_name;
    private EditText txt_designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        session = new Session(this);
        gson = new Gson();
        api = new ApiCallManager(this);

        tl_user_id = findViewById(R.id.tl_user_id);
        tl_password = findViewById(R.id.tl_password);
        tl_confirm_password = findViewById(R.id.tl_confirm_password);
        tl_full_name = findViewById(R.id.tl_fullname);
        tl_designation = findViewById(R.id.tl_designation);
        txt_user_id = findViewById(R.id.txt_user_id);
        txt_password = findViewById(R.id.txt_password);
        txt_confirm_password = findViewById(R.id.txt_confirm_password);
        txt_full_name = findViewById(R.id.txt_full_name);
        txt_designation = findViewById(R.id.txt_designation);

        txt_user_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validateUserId();
            }
        });

        txt_confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validateConfirmPassword();
            }
        });

        Button btn= findViewById(R.id.btn_register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }

    private void validateConfirmPassword() {

        if(txt_confirm_password.getText().length() >0 ){
            if(txt_confirm_password.getText() != txt_password.getText()){
                tl_confirm_password.setError("Password not match");
            }else{
                tl_confirm_password.setError(null);
            }
        }
    }

    private void validateUserId() {
        String user_id = txt_user_id.getText().toString();
        if(user_id.length() > 0){
            api.checkUserId(new CallbackWithResponse() {
                @Override
                public void execute(JSONObject response) {
                    Response r = gson.fromJson(response.toString(),Response.class);
                    // TODO: 30/11/2018 Must be validated before pressing register button
                    if(r.status){
                        tl_user_id.setError("User ID already taken");
                    }else{
                        tl_user_id.setError(null);
                    }
                }
            },user_id);
        }
    }

    private void validateForm() {
        if(txt_user_id.getText().length() == 0){
            tl_user_id.setError("User ID is required");
        }

        if(txt_password.getText().length() == 0){
            tl_password.setError("Password is required");
        }

        if(txt_full_name.getText().length() == 0){
            tl_full_name.setError("Please enter full name");
        }

        if(txt_designation.getText().length() == 0){
            tl_designation.setError("Please enter designation");
        }

        if(tl_user_id.getError() == null && txt_password.getText().length() != 0 && tl_confirm_password.getError() == null && txt_full_name.getText().length() != 0 && txt_designation.getText().length() != 0){
            proceedRegistration();
        }
    }

    private void proceedRegistration() {
        String user_id = txt_user_id.getText().toString();
        String password = txt_password.getText().toString();
        String fullname = txt_full_name.getText().toString();
        String designation = txt_designation.getText().toString();

        final User USER = new User(user_id,password,fullname,designation);

        api.register(new CallbackWithResponse() {
            @Override
            public void execute(JSONObject response) {
                Response r = gson.fromJson(response.toString(),Response.class);
                if(r.status){
                    session.setUser(USER);
                    Intent intent = new Intent(getApplicationContext(),PatientList.class);
                    startActivity(intent);
                    Toast.makeText(Register.this, "Welcome " + USER.getFull_name(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        },USER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private class Response{

        public Boolean status;
    }
}

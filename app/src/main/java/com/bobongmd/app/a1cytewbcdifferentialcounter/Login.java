package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private Session session;
    private Gson gson;
    private TextInputLayout tl_user_id;
    private TextInputLayout tl_password;
    private EditText txt_user_id;
    private EditText txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        session = new Session(this);
        gson = new Gson();
        tl_user_id = findViewById(R.id.tl_user_id);
        tl_password = findViewById(R.id.tl_password);
        txt_user_id = findViewById(R.id.txt_user_id);
        txt_password = findViewById(R.id.txt_password);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    private void validateForm() {
        if(txt_user_id.getText().length() == 0){
            tl_user_id.setError("User ID is required");
        }

        if(txt_password.getText().length() == 0){
            tl_password.setError("Password is required");
        }

        if(txt_user_id.getText().length() != 0 && txt_password.getText().length() !=0){
            tl_user_id.setError(null);
            tl_password.setError(null);
            proceedLogin();
        }
    }



    private void proceedLogin() {
        String user_id = txt_user_id.getText().toString();
        String password = txt_password.getText().toString();

        ApiCallManager api = new ApiCallManager(this);
        api.login(new CallbackWithResponse() {
            @Override
            public void execute(JSONObject response) {
                applyLogin(response);
            }
        },user_id,password);
    }

    private void applyLogin(JSONObject response) {
        Response r = gson.fromJson(response.toString(),Response.class);
        User user = r.user;
        if(user != null){
            session.setUser(user);
            Intent intent = new Intent(this,PatientList.class);
            startActivity(intent);
            finish();
        }else{
            tl_user_id.setError("User ID and Password not found");
        }
    }

    private class Response{
        public User user;
    }

    @Override
    protected void onResume() {
        User user = session.getUser();
        if(user != null){
            Intent intent = new Intent(this,PatientList.class);
            startActivity(intent);
            Toast.makeText(this, "Welcome back " + user.getFull_name() , Toast.LENGTH_SHORT).show();
            finish();
        }
        super.onResume();
    }
}

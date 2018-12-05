package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowServerResponse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_server_response);
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        TextView txt_network_problem = findViewById(R.id.txt_network_problem);
        txt_network_problem.setText(message);

    }
}

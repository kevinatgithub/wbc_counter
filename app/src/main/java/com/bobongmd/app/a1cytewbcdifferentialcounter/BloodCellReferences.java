package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class BloodCellReferences extends AppCompatActivity {

    private Session session;
    private BloodCellCollection collection;
    private ListView lv_cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        session = new Session(this);
        lv_cells = findViewById(R.id.lv_cells);



        lv_cells.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BloodCell cell = (BloodCell) lv_cells.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(),RegisterReference.class);
                intent.putExtra("cell",new Gson().toJson(cell));
                startActivity(intent);
            }
        });

        FloatingActionButton fab_new_reference = findViewById(R.id.fab_new_reference);
        fab_new_reference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterReference.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private class ReferencesArrayAdapter extends ArrayAdapter<BloodCell>{

        public ReferencesArrayAdapter(@NonNull Context context, ArrayList<BloodCell> cells) {
            super(context, 0, cells);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            BloodCell cell = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_row, parent,false);
            }

            String base64Image = cell.getImg().split(",")[1];

            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            TextView txt_cell_name = convertView.findViewById(R.id.txt_cell_name);
            txt_cell_name.setText(cell.getName());
            ImageView img_cell_img = convertView.findViewById(R.id.img_cell_img);
            img_cell_img.setImageBitmap(decodedByte);

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        collection = session.getCellCollection();
        ReferencesArrayAdapter referencesArrayAdapter = new ReferencesArrayAdapter(this,new ArrayList<BloodCell>(Arrays.asList(collection.getCells())));
        lv_cells.setAdapter(referencesArrayAdapter);
        super.onResume();
    }
}

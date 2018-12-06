package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class RegisterReference extends AppCompatActivity {

    private ApiCallManager api;
    private Gson gson;
    private Session session;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextInputLayout tl_cell_name;
    private EditText txt_cell_name;
    private TextInputLayout tl_short_name;
    private EditText txt_short_name;
    private ImageView img_photo;
    private String base64converted = "";
    private TextToSpeech tts;
    private TextView lbl_photo;
    private Intent intent;
    private BloodCell bloodCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reference);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        api = new ApiCallManager(this);
        gson = new Gson();
        session = new Session(this);
        
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

            }
        });
        tts.setLanguage(Locale.US);
        tts.setSpeechRate(2f);


        img_photo = findViewById(R.id.img_photo);
        tl_cell_name = findViewById(R.id.tl_cell_name);
        txt_cell_name = findViewById(R.id.txt_cell_name);
        tl_short_name = findViewById(R.id.tl_short_name);
        txt_short_name = findViewById(R.id.txt_short_name);
        lbl_photo = findViewById(R.id.lbl_photo);

        img_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        Button btn_speak = findViewById(R.id.btn_speak);
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSpeak();
            }
        });

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        Button btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        intent = getIntent();
        String strCell = intent.getStringExtra("cell");
        if(strCell != null){
            btn_delete.setVisibility(View.VISIBLE);
            bloodCell = gson.fromJson(strCell,BloodCell.class);
            txt_cell_name.setText(bloodCell.getName());
            txt_cell_name.setEnabled(false);
            txt_short_name.setText(bloodCell.getShortname());
            String base64Image = bloodCell.getImg().split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img_photo.setImageBitmap(decodedByte);
            base64converted = bloodCell.getImg();
        }
    }

    private void confirmDelete() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Delete Reference");
        dialog.setMessage("Are you sure you wan't to delete this reference?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                api.deleteCell(bloodCell.getName(), new CallbackWithResponse() {
                    @Override
                    public void execute(JSONObject response) {
                        refreshReferences();
                    }
                });
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void validateForm() {
        if(txt_cell_name.getText().length() == 0){
            tl_cell_name.setError("Cell name is required");
        }

        if(txt_short_name.getText().length() == 0){
            tl_short_name.setError("Cell short name is required");
        }

        if(base64converted == null){
            lbl_photo.setText("Photo is required");
            lbl_photo.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        if(txt_short_name.getText().length() > 0 && txt_cell_name.getText().length() > 0 && base64converted != null){
            performSave();
        }
    }

    private void performSave() {

        if(bloodCell == null){
            api.registerCell(new BloodCell(txt_cell_name.getText().toString(), txt_short_name.getText().toString(),"data:image/jpeg;base64,"+base64converted ), new CallbackWithResponse() {
                @Override
                public void execute(JSONObject response) {
                    refreshReferences();
                }
            });
        }else{
            api.updateCell(new BloodCell(bloodCell.getName(), txt_short_name.getText().toString(), "data:image/jpeg;base64," + base64converted), new CallbackWithResponse() {
                @Override
                public void execute(JSONObject response) {
                    refreshReferences();
                }
            });
        }
    }

    private void refreshReferences() {
        api.getCells(new CallbackWithResponse() {
            @Override
            public void execute(JSONObject response) {
                BloodCellCollection collection = gson.fromJson(response.toString(),BloodCellCollection.class);
                session.setCellCollection(collection);
                finish();
            }
        });
    }

    private void performSpeak() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            tts.speak(txt_short_name.getText().toString(), TextToSpeech.QUEUE_ADD,null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap bitmap = getResizedBitmap(imageBitmap,100);
            img_photo.setImageBitmap(bitmap);
            performBase64Convertion(bitmap);
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void performBase64Convertion(final Bitmap imageBitmap) {
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] b = baos.toByteArray();

                return Base64.encodeToString(b,Base64.NO_WRAP);
            }

            @Override
            protected void onPostExecute(String s) {
                base64converted = s;
                super.onPostExecute(s);
            }
        }.execute();
    }
}

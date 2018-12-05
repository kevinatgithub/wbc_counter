package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.ATYPICAL_LYMPHOCYTE;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.BAND;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.BASOPHIL;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.EOSINOPHIL;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.LYMPHOBLAST;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.LYMPHOCYTE;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.METAMYELOCYTE;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.MONOCYTE;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.MYELOBLAST;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.MYELOCYTE;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.NEUTROPHIL;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.NRBC;
import static com.bobongmd.app.a1cytewbcdifferentialcounter.AppConstants.PROMYELOCYTE;

public class Counter extends AppCompatActivity {

    protected TextToSpeech tts;
    protected Vibrator vibrator;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

            }
        });
        tts.setLanguage(Locale.US);
        tts.setSpeechRate(2f);


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    protected void doVibrate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(100);
        }
    }

    protected int getCellPicId(String cell){
        if(cell.toUpperCase().equals(ATYPICAL_LYMPHOCYTE.getName())){
            return R.drawable.atypical_lymphocyte;
        }else if(cell.toUpperCase().equals(BAND.getName())){
            return R.drawable.band;
        }else if(cell.toUpperCase().equals(BASOPHIL.getName())){
            return R.drawable.basophil;
        }else if(cell.toUpperCase().equals(EOSINOPHIL.getName())){
            return R.drawable.eosinophil;
        }else if(cell.toUpperCase().equals(LYMPHOBLAST.getName())){
            return R.drawable.lymphoblast;
        }else if(cell.toUpperCase().equals(LYMPHOCYTE.getName())){
            return R.drawable.lymphocyte;
        }else if(cell.toUpperCase().equals(METAMYELOCYTE.getName())){
            return R.drawable.metamyelocyte;
        }else if(cell.toUpperCase().equals(MONOCYTE.getName())){
            return R.drawable.monocyte;
        }else if(cell.toUpperCase().equals(MYELOBLAST.getName())){
            return R.drawable.myeloblast;
        }else if(cell.toUpperCase().equals(MYELOCYTE.getName())){
            return R.drawable.myelocyte;
        }else if(cell.toUpperCase().equals(NEUTROPHIL.getName())){
            return R.drawable.neutrophil;
        }else if(cell.toUpperCase().equals(NRBC.getName())){
            return R.drawable.nrbc;
        }else if(cell.toUpperCase().equals(PROMYELOCYTE.getName())){
            return R.drawable.promyelocyte;
        }
        return R.drawable.ic_warning;
    }
}

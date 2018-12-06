package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class Counter extends AppCompatActivity implements View.OnClickListener {

    protected TextToSpeech tts;
    protected Vibrator vibrator;
    protected DifferntialCount differntialCount;
    protected ArrayList<History> history = new ArrayList<>();
    protected int taps = 0;
    protected TextView lbl_status;
    protected TextView lbl_last;
    protected Button btn_result;
    protected Patient patient;
    protected Session session;
    protected ApiCallManager api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new Session(this);
        api = new ApiCallManager(this);
        patient = session.getPatient();
        differntialCount = session.getDifferentialCount();

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

    protected void setPatientNameAndDiagnosis(){
        TextView lbl_patient_name = findViewById(R.id.lbl_patient_id);
        lbl_patient_name.setText(patient.getFirstname() + " " + patient.getSurname());
        TextView lbl_diagnosis = findViewById(R.id.lbl_diagnosis);
        lbl_diagnosis.setText(patient.getDiagnosis());
    }

    protected void doVibrate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(100);
        }
    }

    protected Bitmap getCellImageAsBitmap(BloodCell cell){
        String base64Image = cell.getImg().split(",")[1];
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    protected void highlight(int layoutId){
        int[] ids = new int[]{R.id.card1,R.id.card2,R.id.card3,R.id.card4,R.id.card5,R.id.card6,R.id.card7,R.id.card8,R.id.card9,R.id.card10,R.id.card11,R.id.card12};
        for(int id:ids){
            LinearLayout ll = findViewById(id);
            if(ll != null){
                ll.setBackgroundColor(getResources().getColor(R.color.buttonDefault));
            }
        }
        LinearLayout l = findViewById(layoutId);
        if(l != null){
            l.setBackgroundColor(getResources().getColor(R.color.hightlight));
        }
    }

    protected void adjustCardLabels() {
        int[] ids = new int[]{
                R.id.lbl1,R.id.lbl2,R.id.lbl3,R.id.lbl4,R.id.lbl5,R.id.lbl6,
                R.id.lbl7,R.id.lbl8,R.id.lbl9,R.id.lbl10,R.id.lbl11,R.id.lbl12
        };
        DifferentialCountResult[] results = differntialCount.getCellsForCounting();
        int i = 0;
        for(int id:ids){
            if(i < results.length){
                String cellName = results[i].getBloodCell().getName();
                setCardLabel(id,cellName);
            }
            i++;
        }
    }

    private void setCardLabel(int id, String cellName) {
        TextView lbl = findViewById(id);
        if(lbl != null){
            lbl.setText(cellName);
        }
    }

    protected void adjustImageResources() {
        int[] ids = new int[]{
                R.id.img1,R.id.img2,R.id.img3,R.id.img4,R.id.img5,R.id.img6,
                R.id.img7,R.id.img8,R.id.img9,R.id.img10,R.id.img11,R.id.img12
        };

        DifferentialCountResult[] results = differntialCount.getCellsForCounting();
        int i = 0;
        for(int id: ids){
            if(i < results.length){
                BloodCell bloodCell = results[i].getBloodCell();
                setImageResource(id,bloodCell);
            }
            i++;
        }

    }

    private void setImageResource(int id, BloodCell bloodCell) {
        ImageView img = findViewById(id);
        if(img != null){
            img.setImageBitmap(getCellImageAsBitmap(bloodCell));
        }

    }

    protected void applyOnClickListenersToCard() {
        int[] ids = new int[]{
                R.id.btn_cell1,R.id.btn_cell2,R.id.btn_cell3,R.id.btn_cell4,R.id.btn_cell5,R.id.btn_cell6,
                R.id.btn_cell7,R.id.btn_cell8,R.id.btn_cell9,R.id.btn_cell10,R.id.btn_cell11,R.id.btn_cell12
        };

        for(int id: ids){
            applyOnClickToCard(id);
        }
    }

    private void applyOnClickToCard(int btnId){
        CardView btn_cell = findViewById(btnId);
        if(btn_cell != null){
            btn_cell.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int max = Integer.parseInt(differntialCount.getCellsToBeCounted());
        if(taps < max){

            int viewId = R.id.card1;
            switch (view.getId()){
                case R.id.btn_cell1:
                    viewId = R.id.card1;
                    prepareCellTapEffect(R.id.card1,R.id.lbl_counter1,0);
                    break;
                case R.id.btn_cell2:
                    viewId = R.id.card2;
                    prepareCellTapEffect(R.id.card2,R.id.lbl_counter2,1);
                    break;
                case R.id.btn_cell3:
                    viewId = R.id.card3;
                    prepareCellTapEffect(R.id.card3,R.id.lbl_counter3,2);
                    break;
                case R.id.btn_cell4:
                    viewId = R.id.card4;
                    prepareCellTapEffect(R.id.card4,R.id.lbl_counter4,3);
                    break;
                case R.id.btn_cell5:
                    viewId = R.id.card5;
                    prepareCellTapEffect(R.id.card5,R.id.lbl_counter5,4);
                    break;
                case R.id.btn_cell6:
                    viewId = R.id.card6;
                    prepareCellTapEffect(R.id.card6,R.id.lbl_counter6,5);
                    break;
                case R.id.btn_cell7:
                    viewId = R.id.card7;
                    prepareCellTapEffect(R.id.card7,R.id.lbl_counter7,6);
                    break;
                case R.id.btn_cell8:
                    viewId = R.id.card8;
                    prepareCellTapEffect(R.id.card8,R.id.lbl_counter8,7);
                    break;
                case R.id.btn_cell9:
                    viewId = R.id.card9;
                    prepareCellTapEffect(R.id.card9,R.id.lbl_counter9,8);
                    break;
                case R.id.btn_cell10:
                    viewId = R.id.card10;
                    prepareCellTapEffect(R.id.card10,R.id.lbl_counter10,9);
                    break;
                case R.id.btn_cell11:
                    viewId = R.id.card11;
                    prepareCellTapEffect(R.id.card11,R.id.lbl_counter11,10);
                    break;
                case R.id.btn_cell12:
                    viewId = R.id.card12;
                    prepareCellTapEffect(R.id.card12,R.id.lbl_counter12,11);
                    break;
            }
            history.add(new History(lbl_last.getText().toString(),viewId));
            taps++;
            lbl_status.setText(taps + "/" + max);
        }
        if(taps >= max){
            btn_result.setVisibility(View.VISIBLE);
        }
    }

    protected void prepareCellTapEffect(int cardId,int labelId,int index){
        DifferentialCountResult[] diffResults = differntialCount.getCellsForCounting();
        highlight(cardId);
        diffResults[index].setCountValue(diffResults[index].getCountValue()+1);
        differntialCount.setCellsForCounting(diffResults);
        lbl_last.setText(diffResults[index].getBloodCell().getName().toUpperCase());
        showCellTapEffects(labelId,diffResults[index].getBloodCell().getShortname());
    }

    private void showCellTapEffects(int counter_id,String cell){
        TextView c = findViewById(counter_id);
        int n = Integer.parseInt(c.getText().toString());
        n++;
        c.setText(new Integer(n).toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            tts.speak(cell, TextToSpeech.QUEUE_ADD,null);
        }
        doVibrate();
    }

    protected class History{
        String cellName;
        int viewId;

        public History(String cellName, int viewId) {
            this.cellName = cellName;
            this.viewId = viewId;
        }
    }

    protected void undo() {
        DifferentialCountResult[] diffResults = differntialCount.getCellsForCounting();

        int lastIndex = history.size()-1;

        if(lastIndex<0){
            return;
        }

        History last = history.get(lastIndex);

        int ids[] = new int[]{
                R.id.lbl_counter1,R.id.lbl_counter2,R.id.lbl_counter3,R.id.lbl_counter4,R.id.lbl_counter5,R.id.lbl_counter6,
                R.id.lbl_counter7,R.id.lbl_counter8,R.id.lbl_counter9,R.id.lbl_counter10,R.id.lbl_counter11,R.id.lbl_counter12
        };

        int i = 0;
        for(int id:ids){
            if(i < diffResults.length){
                if(last.cellName == diffResults[i].getBloodCell().getName()){
                    performUndoEffect(i,id);
                }
            }
            i++;
        }
    }

    private void performUndoEffect(int index,int lbl_id) {
        DifferentialCountResult[] diffResults = differntialCount.getCellsForCounting();
        diffResults[index].setCountValue(diffResults[index].getCountValue()-1);
        differntialCount.setCellsForCounting(diffResults);

        TextView c = findViewById(lbl_id);
        int n = Integer.parseInt(c.getText().toString());
        n--;
        c.setText(new Integer(n).toString());

        taps--;
        int max = Integer.parseInt(differntialCount.getCellsToBeCounted());
        lbl_status.setText(taps + "/" + max);
        int last = history.size()-1;
        if(last>=0){
            History h = history.get(last);
            highlight(h.viewId);
            lbl_last.setText(h.cellName);
            history.remove(last);
        }
    }

    protected void implementFABClicks(){
        FloatingActionButton fab_close = findViewById(R.id.fab_close);
        fab_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        FloatingActionButton fab_reference = findViewById(R.id.fab_reference);
        fab_reference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BloodCellReferences.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab_undo = findViewById(R.id.fab_undo);
        fab_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(history.size()> 0){
                    undo();
                }
            }
        });
    }

    protected void endCount(){
        if(patient.getDifferntialCounts() == null){
            patient.setDifferntialCounts(new ArrayList<DifferntialCount>());
        }
        for(DifferentialCountResult result: differntialCount.getCellsForCounting()){
            result.getBloodCell().setImg(null);
        }
        patient.getDifferntialCounts().add(differntialCount);
        session.setPatient(patient);
        session.setDifferentialCount(differntialCount);
        api.updatePatient(patient, new CallbackWithResponse() {
            @Override
            public void execute(JSONObject response) {
                Intent intent = new Intent(getApplicationContext(),CountResult.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

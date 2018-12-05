package com.bobongmd.app.a1cytewbcdifferentialcounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultRowAdapter extends ArrayAdapter<ResultItem> {

    public ResultRowAdapter(@NonNull Context context, ArrayList<ResultItem> results) {
        super(context, 0, results);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ResultItem result = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_row, parent,false);
        }

        TextView lbl_cell = convertView.findViewById(R.id.lbl_cell);
        TextView lbl_count = convertView.findViewById(R.id.lbl_count);
        TextView lbl_percentage = convertView.findViewById(R.id.lbl_percentage);
        TextView lbl_absolute = convertView.findViewById(R.id.lbl_absolute);

        lbl_cell.setText(result.getCell());
        lbl_count.setText(result.getCountValue() + "");
        lbl_percentage.setText(result.getPercentage() + "%");
        lbl_absolute.setText(result.getAbsolute()+"");

        return convertView;
    }
}

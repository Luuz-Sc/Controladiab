package com.example.controladiab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class AlarmAdapter extends ArrayAdapter<String> {

    public AlarmAdapter(Context context, List<String> alarms) {
        super(context, 0, alarms);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        String alarm = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(alarm);

        return convertView;
    }
}


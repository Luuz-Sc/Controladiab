package com.example.controladiab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

public class RecordatorioActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private ArrayList<String> recordatorios;
    private ArrayAdapter<String> adapter;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        Button btnGuardarRecordatorio = findViewById(R.id.btnGuardarRecordatorio);
        Button btnModificarCita = findViewById(R.id.btnModificarCita);
        Button btnEliminarCita = findViewById(R.id.btnEliminarCita);
        Button btnRegresar = findViewById(R.id.btnRegresar);
        ListView listView = findViewById(R.id.listView);

        timePicker.setIs24HourView(true);

        recordatorios = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recordatorios);
        listView.setAdapter(adapter);

        cargarRecordatorios(); // Cargar recordatorios guardados

        btnGuardarRecordatorio.setOnClickListener(v -> guardarRecordatorio());
        btnModificarCita.setOnClickListener(v -> modificarCita());
        btnEliminarCita.setOnClickListener(v -> eliminarCita());
        btnRegresar.setOnClickListener(v -> finish());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            Toast.makeText(this, "Seleccionaste el recordatorio: " + recordatorios.get(position), Toast.LENGTH_SHORT).show();
        });
    }

    private void guardarRecordatorio() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String recordatorio = String.format(Locale.getDefault(),
                "Fecha: %02d/%02d/%d Hora: %02d:%02d", day, month, year, hour, minute);

        recordatorios.add(recordatorio);
        adapter.notifyDataSetChanged();
        guardarListaEnSharedPreferences();

        Toast.makeText(this, "Recordatorio guardado", Toast.LENGTH_SHORT).show();
    }

    private void modificarCita() {
        if (selectedIndex == -1) {
            Toast.makeText(this, "Por favor selecciona un recordatorio para modificar", Toast.LENGTH_SHORT).show();
            return;
        }

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String nuevoRecordatorio = String.format(Locale.getDefault(),
                "Fecha: %02d/%02d/%d Hora: %02d:%02d", day, month, year, hour, minute);

        recordatorios.set(selectedIndex, nuevoRecordatorio);
        adapter.notifyDataSetChanged();
        guardarListaEnSharedPreferences();

        Toast.makeText(this, "Recordatorio modificado", Toast.LENGTH_SHORT).show();
    }

    private void eliminarCita() {
        if (selectedIndex == -1) {
            Toast.makeText(this, "Por favor selecciona un recordatorio para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        recordatorios.remove(selectedIndex);
        adapter.notifyDataSetChanged();
        guardarListaEnSharedPreferences();
        selectedIndex = -1;

        Toast.makeText(this, "Recordatorio eliminado", Toast.LENGTH_SHORT).show();
    }

    private void guardarListaEnSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("recordatorios_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray(recordatorios);
        editor.putString("recordatorios", jsonArray.toString());
        editor.apply();
    }

    private void cargarRecordatorios() {
        SharedPreferences prefs = getSharedPreferences("recordatorios_prefs", MODE_PRIVATE);
        String json = prefs.getString("recordatorios", null);
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                recordatorios.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    recordatorios.add(jsonArray.getString(i));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e("RecordatorioActivity", "Error al procesar JSON", e);
            }
        }
    }
}

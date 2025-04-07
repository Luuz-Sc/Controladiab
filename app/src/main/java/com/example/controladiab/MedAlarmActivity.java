package com.example.controladiab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Calendar;

public class MedAlarmActivity extends AppCompatActivity {

    private EditText etMedicationName;
    private TimePicker timePicker;
    private CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;

    private final ArrayList<String> alarmList = new ArrayList<>();
    private AlarmAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_alarm);

        etMedicationName = findViewById(R.id.etMedicationName);
        timePicker = findViewById(R.id.timePicker);
        lunes = findViewById(R.id.lunes);
        martes = findViewById(R.id.martes);
        miercoles = findViewById(R.id.miercoles);
        jueves = findViewById(R.id.jueves);
        viernes = findViewById(R.id.viernes);
        sabado = findViewById(R.id.sabado);
        domingo = findViewById(R.id.domingo);
        Button btnSetAlarm = findViewById(R.id.btnSetAlarm);
        Button btnDeleteAlarm = findViewById(R.id.btnDeleteAlarm);
        Button btnModifyAlarm = findViewById(R.id.btnModifyAlarm);
        Button btnBack = findViewById(R.id.btnBack);
        ListView alarmListView = findViewById(R.id.alarmListView);

        alarmAdapter = new AlarmAdapter(this, alarmList);
        alarmListView.setAdapter(alarmAdapter);

        btnSetAlarm.setOnClickListener(v -> setAlarm());
        btnDeleteAlarm.setOnClickListener(v -> deleteAlarm());
        btnModifyAlarm.setOnClickListener(v -> modifyAlarm());
        btnBack.setOnClickListener(v -> finish());
    }

    private void setAlarm() {
        String medication = etMedicationName.getText().toString();
        if (medication.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el nombre del medicamento", Toast.LENGTH_SHORT).show();
            return;
        }

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Obtener los días seleccionados
        String daysSelected = getSelectedDays();

        // Configurar la alarma
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);


        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("medicationName", medication);
        intent.putExtra("daysSelected", daysSelected);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        alarmList.add("Alarma para " + medication + " a las " + hour + ":" + minute + " los días: " + daysSelected);
        alarmAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Alarma configurada", Toast.LENGTH_SHORT).show();
    }

    private void deleteAlarm() {

        if (!alarmList.isEmpty()) {
            alarmList.remove(alarmList.size() - 1);
            alarmAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Alarma eliminada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay alarmas para eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private void modifyAlarm() {

        Toast.makeText(this, "Modificar alarmas aún no implementado", Toast.LENGTH_SHORT).show();
    }

    private String getSelectedDays() {
        StringBuilder selectedDays = new StringBuilder();
        if (lunes.isChecked()) selectedDays.append("Lunes, ");
        if (martes.isChecked()) selectedDays.append("Martes, ");
        if (miercoles.isChecked()) selectedDays.append("Miércoles, ");
        if (jueves.isChecked()) selectedDays.append("Jueves, ");
        if (viernes.isChecked()) selectedDays.append("Viernes, ");
        if (sabado.isChecked()) selectedDays.append("Sábado, ");
        if (domingo.isChecked()) selectedDays.append("Domingo, ");

        if (selectedDays.length() > 0) {
            selectedDays.delete(selectedDays.length() - 2, selectedDays.length());
        } else {
            selectedDays.append("Ningún día seleccionado");
        }

        return selectedDays.toString();
    }
}

package com.example.controladiab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainPageActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);


        ImageButton btnControlGlucosa = findViewById(R.id.btn_control_glucosa);
        btnControlGlucosa.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, ControlGlucosaActivity.class);
            startActivity(intent);
        });

        ImageButton btnMedAlarm = findViewById(R.id.btn_med_alarm);
        btnMedAlarm.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, MedAlarmActivity.class);
            startActivity(intent);
        });

        ImageButton btnRegisterAlimentos = findViewById(R.id.btn_register_alimentos);
        btnRegisterAlimentos.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, RegistroAlimentosActivity.class);
            startActivity(intent);
        });

        ImageButton btnActividadFisica = findViewById(R.id.btn_actividad_fisica);
        btnActividadFisica.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, InfoDiabetesActivity.class);
            startActivity(intent);
        });

        ImageButton btnCalendario = findViewById(R.id.btn_calendario);
        btnCalendario.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, RecordatorioActivity.class);
            startActivity(intent);
        });

        ImageButton btnJarraBuenBeber = findViewById(R.id.btn_jarra_buen_beber);
        btnJarraBuenBeber.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, JarraDelBuenBeberActivity.class);
            startActivity(intent);
        });

        Button btnRegistraDatos = findViewById(R.id.btn_registra_datos);
        btnRegistraDatos.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, RegistroDatosActivity.class);
            startActivity(intent);
        });
    }


    private void cerrarSesion() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(MainPageActivity.this, MainActivityLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            cerrarSesion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

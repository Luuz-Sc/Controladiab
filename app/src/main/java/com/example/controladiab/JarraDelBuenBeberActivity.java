package com.example.controladiab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class JarraDelBuenBeberActivity extends AppCompatActivity {

    private static final int TOTAL_VASOS = 8;
    private int vasosRestantes = TOTAL_VASOS;
    private int vasosTomados = 0;

    private ImageView imgJarra;
    private TextView txtLitrosFaltantes, txtVasosTomados, txtHistorial;

    private SharedPreferences preferences;
    private ArrayList<String> historico;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jarra_del_buen_beber);


        Toolbar toolbar = findViewById(R.id.top_app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Controladiab - Jarra del Buen Beber");
        }

        preferences = getSharedPreferences("JarraPreferences", MODE_PRIVATE);
        historico = new ArrayList<>();
        cargarHistorial();


        checkAndResetCycle();

        imgJarra = findViewById(R.id.img_jarra);
        txtLitrosFaltantes = findViewById(R.id.txt_litros_faltantes);
        txtVasosTomados = findViewById(R.id.txt_vasos_tomados);
        txtHistorial = findViewById(R.id.txt_historial);

        Button btnAgregarAgua = findViewById(R.id.btn_agregar_agua);
        Button btnReiniciar = findViewById(R.id.btn_reiniciar);
        Button btnRegresar = findViewById(R.id.btn_regresar);
        Button btnLimpiarHistorial = findViewById(R.id.btn_limpiar_Historial);

        actualizarTextoLitros();
        actualizarTextoHistorial();


        btnAgregarAgua.setOnClickListener(v -> {
            if (vasosRestantes > 0) {
                vasosRestantes--;
                vasosTomados++;
                guardarEstadoVasos();
                actualizarTextoLitros();


                if (vasosRestantes == 0) {
                    agregarAlHistorial("Meta cumplida: 2 litros (" + obtenerFechaActual() + ")");
                    actualizarTextoHistorial();
                }
            }
        });


        btnReiniciar.setOnClickListener(v -> {
            vasosRestantes = TOTAL_VASOS;
            vasosTomados = 0;
            guardarEstadoVasos();
            actualizarTextoLitros();
        });


        btnRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(JarraDelBuenBeberActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
        });


        btnLimpiarHistorial.setOnClickListener(v -> {
            historico.clear();
            guardarHistorial();
            actualizarTextoHistorial();
        });
    }


    private void actualizarTextoLitros() {
        txtLitrosFaltantes.setText(getString(R.string.txt_faltan_litros, vasosRestantes));
        txtVasosTomados.setText(getString(R.string.txt_vasos_tomados, vasosTomados));

        if (vasosRestantes == 0) {
            imgJarra.setImageResource(R.drawable.jarra_vacia);
        } else {
            imgJarra.setImageResource(R.drawable.jarra_llena);
        }
    }


    private void actualizarTextoHistorial() {
        StringBuilder builder = new StringBuilder();
        for (String log : historico) {
            builder.append(log).append("\n");
        }
        txtHistorial.setText(builder.toString().trim());
    }


    private void agregarAlHistorial(String log) {
        historico.add(log);
        guardarHistorial();
    }


    private void guardarHistorial() {
        StringBuilder builder = new StringBuilder();
        for (String log : historico) {
            builder.append(log).append("\n");
        }
        preferences.edit().putString("historial", builder.toString().trim()).apply();
    }


    private void cargarHistorial() {
        String historialGuardado = preferences.getString("historial", "");
        if (!historialGuardado.isEmpty()) {
            String[] logs = historialGuardado.split("\n");
            historico.addAll(Arrays.asList(logs));
        }
    }


    private void checkAndResetCycle() {
        long ultimoReinicio = preferences.getLong("ultimoReinicio", 0);
        long ahora = Calendar.getInstance().getTimeInMillis();

        if ((ahora - ultimoReinicio) >= 24 * 60 * 60 * 1000) { // 24 horas
            vasosRestantes = TOTAL_VASOS;
            vasosTomados = 0;
            guardarEstadoVasos();
            preferences.edit().putLong("ultimoReinicio", ahora).apply();
        } else {
            vasosRestantes = preferences.getInt("vasosRestantes", TOTAL_VASOS);
            vasosTomados = preferences.getInt("vasosTomados", 0);
        }
    }


    private void guardarEstadoVasos() {
        preferences.edit()
                .putInt("vasosRestantes", vasosRestantes)
                .putInt("vasosTomados", vasosTomados)
                .apply();
    }


    private String obtenerFechaActual() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }
}

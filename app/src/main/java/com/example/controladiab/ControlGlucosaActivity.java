package com.example.controladiab;

import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ControlGlucosaActivity extends AppCompatActivity {

    private EditText etNivelGlucosa;
    private TextView tvResultadoGlucosa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_glucosa);

        etNivelGlucosa = findViewById(R.id.etNivelGlucosa);
        Button btnRegistrarGlucosa = findViewById(R.id.btnRegistrarGlucosa);
        Button btnConsultarGlucosa = findViewById(R.id.btnConsultar);
        Button btnRegresar = findViewById(R.id.btnRegresar);
        tvResultadoGlucosa = findViewById(R.id.tvResultadoGlucosa);

        btnRegistrarGlucosa.setOnClickListener(this::onClick);
        btnConsultarGlucosa.setOnClickListener(v -> consultarGlucosa());
        btnRegresar.setOnClickListener(v -> regresarAPaginaPrincipal());
    }

    private void onClick(View v) {
        String nivelGlucosaStr = etNivelGlucosa.getText().toString().trim();

        if (!nivelGlucosaStr.isEmpty()) {
            try {
                int nivelGlucosa = Integer.parseInt(nivelGlucosaStr);
                guardarNivelGlucosa(nivelGlucosa);
                mostrarResultado(nivelGlucosa);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor, ingresa un número válido.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, ingresa tu nivel de glucosa.", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarResultado(int nivelGlucosa) {
        String resultado;
        if (nivelGlucosa < 70) {
            resultado = "Nivel de glucosa: MUY BAJO.\n\nRecomendación: Consume algo con azúcar inmediatamente y consulta a un médico.";
        } else if (nivelGlucosa <= 99) {
            resultado = "Nivel de glucosa: NORMAL.\n\nTu glucosa está dentro de los valores recomendados.";
        } else if (nivelGlucosa <= 125) {
            resultado = "Nivel de glucosa: INTERMEDIO (PRE-DIABETES).\n\nRecomendación: Consulta a un médico para prevenir complicaciones.";
        } else {
            resultado = "Nivel de glucosa: ALTO (DIABETES).\n\nRecomendación: Es importante que consultes a un médico para tratamiento adecuado.";
        }

        tvResultadoGlucosa.setText(resultado);
    }

    private void guardarNivelGlucosa(int nivelGlucosa) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);

        try {
            long resultado = dbHelper.insertarNivelGlucosa(nivelGlucosa);

            if (resultado != -1) {
                Toast.makeText(this, "Nivel de glucosa registrado con éxito.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al registrar el nivel de glucosa.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrió un error al guardar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }





    private void consultarGlucosa() {
        try (DatabaseHelper dbHelper = new DatabaseHelper(this);
             SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM Glucosa ORDER BY fecha_registro DESC LIMIT 1", null)) {

            if (cursor != null && cursor.moveToFirst()) {
                int nivelGlucosaIndex = cursor.getColumnIndex("nivel_glucosa");
                int fechaRegistroIndex = cursor.getColumnIndex("fecha_registro");

                if (nivelGlucosaIndex != -1 && fechaRegistroIndex != -1) {
                    int nivelGlucosa = cursor.getInt(nivelGlucosaIndex);
                    String fechaRegistro = cursor.getString(fechaRegistroIndex);

                    String resultadoConsulta = "Nivel de glucosa: " + nivelGlucosa + "\nFecha: " + fechaRegistro;
                    tvResultadoGlucosa.setText(resultadoConsulta);
                } else {
                    Toast.makeText(this, "Error al leer los datos.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No hay registros para mostrar.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al consultar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void regresarAPaginaPrincipal() {
        Intent intent = new Intent(ControlGlucosaActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }
}


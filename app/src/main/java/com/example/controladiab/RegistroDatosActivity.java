package com.example.controladiab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroDatosActivity extends AppCompatActivity {

    private EditText etNombreCompleto, etEdad, etFechaUltimaPrueba, etPeso, etAltura;
    private TextView tvIMCResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_datos);


        etNombreCompleto = findViewById(R.id.etNombreCompleto);
        etEdad = findViewById(R.id.etEdad);
        etFechaUltimaPrueba = findViewById(R.id.etFechaUltimaPrueba);
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        tvIMCResult = findViewById(R.id.tvIMCResult);

        Spinner spinnerGenero = findViewById(R.id.spinnerGenero);
        Spinner spinnerTipoDiabetes = findViewById(R.id.spinnerTipoDiabetes);

        Button btnCalcularIMC = findViewById(R.id.btnCalcularIMC);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnRegresarPrincipal = findViewById(R.id.btnRegresarPrincipal);


        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(this,
                R.array.generos, android.R.layout.simple_spinner_item);
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapterGenero);

        ArrayAdapter<CharSequence> adapterTipoDiabetes = ArrayAdapter.createFromResource(this,
                R.array.tipos_diabetes, android.R.layout.simple_spinner_item);
        adapterTipoDiabetes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoDiabetes.setAdapter(adapterTipoDiabetes);


        btnCalcularIMC.setOnClickListener(v -> calcularIMC());
        btnRegistrar.setOnClickListener(v -> registrarDatos());
        btnRegresarPrincipal.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroDatosActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("DefaultLocale")
    private void calcularIMC() {
        String pesoStr = etPeso.getText().toString().trim();
        String alturaStr = etAltura.getText().toString().trim();

        if (TextUtils.isEmpty(pesoStr) || TextUtils.isEmpty(alturaStr)) {
            Toast.makeText(this, "Por favor, ingrese peso y altura.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            if (peso <= 0 || altura <= 0) {
                Toast.makeText(this, "Peso y altura deben ser valores positivos.", Toast.LENGTH_SHORT).show();
                return;
            }

            double imc = peso / (altura * altura);
            tvIMCResult.setText(String.format("Tu IMC es: %.2f", imc));
            tvIMCResult.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Peso y altura deben ser valores numéricos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void registrarDatos() {
        String nombre = etNombreCompleto.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();
        String pesoStr = etPeso.getText().toString().trim();
        String alturaStr = etAltura.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(edadStr) || TextUtils.isEmpty(pesoStr) || TextUtils.isEmpty(alturaStr)) {
            Toast.makeText(this, "Por favor, complete todos los campos obligatorios.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            if (edad <= 0 || peso <= 0 || altura <= 0) {
                Toast.makeText(this, "Edad, peso y altura deben ser valores positivos.", Toast.LENGTH_SHORT).show();
                return;
            }


            DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
            dbHelper.guardarDatosUsuario("123", "Tipo 1", 22.5, 30, 110.0, 70.0, 1.75, "M", "2024-01-13", this);



            Toast.makeText(this, "Datos registrados correctamente", Toast.LENGTH_LONG).show();
            limpiarCampos();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad, peso y altura deben ser valores numéricos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etNombreCompleto.setText("");
        etEdad.setText("");
        etFechaUltimaPrueba.setText("");
        etPeso.setText("");
        etAltura.setText("");
        tvIMCResult.setVisibility(View.GONE);
    }
}

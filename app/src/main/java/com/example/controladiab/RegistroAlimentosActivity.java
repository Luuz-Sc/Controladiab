package com.example.controladiab;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class RegistroAlimentosActivity extends AppCompatActivity {

    private CheckBox arrozCheckBox, avenaCheckBox, maizCheckBox, trigoCheckBox;
    private CheckBox manzanaCheckBox, peraCheckBox, espinacaCheckBox, zanahoriaCheckBox, guayabaCheckBox;
    private CheckBox frijolCheckBox, lentejasCheckBox, polloCheckBox, pescadoCheckBox, carneResCheckBox;

    private EditText alergiasEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_alimentos);

        arrozCheckBox = findViewById(R.id.cbArroz);
        avenaCheckBox = findViewById(R.id.cbAvena);
        maizCheckBox = findViewById(R.id.cbMaiz);
        trigoCheckBox = findViewById(R.id.cbTrigo);

        manzanaCheckBox = findViewById(R.id.cbManzana);
        peraCheckBox = findViewById(R.id.cbPera);
        espinacaCheckBox = findViewById(R.id.cbEspinaca);
        zanahoriaCheckBox = findViewById(R.id.cbZanahoria);
        guayabaCheckBox = findViewById(R.id.cbGuayaba);

        frijolCheckBox = findViewById(R.id.cbFrijol);
        lentejasCheckBox = findViewById(R.id.cbLentejas);
        polloCheckBox = findViewById(R.id.cbPollo);
        pescadoCheckBox = findViewById(R.id.cbPescado);
        carneResCheckBox = findViewById(R.id.cbCarneDeRes);

        alergiasEditText = findViewById(R.id.etAlergias);
        Button btnGenerarDieta = findViewById(R.id.btnGenerarDieta);
        Button btnRegresar = findViewById(R.id.btnRegresarAlimentos);
        Button btnGuardar = findViewById(R.id.btnGuardarAlimentos);

        btnGenerarDieta.setOnClickListener(v -> generarRecomendacion());

        btnRegresar.setOnClickListener(v -> finish());


        btnGuardar.setOnClickListener(v -> Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show());
    }

    private void generarRecomendacion() {
        List<String> seleccionados = obtenerAlimentosSeleccionados();
        List<String> alimentosAlergias = obtenerAlergias();
        Map<String, List<String>> sugerencias = crearSugerencias();

        List<String> dietaGenerada = new ArrayList<>();
        Random random = new Random();

        for (Map.Entry<String, List<String>> entrada : sugerencias.entrySet()) {
            String tiempoComida = entrada.getKey();
            List<String> opciones = entrada.getValue().stream()
                    .filter(opcion -> seleccionados.stream().anyMatch(alimento -> opcion.toLowerCase().contains(alimento.toLowerCase())))
                    .filter(opcion -> alimentosAlergias.stream().noneMatch(alergia -> opcion.toLowerCase().contains(alergia.toLowerCase())))
                    .collect(Collectors.toList());

            if (!opciones.isEmpty()) {
                String opcionSeleccionada = opciones.get(random.nextInt(opciones.size()));
                dietaGenerada.add(tiempoComida + ": " + opcionSeleccionada);
            }
        }

        if (!dietaGenerada.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Plan de Dieta")
                    .setMessage(String.join("\n", dietaGenerada) + "\n\nNota: Estas recomendaciones no sustituyen la consulta con un profesional de la salud. Consulte a su médico.")
                    .setPositiveButton("Aceptar", null)
                    .create()
                    .show();
        } else {
            Toast.makeText(this, "No hay recomendaciones disponibles para los alimentos seleccionados.", Toast.LENGTH_LONG).show();
        }
    }

    private List<String> obtenerAlimentosSeleccionados() {
        List<String> seleccionados = new ArrayList<>();
        if (arrozCheckBox.isChecked()) seleccionados.add("Arroz");
        if (avenaCheckBox.isChecked()) seleccionados.add("Avena");
        if (maizCheckBox.isChecked()) seleccionados.add("Maíz");
        if (trigoCheckBox.isChecked()) seleccionados.add("Trigo");

        if (manzanaCheckBox.isChecked()) seleccionados.add("Manzana");
        if (peraCheckBox.isChecked()) seleccionados.add("Pera");
        if (espinacaCheckBox.isChecked()) seleccionados.add("Espinaca");
        if (zanahoriaCheckBox.isChecked()) seleccionados.add("Zanahoria");
        if (guayabaCheckBox.isChecked()) seleccionados.add("Guayaba");
        if (guayabaCheckBox.isChecked()) seleccionados.add("Fresa");
        if (guayabaCheckBox.isChecked()) seleccionados.add("Mora");
        if (guayabaCheckBox.isChecked()) seleccionados.add("Mandarina");
        if (guayabaCheckBox.isChecked()) seleccionados.add("Arándanos");
        if (guayabaCheckBox.isChecked()) seleccionados.add("Naranja con bagazo");

        if (frijolCheckBox.isChecked()) seleccionados.add("Frijol");
        if (lentejasCheckBox.isChecked()) seleccionados.add("Lentejas");
        if (polloCheckBox.isChecked()) seleccionados.add("Pollo");
        if (pescadoCheckBox.isChecked()) seleccionados.add("Pescado");
        if (carneResCheckBox.isChecked()) seleccionados.add("Atun");
        if (carneResCheckBox.isChecked()) seleccionados.add("Carne de Res");

        return seleccionados;
    }

    private List<String> obtenerAlergias() {
        String alergiasText = alergiasEditText.getText().toString();
        List<String> alergias = new ArrayList<>();
        if (!alergiasText.isEmpty()) {
            String[] alergiasArray = alergiasText.split(",");
            for (String alergia : alergiasArray) {
                alergias.add(alergia.trim());
            }
        }
        return alergias;
    }

    private Map<String, List<String>> crearSugerencias() {
        Map<String, List<String>> sugerencias = new LinkedHashMap<>();
        sugerencias.put("Desayuno", List.of(
                "Avena con leche de almendras y manzana",
                "Tostadas de trigo con pera",
                "Huevos revueltos con espinaca y pan integral",
                "Panqueques de avena con arándanos",
                "Smoothie de zanahoria, manzana y jengibre",
                "Fresas con yogurth natural"
        ));
        sugerencias.put("Colación de la mañana", List.of(
                "Manzana con crema de cacahuate",
                "Yogur natural con avena",
                "Zanahorias baby",
                "Un puñado de almendras y una pera",
                "Pepinos con limón y chile en polvo",
                "Naranja con chile en polvo",
                "Moras con arandanos y yogurth natural"
        ));
        sugerencias.put("Comida", List.of(
                "Pollo a la plancha con arroz integral y espinacas",
                "Filete de pescado con ensalada de zanahoria y lentejas",
                "Sopa de verduras con pollo desmenuzado",
                "Sopa de frijoles con tortillas de maíz",
                "Lentejas guisadas con arroz y vegetales al vapor"
        ));
        sugerencias.put("Cena", List.of(
                "Ensalada de pollo con espinacas y fresas",
                "Tacos de pescado en tortilla de maíz",
                "Ensalada de quinoa con espinaca y zanahoria",
                "Pan integral con guacamole y huevo duro",
                "Yogur con frutos secos"
        ));
        return sugerencias;
    }
}

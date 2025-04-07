package com.example.controladiab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InfoDiabetesActivity extends AppCompatActivity {

    private WebView youtubeWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        Toolbar toolbar = findViewById(R.id.top_app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }


        youtubeWebView = findViewById(R.id.youtube_webview);
        configureWebView();


        Button infoButton = findViewById(R.id.info_button);
        infoButton.setOnClickListener(v -> showInfoDialog());


        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(InfoDiabetesActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView() {
        youtubeWebView.setWebViewClient(new WebViewClient());
        youtubeWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);


        String videoUrl = "https://www.youtube.com/watch?v=cIpPN3rKPaQ&t=192s";
        youtubeWebView.loadUrl(videoUrl);
    }

    private void showInfoDialog() {

        String infoText = "La diabetes es una enfermedad crónica que afecta la manera en que el cuerpo regula el azúcar (glucosa) en la sangre. " +
                "Es fundamental mantener un control adecuado para prevenir complicaciones.\n\n" +
                "Cómo cuidarse:\n" +
                "- Mantener una dieta equilibrada basada en alimentos saludables.\n" +
                "- Realizar actividad física regularmente.\n" +
                "- Medir los niveles de glucosa en sangre según lo indicado por un profesional de la salud.\n" +
                "- Tomar medicamentos o insulina, si son recetados, en el horario adecuado.\n" +
                "- Evitar el consumo de azúcares y carbohidratos refinados.";


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Qué es la diabetes y cómo cuidarse?");
        builder.setMessage(infoText);
        builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}

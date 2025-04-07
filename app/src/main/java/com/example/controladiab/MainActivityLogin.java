package com.example.controladiab;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityLogin extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnGoToRegister);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        userRepository = new UserRepository(this);

        btnLogin.setOnClickListener(this::loginUser);

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityLogin.this, RegisterActivity.class);
            startActivity(intent);
        });

        tvForgotPassword.setOnClickListener(v -> showForgotPasswordDialog());
    }

    private void loginUser(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingrese su correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userRepository.loginUser(email, password)) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recuperar Contraseña");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        input.setHint("Ingrese su correo");
        builder.setView(input);

        builder.setPositiveButton("Recuperar", (dialog, which) -> {
            String email = input.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
                return;
            }

            String password = userRepository.getPasswordByEmail(email);
            if (password != null) new AlertDialog.Builder(this)
                    .setTitle("Contraseña Recuperada")
                    .setMessage(getString(R.string.tu_contrase_a_es) + password)
                    .setPositiveButton("OK", null)
                    .show();
            else {
                Toast.makeText(this, "Correo no encontrado", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}

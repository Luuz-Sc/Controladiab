package com.example.controladiab;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmailRegister, etPasswordRegister;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        userRepository = new UserRepository(this);

        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = etEmailRegister.getText().toString().trim();
        String password = etPasswordRegister.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmailRegister.setError("El correo electrónico es obligatorio");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPasswordRegister.setError("La contraseña es obligatoria");
            return;
        }

        if (userRepository.registerUser(email, password)) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
        }
    }
}

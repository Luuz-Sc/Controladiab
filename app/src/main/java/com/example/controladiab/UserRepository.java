package com.example.controladiab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserRepository {

    private final DatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public boolean registerUser(String email, String password) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = null;

        try {
            // Verificar si el usuario ya existe
            String query = "SELECT 1 FROM users WHERE email = ?";
            cursor = database.rawQuery(query, new String[]{email});

            if (cursor.moveToFirst()) {
                return false; // Usuario ya registrado
            }

            // Insertar nuevo usuario
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_EMAIL, email);
            values.put(DatabaseHelper.COLUMN_PASSWORD, password);

            long result = database.insert(DatabaseHelper.TABLE_USERS, null, values);
            return result != -1;
        } catch (SQLException e) {
            Log.e("UserRepository", "Error al registrar el usuario", e);
            return false;
        } finally {
            if (cursor != null) cursor.close();
            database.close();
        }
    }


    public boolean loginUser(String email, String password) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT 1 FROM users WHERE email = ? AND password = ?";
            cursor = database.rawQuery(query, new String[]{email, password});

            return cursor.moveToFirst();
        } catch (SQLException e) {
            Log.e("UserRepository", "Error al iniciar sesión", e);
            return false;
        } finally {
            if (cursor != null) cursor.close();
            database.close();
        }
    }


    public String getPasswordByEmail(String email) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        String password = null;

        try {
            String query = "SELECT password FROM users WHERE email = ?";
            cursor = database.rawQuery(query, new String[]{email});

            if (cursor.moveToFirst()) {
                password = cursor.getString(0);
            }
        } catch (SQLException e) {
            Log.e("UserRepository", "Error al recuperar contraseña", e);
        } finally {
            if (cursor != null) cursor.close();
            database.close();
        }

        return password;
    }
}

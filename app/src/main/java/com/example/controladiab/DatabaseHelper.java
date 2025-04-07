package com.example.controladiab;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String COLUMN_NIVEL_GLUCOSA = "nivel_glucosa";
    private static final String DATABASE_NAME = "ControlDiabetes.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_GLUCOSA = "Glucosa";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FECHA_REGISTRO = "fecha_registro";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_ALIMENTOS = "Alimentos";
    public static final String COLUMN_TIPO = "tipo";
    public static final String COLUMN_NOMBRE = "nombre";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableGlucosa = "CREATE TABLE " + TABLE_GLUCOSA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NIVEL_GLUCOSA + " INTEGER NOT NULL, " +
                COLUMN_FECHA_REGISTRO + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(createTableGlucosa);

        String createTableUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COLUMN_PASSWORD + " TEXT NOT NULL" +
                ")";
        db.execSQL(createTableUsers);

        String createTableAlimentos = "CREATE TABLE " + TABLE_ALIMENTOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIPO + " TEXT NOT NULL, " +
                COLUMN_NOMBRE + " TEXT NOT NULL" +
                ")";
        db.execSQL(createTableAlimentos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            String createTableUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL" +
                    ")";
            db.execSQL(createTableUsers);
        }
        if (oldVersion < 3) {
            String createTableAlimentos = "CREATE TABLE " + TABLE_ALIMENTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TIPO + " TEXT NOT NULL, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL" +
                    ")";
            db.execSQL(createTableAlimentos);
        }
    }


    public void guardarDatosUsuario(String id, String tipoDiabetes, double peso, int edad, double glucosa, double colesterol, double estatura, String sexo, String fechaRegistro, Context context) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("tipo_diabetes", tipoDiabetes);
            values.put("peso", peso);
            values.put("edad", edad);
            values.put(COLUMN_NIVEL_GLUCOSA, glucosa);
            values.put("nivel_colesterol", colesterol);
            values.put("estatura", estatura);
            values.put("sexo", sexo);
            values.put("fecha_registro", fechaRegistro);

            db.insert(TABLE_GLUCOSA, null, values);
            Toast.makeText(context, "Datos del usuario guardados correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertAlimento(String tipo, String nombre) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIPO, tipo);
            values.put(COLUMN_NOMBRE, nombre);
            db.insert(TABLE_ALIMENTOS, null, values);
        }
    }

    public long insertarNivelGlucosa(int nivelGlucosa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NIVEL_GLUCOSA, nivelGlucosa);

        long resultado = db.insert(TABLE_GLUCOSA, null, values);

        db.close();

        return resultado;
    }

    public void endTransaction() {
    }

    public void beginTransaction() {
    }

    public void setTransactionSuccessful() {
    }
}

package com.example.nurdermaaaaaa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME = "nurderma.db";
    private static final int DB_VERSION = 5; // Aumentamos la versión para forzar actualización

    // Nombres de tablas
    private static final String TABLE_PACIENTES = "pacientes";
    private static final String TABLE_TRATAMIENTOS = "tratamientos";
    private static final String TABLE_ASIGNACIONES = "asignaciones";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "DatabaseHelper: Constructor inicializado");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: Creando tablas de la base de datos");

        try {
            // Crear tabla pacientes
            String crearTablaPacientes = "CREATE TABLE " + TABLE_PACIENTES + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "apellido TEXT NOT NULL)";
            db.execSQL(crearTablaPacientes);
            Log.d(TAG, "onCreate: Tabla pacientes creada");

            // Crear tabla tratamientos
            String crearTablaTratamientos = "CREATE TABLE " + TABLE_TRATAMIENTOS + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "descripcion TEXT)";
            db.execSQL(crearTablaTratamientos);
            Log.d(TAG, "onCreate: Tabla tratamientos creada");

            // Crear tabla asignaciones
            String crearTablaAsignaciones = "CREATE TABLE " + TABLE_ASIGNACIONES + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "paciente TEXT," +
                    "tratamiento TEXT," +
                    "fecha TEXT," +
                    "hora TEXT," +
                    "dias TEXT)";
            db.execSQL(crearTablaAsignaciones);
            Log.d(TAG, "onCreate: Tabla asignaciones creada");

            // Insertar tratamientos predeterminados
            insertarTratamientosPredeterminados(db);
            Log.d(TAG, "onCreate: Tratamientos predeterminados insertados");
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Error al crear tablas: " + e.getMessage());
        }
    }

    private void insertarTratamientosPredeterminados(SQLiteDatabase db) {
        String insertTratamientos = "INSERT INTO " + TABLE_TRATAMIENTOS + " (nombre, descripcion) VALUES " +
                "('Reductivo', 'Tratamiento enfocado en la reducción de grasa localizada.')," +
                "('Moldeador', 'Define y mejora el contorno corporal.')," +
                "('Anticelulítico', 'Disminuye la apariencia de la celulitis.')," +
                "('Tonificante', 'Mejora la firmeza de la piel.')," +
                "('Drenaje Linfático', 'Estimula el sistema linfático para eliminar toxinas.')," +
                "('Radiofrecuencia', 'Usa ondas electromagnéticas para tensar la piel.')," +
                "('Cavitación', 'Rompe células grasas mediante ultrasonido.')," +
                "('Vacumterapia', 'Masaje profundo con succión para movilizar grasa.')," +
                "('Electroestimulación', 'Contracción muscular eléctrica para tonificar.')," +
                "('Termoterapia', 'Aplicación de calor para mejorar circulación.')," +
                "('Mesoterapia Virtual', 'Tratamiento no invasivo para introducir activos.')," +
                "('Criolipólisis', 'Congelamiento de grasa localizada.')," +
                "('Hidrolipoclasia', 'Eliminación de grasa con suero y ultrasonido.')," +
                "('Ultracavitación', 'Destrucción de grasa mediante ondas ultrasónicas.')," +
                "('Maderoterapia', 'Masaje con instrumentos de madera para modelar cuerpo.')," +
                "('Lipoláser', 'Reducción de grasa con luz láser.')," +

                "('ELIAS', 'Reducción de grasa con luz láser.')," +

                "('C A T A L O G O D E\n" +
                "S E R V I C I O S\n', 'Masajes Reductores\n" +
                "Mini Reductores (5 secciones)\n" +
                "\n" +
                "Técnica Brasilera (8 secciones)\n" +
                "\n" +
                "Paquete Reductor Explotecnología (8 secciones)\n" +
                "\n" +
                "Paquete Reductor Primum (12 secciones)\n" +
                "\n" +
                "Mesoterapia Local Brazos\n')," +



                "('C A T A L O G O  D E  S E R V I C I O S', 'Masajes Reductores Mini Reductores (5 secciones)Técnica Brasilera (8 secciones) Paquete Reductor Explotecnología (8 secciones) Paquete Reductor Primum (12 secciones) Mesoterapia Local Brazos.')," +
                "('Presoterapia', 'Compresión neumática para mejorar circulación y linfa.')";
        db.execSQL(insertTratamientos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: Actualizando de versión " + oldVersion + " a " + newVersion);

        try {
            // En lugar de eliminar todas las tablas directamente, podríamos hacer una migración más inteligente
            // pero para simplificar, eliminamos y recreamos
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACIENTES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRATAMIENTOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASIGNACIONES);
            onCreate(db);
        } catch (Exception e) {
            Log.e(TAG, "onUpgrade: Error al actualizar base de datos: " + e.getMessage());
        }
    }

    // Metodo para verificar si existen las tablas necesarias
    private void verificarTablas() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursorPacientes = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                new String[]{TABLE_PACIENTES});

        if (cursorPacientes.getCount() == 0) {
            // La tabla pacientes no existe, la creamos
            Log.d(TAG, "verificarTablas: Tabla pacientes no existe, creándola...");
            String crearTablaPacientes = "CREATE TABLE " + TABLE_PACIENTES + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "apellido TEXT NOT NULL)";
            db.execSQL(crearTablaPacientes);
        }
        cursorPacientes.close();

        // Repetir proceso similar para otras tablas si es necesario
    }

    // Metodo para insertar paciente
    public boolean insertarPaciente(String nombre, String apellido) {
        Log.d(TAG, "insertarPaciente: Intentando insertar paciente: " + nombre + " " + apellido);

        try {
            // Verificar que las tablas existan antes de insertar
            verificarTablas();

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellido", apellido);
            long result = db.insert(TABLE_PACIENTES, null, values);

            if (result != -1) {
                Log.d(TAG, "insertarPaciente: Paciente insertado correctamente con ID: " + result);
                return true;
            } else {
                Log.e(TAG, "insertarPaciente: Error al insertar paciente");
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "insertarPaciente: Excepción: " + e.getMessage());
            return false;
        }
    }

    // Obtener nombres de pacientes
    public ArrayList<String> obtenerNombresPacientes() {
        ArrayList<String> lista = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT nombre || ' ' || apellido FROM " + TABLE_PACIENTES, null);

            if (cursor.moveToFirst()) {
                do {
                    lista.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
            Log.d(TAG, "obtenerNombresPacientes: Recuperados " + lista.size() + " pacientes");
        } catch (Exception e) {
            Log.e(TAG, "obtenerNombresPacientes: Error: " + e.getMessage());
        }

        return lista;
    }

    // Obtener nombres de tratamientos
    public ArrayList<String> obtenerNombresTratamientos() {
        ArrayList<String> lista = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT nombre FROM " + TABLE_TRATAMIENTOS, null);

            if (cursor.moveToFirst()) {
                do {
                    lista.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
            Log.d(TAG, "obtenerNombresTratamientos: Recuperados " + lista.size() + " tratamientos");
        } catch (Exception e) {
            Log.e(TAG, "obtenerNombresTratamientos: Error: " + e.getMessage());
        }

        return lista;
    }

    // Insertar asignación de tratamiento
    public boolean insertarAsignacionTratamiento(String paciente, String tratamiento, String fecha, String hora, String dias) {
        Log.d(TAG, "insertarAsignacionTratamiento: Asignando tratamiento a paciente: " + paciente);

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("paciente", paciente);
            values.put("tratamiento", tratamiento);
            values.put("fecha", fecha);
            values.put("hora", hora);
            values.put("dias", dias);

            long result = db.insert(TABLE_ASIGNACIONES, null, values);

            if (result != -1) {
                Log.d(TAG, "insertarAsignacionTratamiento: Asignación insertada correctamente con ID: " + result);
                return true;
            } else {
                Log.e(TAG, "insertarAsignacionTratamiento: Error al insertar asignación");
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "insertarAsignacionTratamiento: Excepción: " + e.getMessage());
            return false;
        }
    }

    // Obtener agenda (para VerAgendaActivity)
    public ArrayList<HashMap<String, String>> obtenerAgenda() {
        ArrayList<HashMap<String, String>> lista = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT paciente, tratamiento, fecha, hora, dias FROM " + TABLE_ASIGNACIONES,
                    null);

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("paciente", cursor.getString(0));
                    map.put("detalle", cursor.getString(1) + " - " + cursor.getString(2) +
                            " " + cursor.getString(3) + " (" + cursor.getString(4) + ")");
                    lista.add(map);
                } while (cursor.moveToNext());
            }
            cursor.close();
            Log.d(TAG, "obtenerAgenda: Recuperadas " + lista.size() + " citas de agenda");
        } catch (Exception e) {
            Log.e(TAG, "obtenerAgenda: Error: " + e.getMessage());
        }

        return lista;
    }

    // Obtener tratamientos para VerTratamientosActivity
    public ArrayList<String> obtenerListaTratamientos() {
        ArrayList<String> lista = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT nombre || ': ' || descripcion FROM " + TABLE_TRATAMIENTOS,
                    null);

            if (cursor.moveToFirst()) {
                do {
                    lista.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
            Log.d(TAG, "obtenerListaTratamientos: Recuperados " + lista.size() + " detalles de tratamientos");
        } catch (Exception e) {
            Log.e(TAG, "obtenerListaTratamientos: Error: " + e.getMessage());
        }

        return lista;
    }

    // Metodo para eliminar un paciente
    public boolean eliminarPaciente(String nombreCompleto) {
        try {
            // Separar nombre y apellido
            String[] partes = nombreCompleto.split(" ", 2);
            String nombre = partes[0];
            String apellido = partes.length > 1 ? partes[1] : "";

            SQLiteDatabase db = this.getWritableDatabase();
            int result = db.delete(TABLE_PACIENTES,
                    "nombre = ? AND apellido = ?",
                    new String[]{nombre, apellido});

            // También eliminar sus asignaciones
            db.delete(TABLE_ASIGNACIONES,
                    "paciente = ?",
                    new String[]{nombreCompleto});

            Log.d(TAG, "eliminarPaciente: Eliminado paciente: " + nombreCompleto + ", filas afectadas: " + result);
            return result > 0;
        } catch (Exception e) {
            Log.e(TAG, "eliminarPaciente: Error: " + e.getMessage());
            return false;
        }
    }

    // Metodo para eliminar una asignación
    public boolean eliminarAsignacion(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int result = db.delete(TABLE_ASIGNACIONES,
                    "id = ?",
                    new String[]{String.valueOf(id)});

            Log.d(TAG, "eliminarAsignacion: Eliminada asignación ID: " + id + ", filas afectadas: " + result);
            return result > 0;
        } catch (Exception e) {
            Log.e(TAG, "eliminarAsignacion: Error: " + e.getMessage());
            return false;
        }
    }
}
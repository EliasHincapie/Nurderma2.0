package com.example.nurdermaaaaaa; // Paquete al que pertenece esta clase

import android.os.Bundle; // Para manejar el ciclo de vida de la actividad
import android.widget.ArrayAdapter; // Adaptador para conectar una lista de Strings con un ListView
import android.widget.ListView; // Vista que muestra una lista de elementos

import androidx.activity.EdgeToEdge; // (No se usa en este código, pero puede ser para diseño de pantalla completa)
import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades compatibles con versiones antiguas
import androidx.core.graphics.Insets; // (No se usa directamente aquí)
import androidx.core.view.ViewCompat; // (No se usa directamente aquí)
import androidx.core.view.WindowInsetsCompat; // (No se usa directamente aquí)

import java.util.ArrayList; // Lista dinámica para almacenar datos

public class VerTratamientosActivity extends AppCompatActivity { // Actividad que muestra una lista de tratamientos

    ListView listViewTratamientos; // Vista para mostrar la lista de tratamientos
    DatabaseHelper dbHelper; // Objeto para interactuar con la base de datos
    ArrayList<String> listaTratamientos; // Lista que almacenará los tratamientos obtenidos

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Metodo que se ejecuta al iniciar la actividad
        super.onCreate(savedInstanceState); // Llama al metodo padre
        setContentView(R.layout.activity_ver_tratamientos); // Establece el diseño XML de esta pantalla

        listViewTratamientos = findViewById(R.id.listViewTratamientos); // Conecta el ListView con su ID en el XML
        dbHelper = new DatabaseHelper(this); // Inicializa el helper de base de datos

        listaTratamientos = dbHelper.obtenerListaTratamientos(); // Llama al metodo que devuelve los tratamientos como lista de Strings

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, // Crea un adaptador para mostrar la lista
                android.R.layout.simple_list_item_1, // Layout simple para cada ítem (una línea de texto)
                listaTratamientos); // Lista de tratamientos a mostrar

        listViewTratamientos.setAdapter(adapter); // Asigna el adaptador al ListView para mostrar los tratamientos
    }
}

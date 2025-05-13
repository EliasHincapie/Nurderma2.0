package com.example.nurdermaaaaaa; // Paquete donde se encuentra esta clase

import android.os.Bundle; // Necesario para manejar el ciclo de vida de la actividad
import android.widget.ListView; // Componente visual para mostrar listas
import android.widget.SimpleAdapter; // Adaptador para mostrar datos con clave-valor en ListView

import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades compatibles

import java.util.ArrayList; // Estructura de datos para listas dinámicas
import java.util.HashMap; // Estructura para mapear claves y valores (tipo diccionario)

public class VerAgendaActivity extends AppCompatActivity { // Actividad que muestra la agenda completa

    private ListView listView; // ListView para mostrar los datos
    private DatabaseHelper dbHelper; // Objeto para interactuar con la base de datos
    private ArrayList<HashMap<String, String>> listaAgenda; // Lista de asignaciones en formato clave-valor

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Metodo que se ejecuta al iniciar la actividad
        super.onCreate(savedInstanceState); // Llama al metodo de la superclase
        setContentView(R.layout.activity_ver_agenda); // Establece el layout de la pantalla

        listView = findViewById(R.id.listViewAgenda); // Asocia el ListView con su componente en el XML
        dbHelper = new DatabaseHelper(this); // Inicializa el helper de base de datos

        listaAgenda = dbHelper.obtenerAgenda(); // Obtiene la agenda desde la base de datos como lista de HashMap

        // Crea un adaptador para mostrar cada elemento de la agenda
        SimpleAdapter adapter = new SimpleAdapter(
                this, // Contexto actual
                listaAgenda, // Datos a mostrar (lista de pacientes y detalles)
                android.R.layout.simple_list_item_2, // Layout simple con dos líneas de texto
                new String[]{"paciente", "detalle"}, // Claves del HashMap a mostrar
                new int[]{android.R.id.text1, android.R.id.text2} // IDs de vistas donde se muestran los valores
        );

        listView.setAdapter(adapter); // Asigna el adaptador al ListView para mostrar los datos
    }
}

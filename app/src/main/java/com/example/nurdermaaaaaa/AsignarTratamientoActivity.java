package com.example.nurdermaaaaaa; // Paquete donde se encuentra esta clase

import android.os.Bundle; // Necesario para el metodo onCreate
import android.widget.ArrayAdapter; // Adaptador para llenar los Spinners
import android.widget.Button; // Botón de la interfaz
import android.widget.CheckBox; // Casillas de verificación para los días
import android.widget.DatePicker; // Selector de fechas
import android.widget.EditText; // Campo de texto para mostrar fecha y hora
import android.widget.Spinner; // Selector desplegable (pacientes y tratamientos)
import android.widget.TimePicker; // Selector de hora
import android.widget.Toast; // Mensajes breves (notificaciones tipo "toast")

import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades con compatibilidad

import java.util.List; // Para manejar listas (List<String>)

public class AsignarTratamientoActivity extends AppCompatActivity { // Clase principal de la actividad

    Spinner spinnerPacientes, spinnerTratamientos; // Spinners para seleccionar paciente y tratamiento
    EditText editTextFecha, editTextHora; // Campos de texto para mostrar fecha y hora seleccionadas
    DatePicker datePicker; // Componente para seleccionar la fecha
    TimePicker timePicker; // Componente para seleccionar la hora
    CheckBox checkLunes, checkMartes, checkMiercoles, checkJueves, checkViernes, checkSabado, checkDomingo; // Días de la semana
    Button btnGuardar; // Botón para guardar la asignación
    DatabaseHelper dbHelper; // Objeto para acceder a la base de datos SQLite

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Metodo que se ejecuta al iniciar la actividad
        super.onCreate(savedInstanceState); // Llama al metodo de la superclase
        setContentView(R.layout.activity_asignar_tratamiento); // Asocia esta clase con su layout XML

        dbHelper = new DatabaseHelper(this); // Inicializa el helper de la base de datos

        // Enlaza los componentes visuales con sus IDs del layout
        spinnerPacientes = findViewById(R.id.spinnerPacientes);
        spinnerTratamientos = findViewById(R.id.spinnerTratamientos);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextHora = findViewById(R.id.editTextHora);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        // Enlaza los checkboxes con los días de la semana
        checkLunes = findViewById(R.id.checkLunes);
        checkMartes = findViewById(R.id.checkMartes);
        checkMiercoles = findViewById(R.id.checkMiercoles);
        checkJueves = findViewById(R.id.checkJueves);
        checkViernes = findViewById(R.id.checkViernes);
        checkSabado = findViewById(R.id.checkSabado);
        checkDomingo = findViewById(R.id.checkDomingo);

        btnGuardar = findViewById(R.id.btnGuardarTratamiento); // Botón para guardar la asignación

        cargarPacientes(); // Carga los nombres de pacientes en el Spinner
        cargarTratamientos(); // Carga los tratamientos en el Spinner

        // Cuando se hace clic en el campo de fecha, se toma la fecha del DatePicker y se muestra
        editTextFecha.setOnClickListener(v -> {
            int dia = datePicker.getDayOfMonth(); // Obtiene el día
            int mes = datePicker.getMonth() + 1; // Obtiene el mes (0-indexado, por eso +1)
            int anio = datePicker.getYear(); // Obtiene el año
            editTextFecha.setText(dia + "/" + mes + "/" + anio); // Muestra la fecha en el EditText
        });

        // Cuando se hace clic en el campo de hora, se toma la hora del TimePicker y se muestra
        editTextHora.setOnClickListener(v -> {
            int hora = timePicker.getHour(); // Obtiene la hora
            int minuto = timePicker.getMinute(); // Obtiene los minutos
            editTextHora.setText(String.format("%02d:%02d", hora, minuto)); // Muestra la hora formateada (ej: 09:30)
        });

        // Cuando se presiona el botón "Guardar", se ejecuta el metodo para guardar la asignación
        btnGuardar.setOnClickListener(v -> guardarAsignacion());
    }

    private void cargarPacientes() { // Llena el Spinner con los nombres de los pacientes
        List<String> pacientes = dbHelper.obtenerNombresPacientes(); // Obtiene los nombres desde la BD
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pacientes); // Crea adaptador
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Estilo desplegable
        spinnerPacientes.setAdapter(adapter); // Asigna el adaptador al Spinner
    }

    private void cargarTratamientos() { // Llena el Spinner con los nombres de los tratamientos
        List<String> tratamientos = dbHelper.obtenerNombresTratamientos(); // Obtiene tratamientos desde la BD
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tratamientos); // Crea adaptador
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Estilo desplegable
        spinnerTratamientos.setAdapter(adapter); // Asigna el adaptador al Spinner
    }

    private void guardarAsignacion() { // Metodo para guardar la asignación en la base de datos
        String paciente = spinnerPacientes.getSelectedItem().toString(); // Obtiene el nombre del paciente seleccionado
        String tratamiento = spinnerTratamientos.getSelectedItem().toString(); // Obtiene el tratamiento seleccionado
        String fecha = editTextFecha.getText().toString(); // Toma la fecha ingresada
        String hora = editTextHora.getText().toString(); // Toma la hora ingresada

        StringBuilder dias = new StringBuilder(); // Para concatenar los días seleccionados
        if (checkLunes.isChecked()) dias.append("Lunes "); // Verifica si está marcado
        if (checkMartes.isChecked()) dias.append("Martes ");
        if (checkMiercoles.isChecked()) dias.append("Miércoles ");
        if (checkJueves.isChecked()) dias.append("Jueves ");
        if (checkViernes.isChecked()) dias.append("Viernes ");
        if (checkSabado.isChecked()) dias.append("Sábado ");
        if (checkDomingo.isChecked()) dias.append("Domingo ");

        // Llama al metodo de la BD para insertar la asignación
        boolean exito = dbHelper.insertarAsignacionTratamiento(paciente, tratamiento, fecha, hora, dias.toString().trim());

        if (exito) { // Si se insertó correctamente
            Toast.makeText(this, "Tratamiento asignado correctamente", Toast.LENGTH_SHORT).show(); // Notifica éxito
            finish(); // Cierra la actividad
        } else {
            Toast.makeText(this, "Error al asignar tratamiento", Toast.LENGTH_SHORT).show(); // Notifica error
        }
    }
}

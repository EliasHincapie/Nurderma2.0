package com.example.nurdermaaaaaa;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EditarAsignacionActivity extends AppCompatActivity {

    private int asignacionId;
    private Spinner spinnerPacientes, spinnerTratamientos;
    private EditText editTextFecha, editTextHora;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox checkLunes, checkMartes, checkMiercoles, checkJueves, checkViernes, checkSabado, checkDomingo;
    private Button btnGuardar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_tratamiento); // Reutilizamos el mismo layout

        // Inicializar componentes
        dbHelper = new DatabaseHelper(this);

        spinnerPacientes = findViewById(R.id.spinnerPacientes);
        spinnerTratamientos = findViewById(R.id.spinnerTratamientos);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextHora = findViewById(R.id.editTextHora);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        checkLunes = findViewById(R.id.checkLunes);
        checkMartes = findViewById(R.id.checkMartes);
        checkMiercoles = findViewById(R.id.checkMiercoles);
        checkJueves = findViewById(R.id.checkJueves);
        checkViernes = findViewById(R.id.checkViernes);
        checkSabado = findViewById(R.id.checkSabado);
        checkDomingo = findViewById(R.id.checkDomingo);

        btnGuardar = findViewById(R.id.btnGuardarTratamiento);

        // Cambiar el título del botón para indicar que es una actualización
        btnGuardar.setText("Actualizar Asignación");

        // Cargar datos en los spinners
        cargarPacientes();
        cargarTratamientos();

        // Obtener los datos de la asignación a editar
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            asignacionId = Integer.parseInt(extras.getString("ID"));
            String paciente = extras.getString("PACIENTE");
            String tratamiento = extras.getString("TRATAMIENTO");
            String fecha = extras.getString("FECHA");
            String hora = extras.getString("HORA");
            String dias = extras.getString("DIAS");

            // Establecer los valores en los campos correspondientes
            editTextFecha.setText(fecha);
            editTextHora.setText(hora);

            // Seleccionar paciente en el spinner
            selectSpinnerItemByValue(spinnerPacientes, paciente);

            // Seleccionar tratamiento en el spinner
            selectSpinnerItemByValue(spinnerTratamientos, tratamiento);

            // Marcar los días de la semana
            if (dias != null) {
                if (dias.contains("Lunes")) checkLunes.setChecked(true);
                if (dias.contains("Martes")) checkMartes.setChecked(true);
                if (dias.contains("Miércoles")) checkMiercoles.setChecked(true);
                if (dias.contains("Jueves")) checkJueves.setChecked(true);
                if (dias.contains("Viernes")) checkViernes.setChecked(true);
                if (dias.contains("Sábado")) checkSabado.setChecked(true);
                if (dias.contains("Domingo")) checkDomingo.setChecked(true);
            }
        }

        // Configurar el DatePicker
        editTextFecha.setOnClickListener(v -> {
            int dia = datePicker.getDayOfMonth();
            int mes = datePicker.getMonth() + 1;
            int anio = datePicker.getYear();
            editTextFecha.setText(dia + "/" + mes + "/" + anio);
        });

        // Configurar el TimePicker
        editTextHora.setOnClickListener(v -> {
            int hora = timePicker.getHour();
            int minuto = timePicker.getMinute();
            editTextHora.setText(String.format("%02d:%02d", hora, minuto));
        });

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener(v -> actualizarAsignacion());
    }

    private void cargarPacientes() {
        List<String> pacientes = dbHelper.obtenerNombresPacientes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pacientes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPacientes.setAdapter(adapter);
    }

    private void cargarTratamientos() {
        List<String> tratamientos = dbHelper.obtenerNombresTratamientos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tratamientos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTratamientos.setAdapter(adapter);
    }

    // Método para seleccionar un elemento en el spinner por su valor
    private void selectSpinnerItemByValue(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if (adapter.getItem(position).toString().equals(value)) {
                spinner.setSelection(position);
                break;
            }
        }
    }

    private void actualizarAsignacion() {
        String paciente = spinnerPacientes.getSelectedItem().toString();
        String tratamiento = spinnerTratamientos.getSelectedItem().toString();
        String fecha = editTextFecha.getText().toString();
        String hora = editTextHora.getText().toString();

        StringBuilder dias = new StringBuilder();
        if (checkLunes.isChecked()) dias.append("Lunes ");
        if (checkMartes.isChecked()) dias.append("Martes ");
        if (checkMiercoles.isChecked()) dias.append("Miércoles ");
        if (checkJueves.isChecked()) dias.append("Jueves ");
        if (checkViernes.isChecked()) dias.append("Viernes ");
        if (checkSabado.isChecked()) dias.append("Sábado ");
        if (checkDomingo.isChecked()) dias.append("Domingo ");

        boolean exito = dbHelper.actualizarAsignacion(asignacionId, paciente, tratamiento, fecha, hora, dias.toString().trim());

        if (exito) {
            Toast.makeText(this, "¡Asignación actualizada correctamente!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar la asignación", Toast.LENGTH_SHORT).show();
        }
    }
}
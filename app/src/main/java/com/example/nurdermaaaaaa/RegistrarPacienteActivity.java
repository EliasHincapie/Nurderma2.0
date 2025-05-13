package com.example.nurdermaaaaaa; // Paquete al que pertenece esta clase

import android.os.Bundle; // Necesario para el ciclo de vida de actividades
import android.view.View; // Clase base para manejar clics y vistas
import android.widget.Button; // Componente de botón
import android.widget.EditText; // Componente de entrada de texto
import android.widget.Toast; // Clase para mostrar mensajes breves (toasts)

import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades compatibles

public class RegistrarPacienteActivity extends AppCompatActivity { // Clase de la pantalla para registrar pacientes

    private EditText editNombre, editApellido; // Campos para ingresar nombre y apellido
    private Button btnGuardar; // Botón para guardar el paciente
    private DatabaseHelper dbHelper; // Objeto para manejar la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Metodo que se ejecuta al iniciar la actividad
        super.onCreate(savedInstanceState); // Llama a la implementación base de onCreate
        setContentView(R.layout.activity_registrar_paciente); // Establece el layout de la actividad

        editNombre = findViewById(R.id.editNombre); // Asocia el campo de nombre con su ID del XML
        editApellido = findViewById(R.id.editApellido); // Asocia el campo de apellido con su ID del XML
        btnGuardar = findViewById(R.id.btnGuardarPaciente); // Asocia el botón con su ID del XML
        dbHelper = new DatabaseHelper(this); // Inicializa el helper de base de datos

        btnGuardar.setOnClickListener(new View.OnClickListener() { // Asigna una acción al hacer clic en el botón
            @Override
            public void onClick(View v) {
                String nombre = editNombre.getText().toString().trim(); // Obtiene el texto del campo nombre
                String apellido = editApellido.getText().toString().trim(); // Obtiene el texto del campo apellido

                if (nombre.isEmpty() || apellido.isEmpty()) { // Valida que ambos campos estén completos
                    Toast.makeText(getApplicationContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show(); // Muestra advertencia
                    return; // Sale del metodo si falta algún dato
                }

                boolean exito = dbHelper.insertarPaciente(nombre, apellido); // Inserta el paciente en la base de datos

                if (exito) { // Si la inserción fue exitosa
                    Toast.makeText(getApplicationContext(), "Paciente registrado correctamente", Toast.LENGTH_SHORT).show(); // Muestra mensaje de éxito
                    finish(); // Cierra la actividad actual (vuelve atrás o actualiza la lista)
                } else { // Si hubo error al insertar
                    Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show(); // Muestra mensaje de error
                }
            }
        });
    }
}

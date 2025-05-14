package com.example.nurdermaaaaaa;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VerPacientesActivity extends AppCompatActivity {

    private ListView listViewPacientes;
    private DatabaseHelper dbHelper;
    private ArrayList<String> listaPacientes;
    private PacientesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pacientes);

        listViewPacientes = findViewById(R.id.listViewPacientes);
        dbHelper = new DatabaseHelper(this);

        // Cargar los datos de pacientes
        cargarDatos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar datos cada vez que se regrese a esta actividad
        cargarDatos();
    }

    private void cargarDatos() {
        // Obtener la lista de pacientes desde la base de datos
        listaPacientes = dbHelper.obtenerNombresPacientes();

        // Crear y asignar el adaptador personalizado
        adapter = new PacientesAdapter();
        listViewPacientes.setAdapter(adapter);
    }

    // Adaptador personalizado para manejar la vista de cada paciente y su botón de eliminar
    private class PacientesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listaPacientes.size();
        }

        @Override
        public Object getItem(int position) {
            return listaPacientes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_paciente, parent, false);
            }

            // Obtener las referencias a los elementos del layout
            TextView textViewNombrePaciente = view.findViewById(R.id.textViewNombrePaciente);
            Button btnEliminarPaciente = view.findViewById(R.id.btnEliminarPaciente);

            // Obtener el nombre del paciente actual
            final String nombrePaciente = listaPacientes.get(position);

            // Establecer el nombre en la vista
            textViewNombrePaciente.setText(nombrePaciente);

            // Configurar el botón Eliminar
            btnEliminarPaciente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Mostrar diálogo de confirmación
                    new AlertDialog.Builder(VerPacientesActivity.this)
                            .setTitle("Eliminar Paciente")
                            .setMessage("¿Estás seguro de que deseas eliminar a " + nombrePaciente + "?\n\nSe eliminarán también todas sus asignaciones de tratamientos.")
                            .setPositiveButton("Sí", (dialog, which) -> {
                                // Eliminar el paciente si el usuario confirma
                                boolean exito = dbHelper.eliminarPaciente(nombrePaciente);
                                if (exito) {
                                    // Eliminar de la lista y notificar al adaptador
                                    listaPacientes.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(VerPacientesActivity.this,
                                            "Paciente eliminado correctamente",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(VerPacientesActivity.this,
                                            "Error al eliminar al paciente",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });

            return view;
        }


    }
}
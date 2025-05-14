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
import java.util.HashMap;

public class VerAgendaActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private ArrayList<HashMap<String, String>> listaAgenda;
    private AgendaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_agenda);

        listView = findViewById(R.id.listViewAgenda);
        dbHelper = new DatabaseHelper(this);

        // Cargar los datos al iniciar
        cargarDatos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar datos cada vez que se regrese a esta actividad
        cargarDatos();
    }

    private void cargarDatos() {
        // Obtener la agenda desde la base de datos
        listaAgenda = dbHelper.obtenerAgenda();

        // Crear y asignar el adaptador personalizado
        adapter = new AgendaAdapter();
        listView.setAdapter(adapter);
    }

    // Adaptador personalizado para manejar la vista de cada elemento y sus botones
    private class AgendaAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listaAgenda.size();
        }

        @Override
        public Object getItem(int position) {
            return listaAgenda.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_agenda, parent, false);
            }

            // Obtener las referencias a los elementos del layout
            TextView textViewPaciente = view.findViewById(R.id.textViewPaciente);
            TextView textViewDetalle = view.findViewById(R.id.textViewDetalle);
            Button btnEditar = view.findViewById(R.id.btnEditar);
            Button btnEliminar = view.findViewById(R.id.btnEliminar);

            // Obtener los datos del elemento actual
            final HashMap<String, String> item = listaAgenda.get(position);
            String paciente = item.get("paciente");
            String detalle = item.get("detalle");
            final String id = item.get("id"); // Asegúrate de que el ID se incluya en el HashMap

            // Establecer los datos en las vistas
            textViewPaciente.setText(paciente);
            textViewDetalle.setText(detalle);

            // Configurar el botón Editar
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear un Intent para abrir la actividad de edición
                    Intent intent = new Intent(VerAgendaActivity.this, EditarAsignacionActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("PACIENTE", item.get("paciente"));
                    intent.putExtra("TRATAMIENTO", item.get("tratamiento"));
                    intent.putExtra("FECHA", item.get("fecha"));
                    intent.putExtra("HORA", item.get("hora"));
                    intent.putExtra("DIAS", item.get("dias"));
                    startActivity(intent);
                }
            });

            // Configurar el botón Eliminar
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Mostrar diálogo de confirmación
                    new AlertDialog.Builder(VerAgendaActivity.this)
                            .setTitle("Eliminar Asignación")
                            .setMessage("¿Estás seguro de que deseas eliminar esta asignación?")
                            .setPositiveButton("Sí", (dialog, which) -> {
                                // Eliminar la asignación si el usuario confirma
                                boolean exito = dbHelper.eliminarAsignacion(Integer.parseInt(id));
                                if (exito) {
                                    // Eliminar de la lista y notificar al adaptador
                                    listaAgenda.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(VerAgendaActivity.this,
                                            "Asignación eliminada correctamente",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(VerAgendaActivity.this,
                                            "Error al eliminar la asignación",
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
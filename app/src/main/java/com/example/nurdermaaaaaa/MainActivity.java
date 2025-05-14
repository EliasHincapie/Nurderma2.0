package com.example.nurdermaaaaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnRegistrar, btnAsignar, btnVerTratamientos, btnVerAgenda, btnVerPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrar = findViewById(R.id.btnRegistrarPaciente);
        btnAsignar = findViewById(R.id.btnAsignarTratamiento);
        btnVerTratamientos = findViewById(R.id.btnVerTratamientos);
        btnVerAgenda = findViewById(R.id.btnVerAgenda);
        btnVerPacientes = findViewById(R.id.btnVerPacientes);
        btnRegistrar.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RegistrarPacienteActivity.class);
            startActivity(i);
        });

        btnAsignar.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AsignarTratamientoActivity.class);
            startActivity(i);
        });

        btnVerTratamientos.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, VerTratamientosActivity.class);
            startActivity(i);
        });

        btnVerAgenda.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, VerAgendaActivity.class);
            startActivity(i);
        });

        btnVerPacientes.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, VerPacientesActivity.class);
            startActivity(i);
        });
    }
}

package com.example.servicos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btCep = findViewById(R.id.btCep);
        btCep.setOnClickListener(this);

        Button btFeriado = findViewById(R.id.btFeriado);
        btFeriado.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btCep) {
            Intent main = new Intent(MainActivity.this, CepActivity.class);
            startActivity(main);
        } else if (v.getId() == R.id.btFeriado) {
            Intent holidays = new Intent(MainActivity.this, HolidaysActivity.class);
            startActivity(holidays);
        }
    }
}
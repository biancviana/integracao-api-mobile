package com.example.servicos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.servicos.api.InvertextoApi;
import com.example.servicos.model.Feriado;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HolidaysActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_holidays);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btVoltar = findViewById(R.id.btVoltarFeriado);
        btVoltar.setOnClickListener(this);

        Button btBuscar = findViewById(R.id.btBuscarFeriado);
        btBuscar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btVoltarFeriado) {
            finish();
        } else if (v.getId() == R.id.btBuscarFeriado) {
            EditText etAno = findViewById(R.id.etAno);
            String ano = etAno.getText().toString();
            consultar(ano);
        }
    }

    private void consultar(String ano) {
        TextView tvInfo = findViewById(R.id.tvInfoFeriado);
        ProgressBar progressBar = findViewById(R.id.pbCarregarFeriado);

        progressBar.setVisibility(View.VISIBLE);
        tvInfo.setText("");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

        Call<List<Feriado>> call = invertextoApi.getFeriado(
                ano, Constantes.TOKEN_HOLIDAYS
        );

        call.enqueue(new Callback<List<Feriado>>() {

            @Override
            public void onResponse(Call<List<Feriado>> call, Response<List<Feriado>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    List<Feriado> feriados = response.body();
                    StringBuilder sb = new StringBuilder();
                    for (Feriado f : feriados) {
                        sb.append(f.formatar()).append("\n\n");
                    }
                    tvInfo.setText(sb.toString());
                } else {
                    Toast.makeText(HolidaysActivity.this,
                            "Erro ao buscar informações. Verifique o ano!",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Feriado>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(HolidaysActivity.this,
                        "Verifique sua conexão com a Internet!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
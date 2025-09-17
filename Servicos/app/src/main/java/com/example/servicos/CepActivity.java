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
import com.example.servicos.model.Logradouro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cep);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btVoltar = findViewById(R.id.btVoltar);
        btVoltar.setOnClickListener(this);

        Button btBuscar = findViewById(R.id.btBuscarCep);
        btBuscar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btVoltar) {
            finish();
        } else if (v.getId() == R.id.btBuscarCep) {
            EditText etCep = findViewById(R.id.etCep);
            String numeroCep = etCep.getText().toString();
            consultar(numeroCep);
        }
    }

    private void consultar(String numeroCep) {
        TextView tvInfo = findViewById(R.id.tvInfo);
        ProgressBar progressBar = findViewById(R.id.pbCarregar);

        progressBar.setVisibility(View.VISIBLE);
        tvInfo.setText("");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

        Call<Logradouro> call = invertextoApi.getLogradouro(
                numeroCep, Constantes.TOKEN
        );

        call.enqueue(new Callback<Logradouro>() {

            @Override
            public void onResponse(Call<Logradouro> call, Response<Logradouro> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    Logradouro logradouro = response.body();

                    // exibir no tvInfo as informações
                    tvInfo.setText(logradouro.formatar());
                } else {
                    Toast.makeText(CepActivity.this,
                            "Erro ao buscar informações. Verifique o CEP!",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Logradouro> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(CepActivity.this,
                        "Verifique sua conexão com a Internet!",
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
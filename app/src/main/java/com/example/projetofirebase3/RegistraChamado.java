package com.example.projetofirebase3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistraChamado extends AppCompatActivity {


    private Button bt_finaliza, bt_proximo;

    private TextView Agendamento;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Essa parte aqui que não estou conseguindo logar para outra tela TelaPrincipal...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_chamado);

        IniciarComponentes();

        // O erro está no AGENDAMENTO que está saindo do aplicativo
        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistraChamado.this, com.example.projetofirebase3.Agendamento.class);
                startActivity(intent);
            }
        });
    }

    // Aqui está o erro. Tem que criar outra tela para colocar para Transferir os Dias / Horas marcadas.
    private void IniciarComponentes() {

        Agendamento = findViewById(R.id.bt_proximo);

        bt_proximo = findViewById(R.id.bt_proximo);
    }
}
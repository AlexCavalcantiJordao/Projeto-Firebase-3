package com.example.projetofirebase3.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofirebase3.Pessoa;
import com.example.projetofirebase3.R;
import com.example.projetofirebase3.RegistraChamado;
import com.example.projetofirebase3.Servico;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Agendamento extends AppCompatActivity {
   /* private Button bt_agendar, bt_seguinte;

    private EditText edit_data, edit_hora, edit_profissional, edit_tecnico1, edit_tecnico2, edit_tecnico3, edit_dia, edit_mes, edit_nome;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("agenda");
    private Calendar calendar = Calendar.getInstance();
    private String data = "";
    private String hora = "";
    private String year = "";
    private Integer monthOfYear = Integer.valueOf("");
    private Integer dayOfMonth = Integer.valueOf("");
    String[] mensagens = {"Preencha todos os campos.", "Foi agendado com sucesso."};
    String usuarioID;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        /*IniciarComponentes();

        // Aqui que vai para outra tela de "AGENDAMENTO".
        bt_seguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agendamento.this, Servico.class);
                startActivity(intent);
            }
        });

        // Aqui que vai finalizar o pedido de manutenção na máquina "Computador"....
        bt_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
                String nomePessoa = edit_nomePessoa.getText().toString(); // 1
                String nomeSetor = edit_nomeSetor.getText().toString(); // 2
                String siglaSetor = edit_siglaSetor.getText().toString(); // 3
                String material = edit_material.getText().toString(); // 4
                String problema = edit_problema.getText().toString(); // 5
                String quantidade = edit_quantidade.getText().toString(); // 5

                if (nomePessoa.isEmpty() || nomeSetor.isEmpty() || siglaSetor.isEmpty() || material.isEmpty() || problema.isEmpty() || quantidade.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    CadastrarUsuario(v);
                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    // Aqui que vai mostrar o e-mail do usuário que está cadastrado....
    @Override
    protected void onStart() {
        super.onStart();

        String pessoa = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuários").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    edit_nomePessoa.setText(documentSnapshot.getString("nome"));
                    edit_nomePessoa.setText(pessoa);
                }
            }
        });
    }

    // Aqui vai salvar os dados do usuário...
    private void CadastrarUsuario(View v) {
        String nomePessoa = edit_nomePessoa.getText().toString(); // 1
        String nomeSetor = edit_nomeSetor.getText().toString(); // 2
        String siglaSetor = edit_siglaSetor.getText().toString(); // 3
        String material = edit_material.getText().toString(); // 4
        String problema = edit_problema.getText().toString(); // 5
        String quantidade = edit_quantidade.getText().toString(); // 6

        System.out.println("Numeração " + nomePessoa + nomeSetor + siglaSetor + material + problema + quantidade);

        // Criar um ID único para a pessoa no Firebase
        String usuarioID = databaseReference.push().getKey();
        Pessoa pessoa = new Pessoa(nomePessoa, nomeSetor, siglaSetor, material, problema, quantidade);

        databaseReference.child(usuarioID).setValue(pessoa).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("chamada", "Deus me Ajuda" + pessoa.getNomePessoa()); // 1

                if (task.isSuccessful()) {
                    Log.d("entrou", "entrou " + task.isSuccessful());
                    SalvarDadosUsuario();


                } else {
                    String erro;
                    try {
                        throw task.getException();

                    } catch (Exception e) {

                        erro = "Erro ao não preencher as todas as tabelas.";
                    }
                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    // Aqui que vai salvar os dados do usuário que vai relatar os problemas do computador por sistema "Aplicativo"....
    private void SalvarDadosUsuario() {
        String nomePessoa = edit_nomePessoa.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nomePessoa, nomeSetor, setor, sigla, material, problema, quantidade", nomePessoa);

        DocumentReference documentReference = db.collection("Usuários").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void oVoid) {
                        Log.d("db", "Sucesso ao salvar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("db_error", "Erro ao salvar os dados" + e.toString());

                    }
                });
    }

    // Aqui que inicia tudo, as telas como foi criadas e até o fim....
    private void IniciarComponentes() {

        bt_seguinte = findViewById(R.id.seguinte);
        bt_agendar = findViewById(R.id.bt_agendar);*/
    }
}
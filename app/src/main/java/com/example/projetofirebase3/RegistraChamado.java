package com.example.projetofirebase3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofirebase3.view.Agendamento;
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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class RegistraChamado extends AppCompatActivity {
    private Button bt_finaliza, bt_proximo;
    private EditText edit_nomePessoa, edit_nomeSetor, edit_siglaSetor, edit_problema, edit_material, edit_quantidade;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pessoas");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[] mensagens = {"Preencha todos os campos.", "O chamado foi finalizado com sucesso."};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_chamado);

        IniciarComponentes();
        //mAuth = FirebaseAuth.getInstance();
        // Aqui que vai para outra tela de "AGENDAMENTO".
        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistraChamado.this, Agendamento.class);
                startActivity(intent);
            }
        });

        // Aqui que vai finalizar o pedido de manutenção na máquina "Computador"....
        bt_finaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
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
        // FirebaseUser currentUser = mAuth.getCurrentUser();

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
        edit_nomePessoa = findViewById(R.id.edit_nomePessoa);
        edit_nomeSetor = findViewById(R.id.edit_nomeSetor);
        edit_siglaSetor = findViewById(R.id.edit_siglaSetor);
        edit_quantidade = findViewById(R.id.edit_quantidade);
        edit_material = findViewById(R.id.edit_material);
        edit_problema = findViewById(R.id.edit_problema);
        bt_proximo = findViewById(R.id.bt_proximo);
        bt_finaliza = findViewById(R.id.bt_finaliza);
    }
}
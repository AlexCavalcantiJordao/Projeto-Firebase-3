package com.example.projetofirebase3;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tecnico {

    private String tecnico1;
    private String tecnico2;
    private String tecnico3;

    public Tecnico() {

    }

    public Tecnico(String tecnico1, String tecnico2, String tecnico3) {

        this.tecnico1 = tecnico1;
        this.tecnico2 = tecnico2;
        this.tecnico3 = tecnico3;
    }

    public String getValue() {
        return tecnico1 + tecnico2 + tecnico3;
    }

    public String getTecnico1() {
        return tecnico1;
    }

    public String getTecnico2() {
        return tecnico2;
    }

    public String getTecnico3() {
        return tecnico3;
    }

    // Salvar os dados dos Técnicos...
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public void SalvarDadosUsuario(Tecnico tecnico) {

        String key = database.getKey();

        // Gravar o objeto Dados no banco de dados firebase sob a chave gerada....
        database.child("tecnico").push().getKey();

        // Função para ler todos os objetos Dados do banco de dados firebase....
        database.child("tecnico").child(key).setValue(tecnico);
    }

    // Função para ler todos os objetos Dados do banco de dados firebase....
    public void SalvarDadosUsuario() {

        DatabaseReference dadosRef = database.child("tecnicos");

        dadosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tecnico tecnico = snapshot.getValue(Tecnico.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Tratar o erro de leitura dos dados....
                System.out.println("Falha ao ler os dados.");
                databaseError.getMessage();

            }
        });

    }
}
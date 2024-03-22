package com.example.projetofirebase3;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Agenda { // + data + hora + minutos + mes + nome + tecnico + tecnico1 + tecnico2 + tecnico3

    private String data;
    private String hora;
    private String minutos;
    private String mes;
    private String nome;
    private Boolean tecnico1;
    private Boolean tecnico2;
    private Boolean tecnico3;

    public Agenda(String data, String hora, String nome) {

    }

    public Agenda(String data, String hora, String minutos, String mes, String nome, String tecnico, Boolean tecnico1, Boolean tecnico2, Boolean tecnico3) {

        this.data = data;
        this.hora = hora;
        this.minutos = minutos;
        this.mes = mes;
        this.nome = nome;
        this.tecnico1 = tecnico1;
        this.tecnico2 = tecnico2;
        this.tecnico3 = tecnico3;
    }

    public String getValue() {
        return data + hora + minutos + mes + nome + tecnico1 + tecnico2 + tecnico3;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    String getMinutos() {
        return minutos;
    }

    String getMes() {
        return mes;
    }

    String getNome() {
        return nome;
    }

    Boolean getTecnico1() {
        return tecnico1;
    }

    Boolean getTecnico2() {
        return tecnico2;
    }

    Boolean getTecnico3() {
        return tecnico3;
    }

    // Criar uma referência ao banco de dados firebase....
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    // Função para gravar um objeto Dados no banco de dados firabase....
    public void SalvarDadosUsuario(Agenda agenda) {
        String key = database.getKey();
        database.child("agenda").push().getKey();
        // Gravar o objeto Dados no banco de dados firebase sob a chave gerada....

        database.child("agenda").child(key).setValue(agenda);
        // Função para ler todos os objetos Dados do banco de dados firebase....
    }

    // Função para ler todos os objetos Dados do banco de dados firebase....
    public void SalvarDadosUsuario() {
        // Obter uma referência à lista de dados armazenados no banco de dados firebase....

        DatabaseReference dadosRef = database.child("agenda");

        dadosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterar sobre os snapshots e converter cada um em um objeto Dados...
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Agenda agenda = snapshot.getValue(Agenda.class);

                    // Fazer algo com o objetos Dados, como imprimir na tela....
                    System.out.println(agenda.getData());
                    System.out.println(agenda.getHora());
                    System.out.println(agenda.getMinutos());
                    System.out.println(agenda.getMes());
                    System.out.println(agenda.getNome());
                    System.out.println(agenda.getTecnico1());
                    System.out.println(agenda.getTecnico2());
                    System.out.println(agenda.getTecnico3());
                    System.out.println("-------------------------------");
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
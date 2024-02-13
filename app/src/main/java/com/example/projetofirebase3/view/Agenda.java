package com.example.projetofirebase3.view;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Agenda {

    private String data;
    private String hora;
    private String mes;
    private String dia;
    private String tecnico1;
    private String tecnico2;
    private String tecnico3;
    private String nome;

    public Agenda() {

    }
    public Agenda(String data, String hora, String dia, String mes , String nome){

        this.data = String.valueOf(Integer.valueOf(data));
        this.hora = String.valueOf(Integer.valueOf(hora));
        this.tecnico1 = tecnico1;
        this.tecnico2 = tecnico2;
        this.tecnico3 = tecnico3;
        this.mes = String.valueOf(mes);
        this.dia = dia;
        this.nome = nome;

    }

    public String getValue() {
        return data + hora + mes + dia + tecnico1 + tecnico2 + tecnico3 + nome;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
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

    public String getDia() {
        return dia;
    }

    public String getMes() {
        return mes;
    }

    public String getNome() {
        return nome;
    }


    // Criar uma referência ao banco de dados firebase....
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

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
                    System.out.println(agenda.getTecnico1());
                    System.out.println(agenda.getTecnico2());
                    System.out.println(agenda.getTecnico3());
                    System.out.println(agenda.getDia());
                    System.out.println(agenda.getMes()); // year + monthOfYear + dayOfMonth
                    System.out.println(agenda.getNome());
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
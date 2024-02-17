package com.example.projetofirebase3.view;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Agenda {

    private Integer data;
    private Integer hora;
    private Integer dia;
    private Integer minuto;
    private Integer mes;
    private Integer calendar;
    private String nome;
    private String cliente;
    private String tecnico1;
    private String tecnico2;
    private String tecnico3;

    public Agenda() {

    }

    public Agenda(Integer data, Integer hora, Integer dia, Integer minuto, Integer mes, Integer calendar, String nome, String cliente, String tecnico1, String tecnico2, String tecnico3) {

        this.data = data;
        this.hora = hora;
        this.dia = dia;
        this.minuto = minuto;
        this.mes = mes;
        this.calendar = calendar;
        this.nome = nome;
        this.cliente = cliente;
        this.tecnico1 = tecnico1;
        this.tecnico2 = tecnico2;
        this.tecnico3 = tecnico3;

    }

    public String getvalue() {
        return data + hora + dia + minuto + mes + calendar + nome + cliente + tecnico1 + tecnico2 + tecnico3;
    }

    Integer getData() {
        return data;
    }

    Integer getHora() {
        return hora;
    }

    Integer getDia() {
        return dia;
    }

    Integer getMinuto() {
        return minuto;
    }

    Integer getMes() {
        return mes;
    }

    Integer getCalendar() {
        return calendar;
    }

    String getNome() {
        return nome;
    }

    String getCliente() {
        return cliente;
    }

    String getTecnico1() {
        return tecnico1;
    }

    String getTecnico2() {
        return tecnico2;
    }

    String getTecnico3() {
        return tecnico3;
    }

    // Criar uma referência ao banco de dados firebase....
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    // Função para gravar um objeto Dados no banco de dados firabase....
    public void SalvarAgendamento(Agenda agenda) {

        String key = database.getKey();
        database.child("agenda").push().getKey();
        // Gravar o objeto Dados no banco de dados firebase sob a chave gerada....

        database.child("agenda").child(key).setValue(agenda);
        // Função para ler todos os objetos Dados do banco de dados firebase....
    }

    // Função para ler todos os objetos Dados do banco de dados firebase....
    public void SalvarAgendamento() {

        // Obter uma referência à lista de dados armazenados no banco de dados firebase....
        DatabaseReference dadosRef = database.child("agenda");

        dadosRef.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Agenda agenda = snapshot.getValue(Agenda.class);

                    // Fazer algo com o objetos Dados, como imprimir na tela....

                    /* data, hora, dia, minuto, mes, calendar, nome, tecnico1, tecnico2, tecnico3 */

                    System.out.println(agenda.getData());
                    System.out.println(agenda.getHora());
                    System.out.println(agenda.getDia());
                    System.out.println(agenda.getMinuto());
                    System.out.println(agenda.getMes());
                    System.out.println(agenda.getCalendar());
                    System.out.println(agenda.getNome());
                    System.out.println(agenda.getCliente());
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
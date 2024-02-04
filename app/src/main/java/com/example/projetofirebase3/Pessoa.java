package com.example.projetofirebase3;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pessoa {
    private String nomePessoa;
    private String nomeSetor;
    private String siglaSetor;
    private String material;
    private String problema;
    private String quantidade;

    public Pessoa() {

    }

    public Pessoa(String nomePessoa, String nomeSetor, String siglaSetor, String material, String problema, String quantidade) {

        this.nomePessoa = nomePessoa;
        this.nomeSetor = nomeSetor;
        this.siglaSetor = siglaSetor;
        this.material = material;
        this.problema = problema;
        this.quantidade = quantidade;
    }

     public String getValue(){
        return nomePessoa + nomeSetor + siglaSetor + material + quantidade + problema;
     }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    String getSiglaSetor() {
        return siglaSetor;
    }

    String getMaterial() {
        return material;
    }

    String getProblema() {
        return problema;
    }

    String getQuantidade() {
        return quantidade;
    }

    // Criar uma referência ao banco de dados firebase....
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    // Função para gravar um objeto Dados no banco de dados firabase....
    public void SalvarDadosUsuario(Pessoa pessoa) {
        String key = database.getKey();
        database.child("pessoa").push().getKey();
        // Gravar o objeto Dados no banco de dados firebase sob a chave gerada....

        database.child("pessoa").child(key).setValue(pessoa);
        // Função para ler todos os objetos Dados do banco de dados firebase....
    }

    // Função para ler todos os objetos Dados do banco de dados firebase....
    public void SalvarDadosUsuario() {
        // Obter uma referência à lista de dados armazenados no banco de dados firebase....

        DatabaseReference dadosRef = database.child("pessoa");

        dadosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterar sobre os snapshots e converter cada um em um objeto Dados...
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Pessoa pessoa = snapshot.getValue(Pessoa.class);

                    // Fazer algo com o objetos Dados, como imprimir na tela....

                    System.out.println(pessoa.getNomePessoa());
                    System.out.println(pessoa.getNomeSetor());
                    System.out.println(pessoa.getSiglaSetor());
                    System.out.println(pessoa.getProblema());
                    System.out.println(pessoa.getQuantidade());
                    System.out.println(pessoa.getMaterial());
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
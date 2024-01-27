package com.example.projetofirebase3;

public class Pessoa {

    private String nomePessoa;
    private String nomeSetor;
    private String siglaSetor;
    private String material;
    private String problema;
    private String email;
    private int senha;
    private int quantidade;

    public Pessoa(String nomePessoa, String nomeSetor, String siglaSetor, String material, String problema, String quantidade, String email, String senha) {

    }

    public Pessoa(String nomePessoa, String nomeSetor, String siglaSetor, String material, String problema, String email, int senha, int quantidade) {

        this.nomePessoa = nomePessoa;
        this.nomeSetor = nomeSetor;
        this.siglaSetor = siglaSetor;
        this.material = material;
        this.problema = problema;
        this.quantidade = quantidade;
        this.email = email;
        this.senha = senha;
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

    String getEmail() {
        return email;
    }

    public int getSenha() {
        return senha;
    }

    public int getQuantidade() {
        return quantidade;
    }
}

package com.example.projetofirebase3.model;

import java.util.Objects;

public class Servicos {
    private Integer img;
    private String nome;

    public Servicos() {
        this.img = null;
        this.nome = null;
    }

    public Servicos(Integer img, String nome) {
        this.img = img;
        this.nome = nome;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servicos)) return false;
        Servicos servicos = (Servicos) o;
        return Objects.equals(getImg(), servicos.getImg()) &&
                Objects.equals(getNome(), servicos.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImg(), getNome());
    }

    @Override
    public String toString() {
        return "Servicos{" +
                "img=" + img +
                ", nome='" + nome + '\'' +
                '}';
    }
}
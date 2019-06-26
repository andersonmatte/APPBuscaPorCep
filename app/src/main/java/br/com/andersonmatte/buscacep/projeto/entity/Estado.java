package br.com.andersonmatte.buscacep.projeto.entity;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Estado extends RealmObject implements Serializable {

    private Long id;

    private String sigla;

    private String nome;

    private RealmList<String> municipios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RealmList<String> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(RealmList<String> municipios) {
        this.municipios = municipios;
    }

}

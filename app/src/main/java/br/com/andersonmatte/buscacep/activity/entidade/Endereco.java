package br.com.andersonmatte.buscacep.activity.entidade;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Endereco implements Serializable {

    @SerializedName("cep")
    @Expose
    private String cep;

    @SerializedName("logradouro")
    @Expose
    private String logradouro;

    @SerializedName("complemento")
    @Expose
    private String complemento;

    @SerializedName("bairro")
    @Expose
    private String bairro;

    @SerializedName("localidade")
    @Expose
    private String cidade;

    @SerializedName("uf")
    @Expose
    private String uf;

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}

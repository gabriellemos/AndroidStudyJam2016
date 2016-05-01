package com.example.gabrielbl.androidstudyjam.Models;

import java.io.Serializable;

/**
 * Created by gabrielbl on 26/04/16.
 */
public class Movie implements Serializable {

    private String titulo;
    private String descricao;
    private String elenco;
    private String direcao;
    private String duracao;
    private String genero;
    private String classificacao;
    private String dataLancamento;

    private String imgURL;
    private String trailerURL;
    private String filmeURL;

    public Movie() {}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getFilmeURL() {
        return filmeURL;
    }

    public void setFilmeURL(String filmeURL) {
        this.filmeURL = filmeURL;
    }

    @Override
    public String toString() {
        return getTitulo();
    }
}

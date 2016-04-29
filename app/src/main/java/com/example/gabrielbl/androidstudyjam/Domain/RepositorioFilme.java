package com.example.gabrielbl.androidstudyjam.Domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.widget.ArrayAdapter;

import com.example.gabrielbl.androidstudyjam.DataBase.DataBase;
import com.example.gabrielbl.androidstudyjam.Models.Filme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielbl on 26/04/16.
 */
public class RepositorioFilme {

    private SQLiteDatabase conn;
    private static RepositorioFilme instance;

    public RepositorioFilme(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void inserir(Filme filme){

        ContentValues values = new ContentValues();

        values.put("titulo", filme.getTitulo());
        values.put("descricao", filme.getDescricao());
        values.put("elenco", filme.getElenco());
        values.put("direcao", filme.getDirecao());
        values.put("duracao", filme.getDuracao());
        values.put("genero", filme.getGenero());
        values.put("classificacao", filme.getClassificacao());
        values.put("dataLancamento", filme.getDataLancamento());
        values.put("imgUrl", filme.getImgURL());
        values.put("trailerUrl", filme.getTrailerURL());
        values.put("filmeUrl", filme.getFilmeURL());

        conn.insertOrThrow(
                DataBase.FILME_TABLE_NAME, null, values
        );
    }

    public List<Filme> busca() {

        Cursor cursor;
        cursor = conn.query(
            DataBase.FILME_TABLE_NAME, null, null, null, null, null, null
        );

        List<Filme> listFilmes = new ArrayList<>();
        while (cursor.moveToNext()) {
            listFilmes.add(extraiFilme(cursor));
        }
        cursor.close();

        return listFilmes;
    }

    public List<Filme> busca(String selection, String[] selectionArgs) {

        Cursor cursor;
        cursor = conn.query(
                DataBase.FILME_TABLE_NAME, null, selection,
                selectionArgs, null, null, null
        );

        List<Filme> listFilmes = new ArrayList<>();
        while (cursor.moveToNext()) {
            listFilmes.add(extraiFilme(cursor));
        }
        cursor.close();

        return listFilmes;
    }

    private Filme extraiFilme(Cursor cursor) {
        Filme filme = new Filme();

        filme.setTitulo(columnStringValue(cursor, "titulo"));
        filme.setDescricao(columnStringValue(cursor, "descricao"));
        filme.setElenco(columnStringValue(cursor, "elenco"));
        filme.setDirecao(columnStringValue(cursor, "direcao"));
        filme.setDuracao(columnStringValue(cursor, "duracao"));
        filme.setGenero(columnStringValue(cursor, "genero"));
        filme.setClassificacao(columnStringValue(cursor, "classificacao"));
        filme.setDataLancamento(columnStringValue(cursor, "dataLancamento"));
        filme.setImgURL(columnStringValue(cursor, "imgUrl"));
        filme.setTrailerURL(columnStringValue(cursor, "trailerUrl"));
        filme.setFilmeURL(columnStringValue(cursor, "filmeUrl"));

        return filme;
    }

    private String columnStringValue(Cursor cursor, String coluna) {
        return cursor.getString(cursor.getColumnIndex(coluna));
    }

}

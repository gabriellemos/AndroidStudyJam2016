package com.example.gabrielbl.androidstudyjam.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import android.database.sqlite.*;
import android.database.*;

import com.example.gabrielbl.androidstudyjam.DataBase.CineserclaCrowler;
import com.example.gabrielbl.androidstudyjam.DataBase.DataBase;
import com.example.gabrielbl.androidstudyjam.Domain.RepositorioFilme;
import com.example.gabrielbl.androidstudyjam.Models.Filme;
import com.example.gabrielbl.androidstudyjam.R;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private RepositorioFilme repositorioFilme;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CineserclaCrowler(this).execute();

        dataBase = new DataBase(this);
        SQLiteDatabase conn = dataBase.getWritableDatabase();
        repositorioFilme = new RepositorioFilme(conn);

        String[] bla = new String[1];
        bla[0] = "http://www.cinesercla.com.br/filme/detalhes/capitao_america:_guerra_civil_-_pre_venda_de_ingressos";
        List<Filme> listagem = repositorioFilme.busca();//"filmeUrl=?", bla);
        if (listagem.size() > 0) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage(listagem.toString());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("lklklk");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        //mWebView = (WebView) findViewById(R.id.mainWebView);
//
        //// Enable Javascript
        //WebSettings webSettings = mWebView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
//
        //// Stop local links and redirects from opening in browser instead of WebView
        //mWebView.setWebViewClient(new MyAppWebViewClient());
//
        //mWebView.loadUrl("http://www.cinesercla.com.br/");//http://beta.html5test.com/");
    }
}

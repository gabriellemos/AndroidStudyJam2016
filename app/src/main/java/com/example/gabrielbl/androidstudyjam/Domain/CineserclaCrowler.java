package com.example.gabrielbl.androidstudyjam.Domain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.example.gabrielbl.androidstudyjam.Activity.MainActivity;
import com.example.gabrielbl.androidstudyjam.DataBase.DataBase;
import com.example.gabrielbl.androidstudyjam.Domain.MoviesRepository;
import com.example.gabrielbl.androidstudyjam.Models.Movie;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gabrielbl on 26/04/16.
 */
public class CineserclaCrowler extends AsyncTask<Void, Void, Void> {

    private static final String URL_INICIAL = "http://www.cinesercla.com.br/filmes";
    private static final String URL_FILMES = "www.cinesercla.com.br/filme/detalhes/";

    private Context context;
    private DataBase dataBase;
    private MoviesRepository repositorioFilme;

    private boolean showConectionErrorDialog;
    private ProgressDialog pDialog;

    public CineserclaCrowler(Context context) {
        this.context = context;
        dataBase = new DataBase(context);

        SQLiteDatabase conn = dataBase.getWritableDatabase();
        repositorioFilme = new MoviesRepository(conn);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            runCrowler();
        } catch (IOException e) {
            showConectionErrorDialog = true;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        pDialog.dismiss();

        if (showConectionErrorDialog) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setMessage("Connection error. Do you wanna keep trying?");

            dlg.setNegativeButton("NO", null);
            dlg.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((MainActivity) context).refreshMoviesData();
                }
            });
            dlg.show();
        }
    }

    public void runCrowler() throws IOException {

        Document doc;
        String tabEmCartaz = "tabs-1";
        String tabProximos = "tabs-2";

        // Informações da página
        doc = getDocument(URL_INICIAL, 3);

        List<String> urlValidas;
        // Verificando url de filmes 'em cartaz'
        urlValidas = new ArrayList<>(getUrls(doc, tabEmCartaz));

        List<Movie> filmesEmCartaz;
        filmesEmCartaz = new ArrayList<>(getFilmes(urlValidas));

        // Verificando url de filmes 'em breve'
        urlValidas = new ArrayList<>(getUrls(doc, tabProximos));

        List<Movie> filmesProximos;
        filmesProximos = new ArrayList<>(getFilmes(urlValidas));

        System.out.println("Comecando a inserção dos filmes");
        // Atualiza filmes do BD -> Adicionar, remover, editar
        for (Movie filme : filmesEmCartaz) {
            repositorioFilme.inserir(filme);
        }

        for (Movie filme : filmesProximos) {
            repositorioFilme.inserir(filme);
        }
        System.out.println("Finalizado a inserção dos filmes");
    }

    private List<String> getUrls(Document doc, String idElemento)
            throws IOException {

        Elements links;
        String urlTeste;
        List<String> urlValidas = new ArrayList<>();

        links = doc.getElementById(idElemento).select("a[href]");
        for(Element link: links){
            urlTeste = link.attr("abs:href");
            if (urlTeste.contains(URL_FILMES)) {
                urlValidas.add(urlTeste);
            }
        }
        return urlValidas;
    }

    private List<Movie> getFilmes(List<String> urlFilmes)
            throws IOException {

        String parametroBusca = "filmeUrl=?";
        List<Movie> listaRetorno = new ArrayList<>();

        for (String filmeUrl: urlFilmes) {
            String [] args = new String[1];
            args[0] = filmeUrl;

            if (repositorioFilme.busca(parametroBusca, new String[]{filmeUrl}).size() == 0) {
                listaRetorno.add(getFilmeInfo(filmeUrl));
            }
        }

        return listaRetorno;
    }

    private Movie getFilmeInfo(String filmeUrl)
            throws IOException {

        Elements elements;
        String tabInform = "tabst-1";
        String tabTailer = "tabst-2";

        if (!filmeUrl.contains(URL_FILMES)) {
            return null; // LEAVE NOW!
        }

        Movie filme = new Movie();
        filme.setFilmeURL(filmeUrl);

        // Informações da página
        Document doc = getDocument(filmeUrl, 3);

        // Buscando link do trailer
        Element tab = doc.getElementById(tabTailer);
        elements = tab.getElementsByClass("sinopse").select("iframe");
        filme.setTrailerURL(elements.first().attr("src"));

        // Buscando infromçoes do filme
        tab = doc.getElementById(tabInform);
        String prefix = "http://www.cinesercla.com.br";
        filme.setImgURL(prefix + tab.select("div > div > img").attr("src"));

        doc = Jsoup.parse(tab.getElementsByClass("sinopse").html());
        filme.setTitulo(doc.getElementsByClass("tit4").first().text());

        elements = doc.getElementsByClass("tit1");
        Iterator<Element> it = elements.iterator();

        filme.setDescricao(it.next().text().split(": ")[1]);
        filme.setElenco(it.next().text().split(": ")[1]);
        filme.setDirecao(it.next().text().split(": ")[1]);

        String duracao = it.next().text();
        if (duracao.split(": ").length == 2) {
            filme.setDuracao(duracao.split(": ")[1]);
        }

        filme.setGenero(it.next().text().split(": ")[1]);
        filme.setClassificacao(it.next().text().split(": ")[1]);

        if (it.hasNext()) {
            filme.setDataLancamento(it.next().text().split(": ")[1]);
        }
        return filme;
    }

    private Document getDocument(String filmeUrl, int callback) throws IOException {

        Document doc;
        try {
            doc = Jsoup.connect(filmeUrl).get();
        } catch (IOException exception) {
            if (callback > 1) {
                doc = getDocument(filmeUrl, callback - 1);
            } else {
                throw exception;
            }
        }
        return doc;
    }

}

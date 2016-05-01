package com.example.gabrielbl.androidstudyjam.Tests;

import com.example.gabrielbl.androidstudyjam.Models.Movie;

import org.junit.Assert;
import org.junit.Test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {



    @Test
    public void addition_isCorrect() throws Exception {
        Assert.assertEquals(4, 2 + 2);
        System.out.println("asdfff");

        // Em cartaz
        //CineserclaCrowler.runCrowler();
        //processInitialPage("http://www.cinesercla.com.br/filmes");
    }

    private void processInitialPage(String url) throws IOException {

        Document doc;
        Elements links;
        String tabEmCartaz = "tabs-1";
        String tabProximos = "tabs-2";
        String defaultURL = "www.cinesercla.com.br";

        Movie filme;
        List<Movie> filmesEmCartaz = new ArrayList<Movie>();
        List<Movie> filmesProximos = new ArrayList<Movie>();

        if (!url.contains(defaultURL)) {
            return; // LEAVE NOW!
        }

        // Informações da página
        doc = Jsoup.connect(url).get();

        // Verificando url de filmes 'em cartaz'
        links = doc.getElementById(tabEmCartaz).select("a[href]");
        for(Element link: links){
            filme = processaFilmeURL(link.attr("abs:href"));
            if (filme != null) {
                filmesEmCartaz.add(filme);
            }
        }

        System.out.println("Filmes em cartaz:");
        System.out.println(filmesEmCartaz);

        // Verificando url de filmes 'em breve'
        links = doc.getElementById(tabProximos).select("a[href]");
        for(Element link: links){
            filme = processaFilmeURL(link.attr("abs:href"));
            if (filme != null) {
                filmesProximos.add(filme);
            }
        }

        System.out.println("Filmes proximos:");
        System.out.println(filmesProximos);
    }

    private Movie processaFilmeURL(String url) throws IOException {

        Movie filme;
        Element tab;
        Document doc;
        Elements elements;
        String tabInform = "tabst-1";
        String tabTailer = "tabst-2";
        String defaultURL = "www.cinesercla.com.br/filme/detalhes/";

        if (!url.contains(defaultURL)) {
            return null; // LEAVE NOW!
        }

        filme = new Movie();

        // Informações da página
        doc = Jsoup.connect(url).get();

        // Buscando link do trailer
        tab = doc.getElementById(tabTailer);
        elements = tab.getElementsByClass("sinopse").select("iframe");
        filme.setTrailerURL(elements.first().attr("src"));

        // Buscando infromçoes do filme
        tab = doc.getElementById(tabInform);

        filme.setImgURL(tab.select("div > div > img").attr("src"));
        doc = Jsoup.parse(tab.getElementsByClass("sinopse").html());
        elements = doc.getElementsByClass("tit1");

        filme.setTitulo(doc.getElementsByClass("tit4").first().text());

        Iterator<Element> it = elements.iterator();

        filme.setDescricao(it.next().text().split(": ")[1]);
        filme.setElenco(it.next().text().split(": ")[1]);
        filme.setDirecao(it.next().text().split(": ")[1]);

        String duracao = it.next().text();
        if (duracao.split(": ").length == 2)
            filme.setDuracao(duracao.split(": ")[1]);
        else
            duracao = "";

        filme.setGenero(it.next().text().split(": ")[1]);
        filme.setClassificacao(it.next().text().split(": ")[1]);

        String dataLancamento = "";
        if (it.hasNext()) {
            filme.setDataLancamento(it.next().text().split(": ")[1]);
        }
        return filme;
    }
}
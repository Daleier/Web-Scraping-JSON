/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej5_titulares;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author dam203
 */
public class Scrap extends Observable implements Runnable {

    @Override
    public void run() {
        String url = "https://elpais.com/";
        System.out.println("Fetching %s..."+ url);

        Document doc;
        String result="";
        try {
            doc = Jsoup.connect(url).get();
            Elements tit = doc.getElementsByClass("articulo-titulo");
            Elements titulares = tit.select("a");
            
            for(Element link: titulares){
                result = result + "<br/><a href=\"https://elpais.com" + link.attr("href").toString()+"\">"+link.text()+ "<a/><br/>";
                System.out.println("<br/><a href=\"https://elpais.com" + link.attr("href").toString()+">"+link.text()+ "<a/><br/>");
            }
            
            this.setChanged();
            this.notifyObservers(result);
            this.clearChanged();
            System.out.println("SCRAP DONE");
        } catch (IOException ex) {
            Logger.getLogger(Scrap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

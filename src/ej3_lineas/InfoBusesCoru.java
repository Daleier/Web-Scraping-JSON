/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej3_lineas;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class InfoBusesCoru extends Observable implements Runnable {

    String numLinea;
    
    public InfoBusesCoru(String linea){
        numLinea = linea;
    }
    
    @Override
    public void run() {
        ArrayList<String> resultado = new ArrayList();
        String url = "http://www.tranviascoruna.com/lineas-y-horarios/?linea=" + numLinea;
        print("Fetching %s", url);

        Document doc;
        try {
            doc = Jsoup.connect(url).get();

            //Elements links = doc.select("a[href]");
            Elements links = doc.getElementsByTag("a");
            for (Element link : links) {
                if (link.attr("abs:href").lastIndexOf("codpar") != -1) {
                    resultado.add(link.text());
                }
            }
        } catch (IOException ex) {

        }
        for (String elemento : resultado) {
            System.out.println(elemento);
        }

        this.setChanged();
        this.notifyObservers(resultado);
        this.clearChanged();
    }
    
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }



    
}

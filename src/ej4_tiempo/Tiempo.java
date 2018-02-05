/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej4_tiempo;

/**
 *
 * @author dam203
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Observable;

public class Tiempo extends Observable implements Runnable {
    
    String ciudad;
    
    Tiempo(String ciudad){
        this.ciudad = ciudad;
    }
    
    public void run() {
        GeneradorInforme t = new GeneradorInforme();
        if(ciudad.equalsIgnoreCase("A Coruña")){
            ciudad = "a-coruna";
        }

        
        
        try {
            Document document = Jsoup.connect("https://www.eltiempo.es/"+ciudad+".html").get();
            /*El siguiente código permite averiguar qué elementos tiene la columna
        y en qué posición

            Elements columna = document.select("div.m_table_weather_day_wrapper div div.m_table_weather_day_wind");
            System.out.println(columna.size());
            int i = 0;
            for (Element e : columna) {
                System.out.println(e.text());
                System.out.println(i);
                i++;
            }
            */
            Elements tMaxMin = document.select("div.m_table_weather_day_wrapper div div.m_table_weather_day_max_min");
            String stMaxMin = tMaxMin.get(0).text();
            t.tempMax = Integer.parseInt(stMaxMin.split(" ")[0].substring(0, stMaxMin.split(" ")[0].indexOf("°")));
            t.tempMin = Integer.parseInt(stMaxMin.split(" ")[1].substring(0, stMaxMin.split(" ")[1].indexOf("°")));
            Elements temperaturas = document.select("div.m_table_weather_day_wrapper div div.m_table_weather_day_temp_wrapper");
            t.temp08 = Integer.parseInt(temperaturas.get(0).text().substring(0, temperaturas.get(0).text().indexOf("°")));
            t.temp14 = Integer.parseInt(temperaturas.get(1).text().substring(0, temperaturas.get(1).text().indexOf("°")));
            t.temp20 = Integer.parseInt(temperaturas.get(2).text().substring(0, temperaturas.get(2).text().indexOf("°")));
            Elements lluvias = document.select("div.m_table_weather_day_wrapper div div.m_table_weather_day_rain");
            t.lluvia = Float.parseFloat(lluvias.get(0).text().split(" ")[1]);
            Elements hAlba = document.select("div.m_table_weather_day_wrapper div div.m_table_weather_day_dawn");
            t.horaAlba = hAlba.get(0).text();
            Elements hOcaso = document.select("div.m_table_weather_day_wrapper div div.m_table_weather_day_nightfall");
            t.horaOcaso = hOcaso.get(0).text();
            Elements vientos = document.select("div.m_table_weather_day_wrapper div div.m_table_weather_day_wind");
            t.velocidadViento = Integer.parseInt(vientos.get(0).text().split(" ")[1]);
            System.out.println(t.informe());
            setChanged();
            notifyObservers(t.informe());
            clearChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

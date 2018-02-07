/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej2_imagenes;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImages {

    //The url of the website. This is just an example
    private static final String webSiteURL = "https://www.amazon.es";
    //The path of the folder that you want to save the images to
    private static final String folderPath = "imagenes";

    public static void main(String[] args) {
        try {

            //Connect to the website and get the html
            Document doc = Jsoup.connect(webSiteURL).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").get();          
            //Get all elements with img tag ,
            Elements img = doc.getElementsByTag("img");

            for (Element el : img) {

                //for each element get the srs url
                String src = el.absUrl("src");
                System.out.println("Image Found!");
                System.out.println("src attribute is : " + src);

                getImages(src);

            }

        } catch (IOException ex) {
            System.err.println("There was an error");
            Logger.getLogger(DownloadImages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void getImages(String src){
        if (!src.isEmpty()) {
            String folder = null;

            //Exctract the name of the image from the src attribute
            int indexname = src.lastIndexOf("/");

            /*Comprueba si el caracter / aparece al final del string src. En ese caso,
        toma el substring que va del segundo caracter hasta el penúltimo (ambos incluidos).
            "hamburger".substring(4, 8) returns "urge"
             */
            if (indexname == src.length()) {
                src = src.substring(1, indexname);
            }

            indexname = src.lastIndexOf("/");
            String name = src.substring(indexname, src.length());
            System.out.println(name);
            try {
                //Open a URL Stream
                URL url = new URL(src);
                InputStream in = url.openStream();
                name = eliminarCaracteresNoValidos(name);
                OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath + name));

                for (int b; (b = in.read()) != -1;) {
                    out.write(b);
                }
                out.close();
                in.close();
            } catch (MalformedURLException e) {
                System.out.println("URL mal formada");
            } catch (IOException e) {
                System.out.println("Error I/O");
            }
        }
    }

    private static String eliminarCaracteresNoValidos(String s) {
        String newFileName = s.replaceAll("%", "");
        newFileName = newFileName.replaceAll("&", "");
        newFileName = newFileName.replaceAll("=", "");
        newFileName = newFileName.replaceAll("\\?", "");
        /*Esta comprobación debería ser extensible a otros formatos de imagen,
        pero en este caso sólo se comprueba para JPG*/
        if (newFileName.lastIndexOf("jpg") > -1) {
            newFileName = newFileName.substring(0, (newFileName.lastIndexOf("jpg") + 3));
        }
        if (newFileName.length() == 0) {
            throw new IllegalStateException(
                    "File Name " + s + " results in a empty fileName!");
        }
        return newFileName;
    }
}

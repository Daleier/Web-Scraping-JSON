/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej8_cotizacion_euro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dam203
 */
public class CotizacionEuro extends Observable implements Runnable{
    private final String url = "http://www.floatrates.com/daily/eur.json";

    @Override
    public void run() {
        
        String result = "";
        
        try {
            URL url_rate = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url_rate.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader =
                    new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader =
                    new BufferedReader(inputStreamReader, 8192);
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                String rateResult = parseResult(result);
                System.out.println(rateResult);
                this.setChanged();
                this.notifyObservers(rateResult);
                this.clearChanged();
            } else {
                System.out.println("Error in httpURLConnection.getResponseCode()!!!");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CotizacionEuro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CotizacionEuro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CotizacionEuro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String parseResult(String result) throws JSONException {
        String parsedResult = "";
        JSONObject jsonObject = new JSONObject(result);
        
        int numObj = jsonObject.length();
        // ratios usd
        JSONObject usd = jsonObject.getJSONObject("usd");
        String nameUSD = usd.getString("name");
        String rateUSD = Double.toString(usd.getDouble("rate")).substring(0, 5);
        // ratios gbp
        JSONObject gbp = jsonObject.getJSONObject("gbp");
        String nameGBP = gbp.getString("name");
        String rateGBP = Double.toString(gbp.getDouble("rate")).substring(0, 5);
        // ratios jpy
        JSONObject jpy = jsonObject.getJSONObject("jpy");
        String nameJPY = jpy.getString("name");
        String rateJPY = Double.toString(jpy.getDouble("rate")).substring(0, 5);
        // ratios chf
        JSONObject chf = jsonObject.getJSONObject("chf");
        String nameCHF = chf.getString("name");
        String rateCHF = Double.toString(chf.getDouble("rate")).substring(0, 5);       
        // ratios cad
        JSONObject cad = jsonObject.getJSONObject("cad");
        String nameCAD = cad.getString("name");
        String rateCAD = Double.toString(cad.getDouble("rate")).substring(0, 5);
        // ratios cny
        JSONObject cny = jsonObject.getJSONObject("cny");
        String nameCNY = cny.getString("name");
        String rateCNY = Double.toString(cny.getDouble("rate")).substring(0, 5);
        
        parsedResult =
                    nameUSD+": "+rateUSD+
                "\n"+nameGBP+": "+rateGBP+                
                "\n"+nameCAD+": "+rateCAD+
                "\n"+nameCHF+": "+rateCHF+
                "\n"+nameJPY+": "+rateJPY+
                "\n"+nameCNY+": "+rateCNY
                ;
             
        return parsedResult;

    }
    
}

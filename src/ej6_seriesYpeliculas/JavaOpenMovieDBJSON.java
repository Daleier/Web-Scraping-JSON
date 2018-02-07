package ej6_seriesYPeliculas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import org.json.JSONException;
import org.json.JSONObject;

public class JavaOpenMovieDBJSON extends Observable implements Runnable {

    private final String URL_OPEN_MOVIE_DB;
    private final String API_KEY = "5fe8e058";
    
    public JavaOpenMovieDBJSON(String title){
        String movieTitle = title.trim().replace(" ", "+");
        URL_OPEN_MOVIE_DB = "http://www.omdbapi.com/?t="+ movieTitle + "&apikey=" + API_KEY;
    }
    
    @Override
    public void run() {
        String result = "";
        try {
            URL url_weather = new URL(URL_OPEN_MOVIE_DB);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url_weather.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader
                        = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader
                        = new BufferedReader(inputStreamReader, 8192);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();

                String resultado = ParseResult(result);

                System.out.println(resultado);
                this.setChanged();
                notifyObservers(resultado);
                this.clearChanged();
                System.out.println("\nCodigo JSON original:\n" + result);
            } else {
                System.out.println("Error in httpURLConnection.getResponseCode()!!!");
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    static private String ParseResult(String json) throws JSONException {
        String parsedResult = "";
        try{
            JSONObject jsonObject = new JSONObject(json);
            int numObj = jsonObject.length();
            //Obtención del título
            String titulo = jsonObject.getString("Title");
            //Obtención del año
            String año = jsonObject.getString("Year");
            String genero = jsonObject.getString("Genre");
            //Obtención de los actores
            String actores = jsonObject.getString("Actors");
            //Obtención del argumento
            String argumento = jsonObject.getString("Plot");
            parsedResult = "Title: " + titulo +"\nGenre:"+ genero+"\nYear: " + año + "\nActors: " + actores + "\nPlot: " + argumento;
        }catch(JSONException ex) {
            parsedResult = "Movie not found";
            System.out.println(ex.getMessage());
        }
        return parsedResult;

    }

}

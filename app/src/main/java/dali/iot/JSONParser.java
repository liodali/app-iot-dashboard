package dali.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Mohamed ali on 30/01/2017.
 */

public class JSONParser {
    static String json = "";

    // constructor
    public JSONParser() {

    }


    public String makeHttpRequest(String url){


        try {
            URL theurl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) theurl.openConnection();
            BufferedReader buff=new BufferedReader(new InputStreamReader(urlConnection.getInputStream() ,"iso-8859-1"));
            json=buff.readLine();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = buff.readLine()) != null) {
                sb.append(line);
            }
            json=sb.toString();

            buff.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
    public String makeHttpRequest_Post(String url,String data ){

        try {
            URL theurl = new URL(url);

            URLConnection conn = theurl.openConnection();

            conn.setDoOutput(true);

            conn.setRequestProperty("Connection", "Keep-Alive");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader buff=new BufferedReader(new InputStreamReader(conn.getInputStream() ,"iso-8859-1"));
            //if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {

            //}
            json=buff.readLine();
            buff.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}

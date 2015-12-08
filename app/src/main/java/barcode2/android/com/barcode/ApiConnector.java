package barcode2.android.com.barcode;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Deividas on 2015-12-06.
 */
public class ApiConnector{

    public JSONArray getAllInfo(String serverAddress){
        JSONArray array = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(serverAddress);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream in = urlConnection.getInputStream();
            array = readStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    private static JSONArray readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        JSONArray array = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
            try {
                array = new JSONArray(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }
}

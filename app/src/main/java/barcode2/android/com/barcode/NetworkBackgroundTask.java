package barcode2.android.com.barcode;

import android.os.AsyncTask;

import org.json.JSONArray;

/**
 * Created by Deividas on 2015-12-06.
 */
public class NetworkBackgroundTask extends AsyncTask<String, Void, JSONArray>{

    private ApiConnector apiConnector;

    public NetworkBackgroundTask(){
        apiConnector = new ApiConnector();
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        return apiConnector.getAllInfo(strings[0]);
    }
}

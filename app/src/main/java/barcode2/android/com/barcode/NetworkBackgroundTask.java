package barcode2.android.com.barcode;

import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONArray;

/**
 * Created by Deividas on 2015-12-06.
 */
public class NetworkBackgroundTask extends AsyncTask<String, Void, JSONArray>{

    private Context context;
    private ApiConnector apiConnector;

    public NetworkBackgroundTask(Context c){
        context = c;
        apiConnector = new ApiConnector();
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        return apiConnector.getAllInfo(strings[0]);
    }
}

package bws.customerrelation.DAL.Gateway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jacob Ferdinandsen on 25-08-2015.
 */
public class GetJSONFromAPI extends AsyncTask<String, Void, JSONObject> {

    JSONObject jsnobject;
    String result;
    Activity _activity;
    private ProgressDialog _dialog;

    public GetJSONFromAPI(Activity activity) {
        _activity = activity;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader br = null;
        URL url;
        try {
            url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            if (is == null) {
                return null;
            }
            br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            if (sb.length() == 0) {
                return null;
            }
            is.close();
            result = sb.toString();
        } catch (IOException e) {
            Log.e("Get JSON From API IO", "Error ", e);
            return null;
        }
        if (connection != null) {
            connection.disconnect();
        }
        if (br != null) {
            try {
                br.close();
            } catch (final IOException e) {
                Log.e("Get JSON From API IO", "Error closing stream", e);
            }
        }
        try {
            jsnobject = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("Get JSON From API JSON", "Error creating JSON", e);
        }
        return jsnobject; //Rammer 4 gange ?
    }
}

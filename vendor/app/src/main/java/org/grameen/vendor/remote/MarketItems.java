package org.grameen.vendor.remote;

import android.content.Context;
import android.os.AsyncTask;

import org.grameen.vendor.url.Connection;
import org.grameen.vendor.utils.Constants;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Joseph Capito on 6/7/15.
 */
public class MarketItems extends AsyncTask<String, Integer, String> {

    protected Context context;
    public MarketItems(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params){
        String result = "";
        try{
            if(Connection.isConnected(this.context)) {
                String urlData = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("market-items", "UTF-8");
                URL url = new URL(Constants.REMOTE_URL + "/items.php");
                URLConnection urlConnection = url.openConnection();
                Connection.request(urlConnection, urlData);
                result = Connection.response(urlConnection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

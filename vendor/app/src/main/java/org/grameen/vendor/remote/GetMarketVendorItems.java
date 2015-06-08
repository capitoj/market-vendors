package org.grameen.vendor.remote;

import android.os.AsyncTask;

import org.grameen.vendor.url.Connection;
import org.grameen.vendor.utils.Constants;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Joseph Capito on 6/7/15.
 */
public class GetMarketVendorItems extends AsyncTask<String, Integer, String> {

    protected String appID;
    protected String action;
    protected int marketID;

    public GetMarketVendorItems(int marketID){
        this.appID = "";
        this.action = "market-vendors";
        this.marketID = marketID;
    }

    public GetMarketVendorItems(String appID){
        this.appID = appID;
        this.action = "vendor-items";
        this.marketID = 0;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params){
        String result = "";

        try{
            String urlData = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode(this.action, "UTF-8");
            if(this.appID.length() > 0)
                urlData += "&" + URLEncoder.encode("appID", "UTF-8") + "=" + URLEncoder.encode(this.appID, "UTF-8");
            if(this.marketID > 0)
                urlData += "&" + URLEncoder.encode("marketID", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(this.marketID), "UTF-8");

            URL url = new URL(Constants.REMOTE_URL + "/vendors.php");
            URLConnection urlConnection = url.openConnection();
            Connection.request(urlConnection, urlData);
            result = Connection.response(urlConnection);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

package org.grameen.vendor.remote;

import android.content.Context;
import android.os.AsyncTask;

import org.grameen.vendor.url.Connection;
import org.grameen.vendor.utils.Constants;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by niyetu on 6/7/15.
 */
public class MarketVendorItem extends AsyncTask<String, Integer, String> {

    protected String appID;
    protected int itemID;
    protected int price;
    protected int unitID;
    protected Context context;

    public MarketVendorItem(Context context, String appID, int itemID, int price, int unitID){
        this.appID = appID;
        this.itemID = itemID;
        this.price = price;
        this.unitID = unitID;
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
                String urlData = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("add-item", "UTF-8");
                urlData += "&" + URLEncoder.encode("itemID", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(this.itemID), "UTF-8");
                urlData += "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(this.price), "UTF-8");
                urlData += "&" + URLEncoder.encode("unitID", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(this.unitID), "UTF-8");
                urlData += "&" + URLEncoder.encode("appID", "UTF-8") + "=" + URLEncoder.encode(this.appID, "UTF-8");

                URL url = new URL(Constants.REMOTE_URL + "/vendors.php");
                URLConnection urlConnection = url.openConnection();
                Connection.request(urlConnection, urlData);
                result = Connection.response(urlConnection);
            }else{
                result = "Internet disconnected. Check your settings";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

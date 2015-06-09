package org.grameen.vendor.remote;

import android.os.AsyncTask;

import org.grameen.vendor.url.Connection;
import org.grameen.vendor.utils.Constants;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Joseph Capito on 6/6/2015.
 */
public class VendorRegistration extends AsyncTask<String, Integer, String> {

    protected String vendorName;
    protected String stallNumber;
    protected String phoneNumber;
    protected int marketID;

    public VendorRegistration(String vendorName, String stallNumber, String phoneNumber, int marketID){
        this.vendorName = vendorName;
        this.stallNumber = stallNumber;
        this.phoneNumber = phoneNumber;
        this.marketID = marketID;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params){
        String appID = "";
        try{
            String urlData = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("registration", "UTF-8");
            urlData += "&" + URLEncoder.encode("vendorName", "UTF-8") + "=" + URLEncoder.encode(this.vendorName, "UTF-8");
            urlData += "&" + URLEncoder.encode("stallNumber", "UTF-8") + "=" + URLEncoder.encode(this.stallNumber, "UTF-8");
            urlData += "&" + URLEncoder.encode("phoneNumber", "UTF-8") + "=" + URLEncoder.encode(this.phoneNumber, "UTF-8");
            urlData += "&" + URLEncoder.encode("marketID", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(this.marketID), "UTF-8");

            URL url = new URL(Constants.REMOTE_URL + "/vendors.php");
            URLConnection urlConnection = url.openConnection();
            Connection.request(urlConnection, urlData);
            appID = Connection.response(urlConnection);
        }catch(Exception e){
            e.printStackTrace();
        }
        return appID;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }

}

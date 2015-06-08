package org.grameen.vendor.url;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.URLConnection;

/**
 * Created by Irama on 6/6/2015.
 */
public class Connection {

    public static void request(URLConnection urlConnection, String urlQuery) throws Exception{
        urlConnection.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
        outputStreamWriter.write(urlQuery);
        outputStreamWriter.flush();
    }

    public static String response(URLConnection urlConnection) throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return connected;
    }

    public static boolean isReachable(String ipAddress){
        boolean status = false;
        try{
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            status = inetAddress.isReachable(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public static void showStatus(Context context){
        if(!isConnected(context)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Error Connecting...");
            alertDialogBuilder.setMessage("Could not reach the remote server. Check your internet settings");
            alertDialogBuilder.setPositiveButton("OK", null);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}

package com.app.baking.wageeh.bakingapp.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

        public String sendHTTPRequest(String url) {

            if(!connectedToInternet())
                return "";

            Log.d(TAG, "connection start");
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) new URL(url).openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                Scanner scanner = new Scanner(in).useDelimiter("\\A");
                if(!scanner.hasNext())
                    return "";

                return scanner.next();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                assert urlConnection != null;
                urlConnection.disconnect();
            }

            return "";
        }

        /*
        This method is from this stackoverflow answer:
        https://stackoverflow.com/a/9570292
         */
        private boolean connectedToInternet() {
            try {
                InetAddress ipAddr = InetAddress.getByName("google.com");
                return !ipAddr.equals("");

            } catch (Exception e) {
                return false;
            }
        }
}

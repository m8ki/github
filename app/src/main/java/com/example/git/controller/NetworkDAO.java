package com.example.git.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkDAO {
    /**
     * Execute the given URI, and return the data from that URI.
     * @param uri the universal resource indicator for a set of data.
     * @return the set of data provided by the uri
     */
    public String request(String uri) throws IOException {
        StringBuilder sb = new StringBuilder();

        URL url = new URL(uri);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            // temporary string to hold each line read from the reader.
            String inputLine;
            while ((inputLine = bin.readLine()) != null) {
                sb.append(inputLine);
            }
        } finally {
            // regardless of success or failure, we will disconnect from the URLConnection.
            urlConnection.disconnect();
        }
        return sb.toString();
    }
}

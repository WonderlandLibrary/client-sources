package net.silentclient.client.utils;

import net.silentclient.client.Client;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests {
    public static String get(String url) {
        return request("GET", url, null);
    }

    public static String post(String url, String data) {
        return request("POST", url, data);
    }

    public static String post(String url) {
        return request("POST", url, null);
    }

    public static String request(String method, String urlOriginal, String data) {
        try {
            Client.logger.info("Sending a " + method + " Request to a URL: " + urlOriginal + " with data: " + data);
            URL url = new URL(urlOriginal);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("User-Agent", "SilentClient");
            con.setRequestProperty("Authorization", "Bearer " + Client.getInstance().getUserData().getAccessToken());
            if(data != null) {
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = data.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            Client.logger.info(method +  " Request ("+ urlOriginal +") Response: " + content.toString());
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String httpGet(String url) {
        return httpRequest("GET", url, null);
    }

    public static String httpPost(String url, String data) {
        return httpRequest("POST", url, data);
    }

    public static String httpPost(String url) {
        return httpRequest("POST", url, null);
    }

    public static String httpRequest(String method, String urlOriginal, String data) {
        try {
            Client.logger.info("Sending a HTTP " + method + " Request to a URL: " + urlOriginal + " with data: " + data);
            URL url = new URL(urlOriginal);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("User-Agent", "SilentClient");
            con.setRequestProperty("Authorization", "Bearer " + Client.getInstance().getUserData().getAccessToken());
            if(data != null) {
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = data.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            Client.logger.info("HTTP " + method +  " Request ("+ urlOriginal +") Response: " + content.toString());
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

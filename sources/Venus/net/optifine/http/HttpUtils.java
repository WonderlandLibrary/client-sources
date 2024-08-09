/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.optifine.Config;

public class HttpUtils {
    private static String playerItemsUrl = null;
    public static final String SERVER_URL = "http://s.optifine.net";
    public static final String POST_URL = "http://optifine.net";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] get(String string) throws IOException {
        byte[] byArray;
        HttpURLConnection httpURLConnection = null;
        try {
            int n;
            URL uRL = new URL(string);
            httpURLConnection = (HttpURLConnection)uRL.openConnection(Minecraft.getInstance().getProxy());
            httpURLConnection.setDoInput(false);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() / 100 != 2) {
                if (httpURLConnection.getErrorStream() != null) {
                    Config.readAll(httpURLConnection.getErrorStream());
                }
                throw new IOException("HTTP response: " + httpURLConnection.getResponseCode());
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] byArray2 = new byte[httpURLConnection.getContentLength()];
            int n2 = 0;
            do {
                if ((n = inputStream.read(byArray2, n2, byArray2.length - n2)) >= 0) continue;
                throw new IOException("Input stream closed: " + string);
            } while ((n2 += n) < byArray2.length);
            byArray = byArray2;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return byArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String post(String string, Map map, byte[] byArray) throws IOException {
        String string2;
        HttpURLConnection httpURLConnection = null;
        try {
            String string3;
            Object object;
            Object object22;
            URL uRL = new URL(string);
            httpURLConnection = (HttpURLConnection)uRL.openConnection(Minecraft.getInstance().getProxy());
            httpURLConnection.setRequestMethod("POST");
            if (map != null) {
                for (Object object22 : map.keySet()) {
                    object = "" + map.get(object22);
                    httpURLConnection.setRequestProperty((String)object22, (String)object);
                }
            }
            httpURLConnection.setRequestProperty("Content-Type", "text/plain");
            httpURLConnection.setRequestProperty("Content-Length", "" + byArray.length);
            httpURLConnection.setRequestProperty("Content-Language", "en-US");
            httpURLConnection.setUseCaches(true);
            httpURLConnection.setDoInput(false);
            httpURLConnection.setDoOutput(false);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(byArray);
            outputStream.flush();
            outputStream.close();
            object22 = httpURLConnection.getInputStream();
            object = new InputStreamReader((InputStream)object22, "ASCII");
            BufferedReader bufferedReader = new BufferedReader((Reader)object);
            StringBuffer stringBuffer = new StringBuffer();
            while ((string3 = bufferedReader.readLine()) != null) {
                stringBuffer.append(string3);
                stringBuffer.append('\r');
            }
            bufferedReader.close();
            string2 = stringBuffer.toString();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return string2;
    }

    public static synchronized String getPlayerItemsUrl() {
        if (playerItemsUrl == null) {
            try {
                boolean bl = Config.parseBoolean(System.getProperty("player.models.local"), false);
                if (bl) {
                    File file = Minecraft.getInstance().gameDir;
                    File file2 = new File(file, "playermodels");
                    playerItemsUrl = file2.toURI().toURL().toExternalForm();
                }
            } catch (Exception exception) {
                Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
            }
            if (playerItemsUrl == null) {
                playerItemsUrl = SERVER_URL;
            }
        }
        return playerItemsUrl;
    }
}


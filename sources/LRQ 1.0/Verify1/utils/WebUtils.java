/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.Pointer
 *  com.sun.jna.win32.StdCallLibrary
 *  com.sun.jna.win32.StdCallLibrary$StdCallCallback
 */
package Verify1.utils;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtils {
    public static String get(String url) throws IOException {
        String inputLine;
        HttpURLConnection con = (HttpURLConnection)new URL(url).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append("\n");
        }
        in.close();
        return response.toString();
    }

    public static String readContent(String stringURL) throws IOException {
        String line;
        HttpURLConnection httpConnection = stringURL.toLowerCase().startsWith("https://") ? (HttpURLConnection)new URL(stringURL).openConnection() : (HttpURLConnection)new URL(stringURL).openConnection();
        httpConnection.setConnectTimeout(10000);
        httpConnection.setReadTimeout(10000);
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        HttpURLConnection.setFollowRedirects(true);
        httpConnection.setDoOutput(true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static interface User32
    extends StdCallLibrary {
        public static final User32 INSTANCE = (User32)Native.loadLibrary((String)"user32", User32.class);

        public boolean EnumWindows(WNDENUMPROC var1, Pointer var2);

        public int GetWindowTextA(Pointer var1, byte[] var2, int var3);

        public static interface WNDENUMPROC
        extends StdCallLibrary.StdCallCallback {
            public boolean callback(Pointer var1, Pointer var2);
        }
    }
}


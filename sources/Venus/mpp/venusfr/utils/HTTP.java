/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class HTTP {
    private static final HashMap<Integer, FloatBuffer> kernelCache = new HashMap();

    public static String getHTTP(String string) {
        try {
            String string2;
            URL uRL = new URL(string);
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((string2 = bufferedReader.readLine()) != null) {
                stringBuilder.append(string2);
            }
            bufferedReader.close();
            String string3 = stringBuilder.toString();
            httpURLConnection.disconnect();
            return string3;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }
}


/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;

public class HttpUtils {
    public static final String SERVER_URL = "http://s.optifine.net";
    public static final String POST_URL = "http://optifine.net";

    public static byte[] get(String urlStr) throws IOException {
        HttpURLConnection conn = null;
        try {
            int len;
            byte[] len1;
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.connect();
            if (conn.getResponseCode() / 100 != 2) {
                throw new IOException("HTTP response: " + conn.getResponseCode());
            }
            InputStream in = conn.getInputStream();
            byte[] bytes = new byte[conn.getContentLength()];
            int pos = 0;
            do {
                if ((len = in.read(bytes, pos, bytes.length - pos)) >= 0) continue;
                throw new IOException("Input stream closed: " + urlStr);
            } while ((pos += len) < bytes.length);
            byte[] arrby = len1 = bytes;
            return arrby;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String post(String urlStr, Map headers, byte[] content) throws IOException {
        HttpURLConnection conn = null;
        try {
            String line;
            String var11;
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
            conn.setRequestMethod("POST");
            if (headers != null) {
                Set os = headers.keySet();
                for (String isr : os) {
                    String br = "" + headers.get(isr);
                    conn.setRequestProperty(isr, br);
                }
            }
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Content-Length", "" + content.length);
            conn.setRequestProperty("Content-Language", "en-US");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os1 = conn.getOutputStream();
            os1.write(content);
            os1.flush();
            os1.close();
            InputStream in1 = conn.getInputStream();
            InputStreamReader isr1 = new InputStreamReader(in1, "ASCII");
            BufferedReader br1 = new BufferedReader(isr1);
            StringBuffer sb = new StringBuffer();
            while ((line = br1.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
            }
            br1.close();
            String string = var11 = sb.toString();
            return string;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}


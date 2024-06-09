/*
 * Decompiled with CFR 0.145.
 */
package optifine;

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
import optifine.Config;

public class HttpUtils {
    public static final String SERVER_URL = "http://s.optifine.net";
    public static final String POST_URL = "http://optifine.net";

    public static byte[] get(String urlStr) throws IOException {
        HttpURLConnection conn = null;
        try {
            byte[] len1;
            int len;
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.connect();
            if (conn.getResponseCode() / 100 != 2) {
                if (conn.getErrorStream() != null) {
                    Config.readAll(conn.getErrorStream());
                }
                throw new IOException("HTTP response: " + conn.getResponseCode());
            }
            InputStream in2 = conn.getInputStream();
            byte[] bytes = new byte[conn.getContentLength()];
            int pos = 0;
            do {
                if ((len = in2.read(bytes, pos, bytes.length - pos)) >= 0) continue;
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
                Set os2 = headers.keySet();
                for (String isr : os2) {
                    String br2 = "" + headers.get(isr);
                    conn.setRequestProperty(isr, br2);
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
            StringBuffer sb2 = new StringBuffer();
            while ((line = br1.readLine()) != null) {
                sb2.append(line);
                sb2.append('\r');
            }
            br1.close();
            String string = var11 = sb2.toString();
            return string;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}


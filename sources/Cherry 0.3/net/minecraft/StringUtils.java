// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class StringUtils
{
    public static String getText(final String url) throws Exception {
        final URL website = new URL(url);
        final URLConnection connection = website.openConnection();
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    
    public static List<String> getWebsite(final String url) throws Exception {
        final List<String> lines = new ArrayList<String>();
        final URL website = new URL(url);
        final URLConnection connection = website.openConnection();
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            lines.add(inputLine);
        }
        in.close();
        return lines;
    }
    
    public static String callURL(final String myURL) {
        System.out.println("Requeted URL:" + myURL);
        final StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            final URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null) {
                urlConn.setReadTimeout(60000);
            }
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
                final BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char)cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        }
        catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }
        return sb.toString();
    }
}

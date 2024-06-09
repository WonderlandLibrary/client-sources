// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.utilities;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileHelper
{
    public static void download(final String link, final String directory) {
        try {
            final URL url = new URL(link);
            final HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
            final int responseCode = httpConn.getResponseCode();
            if (responseCode == 200) {
                String fileName = "";
                final String disposition = httpConn.getHeaderField("Content-Disposition");
                httpConn.getContentType();
                httpConn.getContentLength();
                if (disposition != null) {
                    final int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                }
                else {
                    fileName = link.substring(link.lastIndexOf("/") + 1, link.length());
                }
                final InputStream inputStream = httpConn.getInputStream();
                final String saveFilePath = String.valueOf(directory) + File.separator + fileName;
                final FileOutputStream outputStream = new FileOutputStream(saveFilePath);
                int bytesRead = -1;
                final byte[] buffer = new byte[63];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
            }
            httpConn.disconnect();
        }
        catch (IOException ex) {}
    }
}

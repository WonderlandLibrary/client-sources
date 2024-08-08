package me.xatzdevelopments.util.security;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;

public class DownloadUrl
{
    public static void main(final String[] args) {
        final String dirName = "";
        try {
            saveFileFromUrlWithJavaIO(String.valueOf(dirName) + "FlowerImage.jpeg", "https://images.pexels.com/photos/736230/pexels-photo-736230.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
            System.out.println("finished");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public static void saveFileFromUrlWithJavaIO(final String fileName, final String fileUrl) throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(fileName);
            final byte[] data = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        }
        finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        if (in != null) {
            in.close();
        }
        if (fout != null) {
            fout.close();
        }
    }
    
    public static void saveFileFromUrlWithCommonsIO(final String fileName, final String fileUrl) throws MalformedURLException, IOException {
        FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
    }
}

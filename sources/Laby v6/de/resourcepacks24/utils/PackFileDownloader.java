package de.resourcepacks24.utils;

import de.resourcepacks24.main.Pack;
import de.resourcepacks24.main.ResourcePacks24;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.Minecraft;

public class PackFileDownloader extends Thread
{
    PackInfoCallback callBack;
    Pack pack;

    public PackFileDownloader(Pack pack, PackInfoCallback callBack)
    {
        this.callBack = callBack;
        this.pack = pack;
        this.start();
    }

    public void run()
    {
        File file1 = Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks();
        String s = file1.getAbsolutePath();

        try
        {
            this.countDownload();
            this.downloadFile(ResourcePacks24.creator_home + this.pack.getCreator() + "/rp_d/" + this.pack.getHashName() + ".zip", new File(s, this.pack.getBestPossibleName() + ".zip"));
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        this.callBack.progress(100);
        ResourcePacks24.getInstance().getDeletedPacks().remove(file1.getName());
    }

    private boolean countDownload()
    {
        try
        {
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(ResourcePacks24.download_count + this.pack.getId() + "&username=" + Minecraft.getMinecraft().getSession().getUsername())).openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            httpurlconnection.setRequestProperty("Cookie", "foo=bar");
            httpurlconnection.connect();

            if (httpurlconnection.getResponseCode() / 100 == 2)
            {
                return true;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return false;
    }

    private void downloadFile(String s, File dest) throws IOException
    {
        URL url = new URL(s);
        URLConnection urlconnection = url.openConnection();
        urlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        long i = (long)urlconnection.getContentLength();
        BufferedInputStream bufferedinputstream = new BufferedInputStream(urlconnection.getInputStream());
        FileOutputStream fileoutputstream = new FileOutputStream(dest);
        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream, 1024);
        byte[] abyte = new byte[1024];
        long j = 0L;
        int k = 0;

        while ((k = bufferedinputstream.read(abyte, 0, 1024)) >= 0)
        {
            j += (long)k;
            int l = (int)((double)j / (double)i * 100.0D);

            if (l < 0)
            {
                l = 2;
            }

            this.callBack.progress(l);
            bufferedoutputstream.write(abyte, 0, k);
        }

        bufferedoutputstream.close();
        bufferedinputstream.close();
    }
}

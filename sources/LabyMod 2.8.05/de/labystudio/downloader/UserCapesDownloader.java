package de.labystudio.downloader;

import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Debug;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class UserCapesDownloader
{
    public UserCapesDownloader()
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                if (!UserCapesDownloader.this.downloadUserCapes("http://info.labymod.net/php/userCapes.json"))
                {
                    System.out.println("Can\'t download usercapes.");
                }
            }
        };
        thread.setPriority(1);
        thread.start();
    }

    private boolean downloadUserCapes(String page)
    {
        try
        {
            System.out.println("[UserCapes] Download all usercapes..");
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(page)).openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            httpurlconnection.setRequestProperty("Cookie", "foo=bar");
            httpurlconnection.connect();
            Debug.debug("[UserCapes] Response: " + httpurlconnection.getResponseCode());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), Charset.forName("UTF-8")));
            String s1 = "";
            String s;
            ArrayList<String> arraylist;

            for (arraylist = new ArrayList(); (s = bufferedreader.readLine()) != null; s1 = s1 + s)
            {
                ;
            }

            String[] astring = s1.split(";");

            for (String s2 : astring)
            {
                arraylist.add(s2);
            }

            Debug.debug("[UserCapes] Total usercapes: " + arraylist.size());
            LabyMod.getInstance().getCapeManager().setUserCapes(arraylist);
            return true;
        }
        catch (Exception exception)
        {
            Debug.debug("[UserCapes] Error: " + exception.getMessage());
            exception.printStackTrace();
            return false;
        }
    }
}

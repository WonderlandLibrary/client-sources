package de.labystudio.downloader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Debug;
import de.labystudio.utils.ServerBroadcast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class ModInfoDownloader
{
    public ModInfoDownloader()
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                if (!ModInfoDownloader.this.downloadModInfo("http://info.labymod.net/php/modInfo.php"))
                {
                    System.out.println("Can\'t download the mod info.");
                }
            }
        };
        thread.setPriority(1);
        thread.start();
    }

    private boolean downloadModInfo(String page)
    {
        try
        {
            System.out.println("[ModInfo] Downloading Modinfo..");
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(page + "?ver=" + "1.8.8" + "&lmver=" + "2.8.05")).openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            httpurlconnection.setRequestProperty("Cookie", "foo=bar");
            httpurlconnection.connect();
            Debug.debug("[ModInfo] Response: " + httpurlconnection.getResponseCode());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), Charset.forName("UTF-8")));
            String s;
            String s1;

            for (s1 = ""; (s = bufferedreader.readLine()) != null; s1 = s1 + s)
            {
                ;
            }

            Debug.debug("[ModInfo] Content length: " + s1.length());
            LabyMod.getInstance().autoUpdaterCurrentVersionId = Integer.parseInt("2.8.05".replace(".", ""));
            JsonParser jsonparser = new JsonParser();
            JsonElement jsonelement = jsonparser.parse(s1);
            String s2 = jsonelement.getAsJsonObject().get("latest_version").getAsString();
            LabyMod.getInstance().latestVersionName = s2;
            boolean flag = jsonelement.getAsJsonObject().get("support_apply").getAsBoolean();
            LabyMod.getInstance().supportApply = flag;
            LabyMod.getInstance().autoUpdaterLatestVersionId = Integer.parseInt(s2.replace(".", ""));
            System.out.println("[LabyMod] The latest LabyMod version is v" + LabyMod.getInstance().latestVersionName + ", you are currently using LabyMod version v" + "2.8.05");

            if (LabyMod.getInstance().autoUpdaterLatestVersionId > LabyMod.getInstance().autoUpdaterCurrentVersionId)
            {
                System.out.println("[LabyMod] You are outdated!");
            }
            else
            {
                System.out.println("[LabyMod] You are using the latest version.");
            }

            try
            {
                for (JsonElement jsonelement1 : jsonelement.getAsJsonObject().get("broadcast").getAsJsonArray())
                {
                    String s3 = jsonelement1.getAsJsonObject().get("line1").getAsString();
                    String s4 = jsonelement1.getAsJsonObject().get("line2").getAsString();
                    String s5 = jsonelement1.getAsJsonObject().get("url").getAsString();
                    LabyMod.getInstance().setServerBroadcast(new ServerBroadcast(s3, s4, s5));
                    System.out.println("[LabyMod] Loaded LabyMod server broadcast");
                }
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
                System.out.println("[LabyMod] Failed to load broadcast: " + exception1.getMessage());
            }

            try
            {
                String s6 = jsonelement.getAsJsonObject().get("texture").getAsString();

                for (JsonElement jsonelement2 : jsonelement.getAsJsonObject().get("skins").getAsJsonArray())
                {
                    String s7 = jsonelement2.getAsJsonObject().get("user").getAsString();
                    LabyMod.getInstance().dumb.add(s7);
                }

                LabyMod.getInstance().dumb_str = s6;
            }
            catch (Exception exception)
            {
                Debug.debug("[ModInfo] Failed to load skins: " + exception.getMessage());
                exception.printStackTrace();
            }

            return true;
        }
        catch (Exception exception2)
        {
            Debug.debug("[ModInfo] Error: " + exception2.getMessage());
            LabyMod.getInstance().autoUpdaterLatestVersionId = LabyMod.getInstance().autoUpdaterCurrentVersionId;
            exception2.printStackTrace();
            return false;
        }
    }
}

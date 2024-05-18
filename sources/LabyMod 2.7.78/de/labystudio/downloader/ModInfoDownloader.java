package de.labystudio.downloader;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import de.labystudio.cosmetic.Cosmetic;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import de.labystudio.utils.Debug;
import de.labystudio.utils.ServerBroadcast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class ModInfoDownloader
{
    public ModInfoDownloader()
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                if (!ModInfoDownloader.this.downloadModInfo(Source.url_mod_info))
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
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(page)).openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            httpurlconnection.setRequestProperty("Cookie", "foo=bar");
            httpurlconnection.connect();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), Charset.forName("UTF-8")));
            String s;
            String s1;

            for (s1 = ""; (s = bufferedreader.readLine()) != null; s1 = s1 + s)
            {
                ;
            }

            LabyMod.getInstance().autoUpdaterCurrentVersionId = Integer.parseInt(Source.mod_VersionName.replace(".", ""));
            JsonParser jsonparser = new JsonParser();
            JsonElement jsonelement = jsonparser.parse(s1);
            String s2 = jsonelement.getAsJsonObject().get("latest_version").getAsString();
            LabyMod.getInstance().latestVersionName = s2;
            LabyMod.getInstance().autoUpdaterLatestVersionId = Integer.parseInt(s2.replace(".", ""));
            System.out.println("[LabyMod] The latest LabyMod version is v" + LabyMod.getInstance().latestVersionName + ", you are currently using LabyMod version v" + Source.mod_VersionName);

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
                for (JsonElement jsonelement1 : jsonelement.getAsJsonObject().get("cosmetics").getAsJsonArray())
                {
                    UUID uuid = UUID.fromString(jsonelement1.getAsJsonObject().get("user_id").getAsString());

                    if (jsonelement1.getAsJsonObject().get("enabled").getAsInt() == 1)
                    {
                        int i = jsonelement1.getAsJsonObject().get("cosmetic_id").getAsInt();
                        JsonElement jsonelement2 = jsonelement1.getAsJsonObject().get("data");
                        ArrayList<Cosmetic> arraylist = new ArrayList();

                        if (LabyMod.getInstance().getCosmeticManager().getCosmetics().containsKey(uuid))
                        {
                            arraylist.addAll((Collection)LabyMod.getInstance().getCosmeticManager().getCosmetics().get(uuid));
                        }

                        if (jsonelement2 instanceof JsonNull)
                        {
                            arraylist.add(new Cosmetic(i));
                            LabyMod.getInstance().getCosmeticManager().getCosmetics().put(uuid, arraylist);
                        }
                        else
                        {
                            String s3 = jsonelement2.getAsString();
                            arraylist.add(new Cosmetic(i, s3));
                            LabyMod.getInstance().getCosmeticManager().getCosmetics().put(uuid, arraylist);
                        }
                    }
                }

                if (Debug.capes())
                {
                    System.out.println("[LabyMod] Loaded " + LabyMod.getInstance().getCosmeticManager().getCosmetics().size() + " cosmetics");
                }
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
                System.out.println("[LabyMod] Failed to load cosmetics");
            }

            try
            {
                for (JsonElement jsonelement3 : jsonelement.getAsJsonObject().get("broadcast").getAsJsonArray())
                {
                    String s4 = jsonelement3.getAsJsonObject().get("line1").getAsString();
                    String s5 = jsonelement3.getAsJsonObject().get("line2").getAsString();
                    String s6 = jsonelement3.getAsJsonObject().get("url").getAsString();
                    LabyMod.getInstance().setServerBroadcast(new ServerBroadcast(s4, s5, s6));
                    System.out.println("[LabyMod] Loaded LabyMod server broadcast");
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                System.out.println("[LabyMod] Failed to load broadcast");
            }

            return true;
        }
        catch (Exception exception2)
        {
            LabyMod.getInstance().autoUpdaterLatestVersionId = LabyMod.getInstance().autoUpdaterCurrentVersionId;
            exception2.printStackTrace();
            return false;
        }
    }
}

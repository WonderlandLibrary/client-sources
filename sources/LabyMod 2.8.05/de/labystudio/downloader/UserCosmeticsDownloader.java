package de.labystudio.downloader;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import de.labystudio.cosmetic.Cosmetic;
import de.labystudio.cosmetic.CosmeticUser;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Debug;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class UserCosmeticsDownloader
{
    public UserCosmeticsDownloader()
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                if (!UserCosmeticsDownloader.this.downloadCosmetics("http://info.labymod.net/php/userCosmetics.json"))
                {
                    System.out.println("Can\'t download cosmetics.");
                }
            }
        };
        thread.setPriority(1);
        thread.start();
    }

    private boolean downloadCosmetics(String page)
    {
        try
        {
            System.out.println("[ModInfo] Downloading Cosmetics..");
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(page)).openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            httpurlconnection.setRequestProperty("Cookie", "foo=bar");
            httpurlconnection.connect();
            Debug.debug("[Cosmetics] Response: " + httpurlconnection.getResponseCode());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), Charset.forName("UTF-8")));
            String s;
            String s1;

            for (s1 = ""; (s = bufferedreader.readLine()) != null; s1 = s1 + s)
            {
                ;
            }

            Debug.debug("[Cosmetics] Response: " + httpurlconnection.getResponseCode());
            JsonParser jsonparser = new JsonParser();
            JsonElement jsonelement = jsonparser.parse(s1);

            try
            {
                HashMap<String, CosmeticUser> hashmap = LabyMod.getInstance().getCosmeticManager().getOnlineCosmetics();
                HashMap<String, CosmeticUser> hashmap1 = LabyMod.getInstance().getCosmeticManager().getOfflineCosmetics();
                hashmap.clear();

                for (JsonElement jsonelement1 : jsonelement.getAsJsonArray())
                {
                    try
                    {
                        String s2 = jsonelement1.getAsJsonObject().get("user_id").getAsString();

                        if (jsonelement1.getAsJsonObject().get("enabled").getAsInt() == 1)
                        {
                            int i = jsonelement1.getAsJsonObject().get("cosmetic_id").getAsInt();
                            JsonElement jsonelement2 = jsonelement1.getAsJsonObject().get("data");
                            CosmeticUser cosmeticuser = (CosmeticUser)hashmap.get(s2);
                            CosmeticUser cosmeticuser1 = cosmeticuser == null ? new CosmeticUser() : cosmeticuser;

                            if (jsonelement2 instanceof JsonNull)
                            {
                                cosmeticuser1.addToCosmeticList(new Cosmetic(i));
                            }
                            else
                            {
                                String s3 = jsonelement2.getAsString();
                                cosmeticuser1.addToCosmeticList(new Cosmetic(i, s3));
                            }

                            cosmeticuser1.updateData();

                            if (cosmeticuser == null)
                            {
                                hashmap.put(s2, cosmeticuser1);
                            }
                        }
                    }
                    catch (Exception exception)
                    {
                        Debug.debug("[Cosmetics] Failed to parse: " + exception.getMessage());
                        exception.printStackTrace();
                    }
                }

                for (String s4 : hashmap.keySet())
                {
                    hashmap1.put(s4, new CosmeticUser(new ArrayList(((CosmeticUser)hashmap.get(s4)).getCosmeticsData())));
                }

                Debug.debug("[Cosmetics] Loaded " + hashmap.size() + " cosmetics");
                LabyMod.getInstance().getCosmeticManager().load();
                return true;
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
                Debug.debug("[Cosmetics] Failed to load cosmetics: " + exception1.getMessage());
            }
        }
        catch (Exception exception2)
        {
            Debug.debug("[Cosmetics] Failed to load cosmetics: " + exception2.getMessage());
            exception2.printStackTrace();
        }

        LabyMod.getInstance().getCosmeticManager().load();
        return false;
    }
}

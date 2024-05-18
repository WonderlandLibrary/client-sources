package de.resourcepacks24.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.resourcepacks24.main.Pack;
import de.resourcepacks24.main.ResourcePacks24;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class PackInfoDownloader extends Thread
{
    PackInfoCallback callBack;
    ArrayList<Pack> out;

    public PackInfoDownloader(PackInfoCallback callBack)
    {
        this.callBack = callBack;
        this.start();
    }

    public void run()
    {
        this.out = this.downloadInfo();

        if (this.out == null)
        {
            this.out = new ArrayList();
        }
        else
        {
            this.addDesc();
        }

        this.callBack.progress(100);
        this.callBack.result(this.out);
    }

    public ArrayList<Pack> downloadInfo()
    {
        try
        {
            this.callBack.progress(0);
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(ResourcePacks24.api_packs + "&v=2")).openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            httpurlconnection.setRequestProperty("Cookie", "foo=bar");
            this.callBack.progress(10);
            httpurlconnection.connect();
            this.callBack.progress(20);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), Charset.forName("UTF-8")));
            this.callBack.progress(30);
            String s;
            String s1;

            for (s1 = ""; (s = bufferedreader.readLine()) != null; s1 = s1 + s)
            {
                ;
            }

            this.callBack.progress(40);
            JsonParser jsonparser = new JsonParser();
            JsonElement jsonelement = jsonparser.parse(s1);
            JsonArray jsonarray = jsonelement.getAsJsonObject().get("rplist").getAsJsonArray();
            ArrayList<Pack> arraylist = new ArrayList();

            for (JsonElement jsonelement1 : jsonarray)
            {
                String s2 = "?";

                try
                {
                    int i = jsonelement1.getAsJsonObject().get("rp_id").getAsInt();
                    s2 = jsonelement1.getAsJsonObject().get("rp_name").getAsString();

                    try
                    {
                        String s3 = null;
                        JsonObject jsonobject = jsonelement1.getAsJsonObject();
                        JsonElement jsonelement2 = jsonobject.get("rp_name_ingame");

                        if (!(jsonelement2 instanceof JsonNull))
                        {
                            s3 = jsonelement2.getAsString();
                        }

                        String s4 = null;
                        JsonElement jsonelement3 = jsonelement1.getAsJsonObject().get("rp_new_name");

                        if (!(jsonelement3 instanceof JsonNull))
                        {
                            s4 = jsonelement3.getAsString();
                        }

                        long j = jsonelement1.getAsJsonObject().get("rp_up_time").getAsLong();
                        String s5 = jsonelement1.getAsJsonObject().get("uploaded_by").getAsString();
                        String s6 = jsonelement1.getAsJsonObject().get("rp_mb").getAsString();
                        int k = jsonelement1.getAsJsonObject().get("rp_status").getAsInt();
                        int l = jsonelement1.getAsJsonObject().get("rp_downloads").getAsInt();
                        String s7 = jsonelement1.getAsJsonObject().get("hashname").getAsString();
                        int i1 = jsonelement1.getAsJsonObject().get("premium_id").getAsInt();
                        int j1 = jsonelement1.getAsJsonObject().get("rp_vote").getAsInt();
                        int k1 = jsonelement1.getAsJsonObject().get("rp_tag_hg").getAsInt();
                        int l1 = jsonelement1.getAsJsonObject().get("rp_tag_sg").getAsInt();
                        int i2 = jsonelement1.getAsJsonObject().get("rp_tag_uhc").getAsInt();
                        int j2 = jsonelement1.getAsJsonObject().get("rp_tag_pot").getAsInt();
                        int k2 = jsonelement1.getAsJsonObject().get("rp_tag_pvp").getAsInt();
                        int l2 = jsonelement1.getAsJsonObject().get("rp_tag_bedwars").getAsInt();
                        int i3 = jsonelement1.getAsJsonObject().get("rp_tag_skywars").getAsInt();
                        int j3 = jsonelement1.getAsJsonObject().get("rp_tag_smyp").getAsInt();
                        int k3 = jsonelement1.getAsJsonObject().get("rp_tag_yt").getAsInt();

                        if (!s6.contains(" "))
                        {
                            s6 = s6 + " MB";
                        }

                        arraylist.add(new Pack(i, s2, s3, s4, j, s5, s6, k, l, s7, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3));
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
                catch (Exception exception1)
                {
                    exception1.printStackTrace();
                    System.out.println("Failed to load " + s2);
                }
            }

            return arraylist;
        }
        catch (Exception exception2)
        {
            exception2.printStackTrace();
            return null;
        }
    }

    public void addDesc()
    {
        try
        {
            this.callBack.progress(50);
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(ResourcePacks24.api_packs + "&v=3")).openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            httpurlconnection.setRequestProperty("Cookie", "foo=bar");
            this.callBack.progress(60);
            httpurlconnection.connect();
            this.callBack.progress(70);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), Charset.forName("UTF-8")));
            this.callBack.progress(80);
            String s;
            String s1;

            for (s1 = ""; (s = bufferedreader.readLine()) != null; s1 = s1 + s)
            {
                ;
            }

            this.callBack.progress(90);
            JsonParser jsonparser = new JsonParser();
            JsonElement jsonelement = jsonparser.parse(s1);

            for (JsonElement jsonelement1 : jsonelement.getAsJsonObject().get("rplist").getAsJsonArray())
            {
                try
                {
                    int i = jsonelement1.getAsJsonObject().get("rp_id").getAsInt();
                    String s2 = jsonelement1.getAsJsonObject().get("rp_desc").getAsString();

                    for (Pack pack : this.out)
                    {
                        if (pack.getId() == i)
                        {
                            pack.setDesc(s2);
                        }
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
        catch (Exception exception1)
        {
            exception1.printStackTrace();
        }
    }
}

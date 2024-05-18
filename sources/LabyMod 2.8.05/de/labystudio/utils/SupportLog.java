package de.labystudio.utils;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import org.lwjgl.input.Keyboard;

public class SupportLog
{
    private static long cooldown = 0L;
    private static long keepTime = 0L;
    private static ArrayList<String> log = new ArrayList();

    public static void overwrite()
    {
        System.out.println("Overwrite output..");
        System.setErr(new SupportLog.PrintStreamLabyMod(System.err));
        System.setOut(new SupportLog.PrintStreamLabyMod(System.out));
    }

    public static void listenKey()
    {
        if (Keyboard.isKeyDown(29) && Keyboard.isKeyDown(56) && Keyboard.isKeyDown(42) && Keyboard.isKeyDown(46) && cooldown < System.currentTimeMillis())
        {
            ++keepTime;

            if (keepTime > 20L)
            {
                cooldown = System.currentTimeMillis() + 20000L;
                upload(log);
            }
        }
        else
        {
            keepTime = 0L;
        }
    }

    public static void upload(ArrayList<String> log)
    {
        System.out.println("-------------------------------");
        System.out.println("Date: " + (new Date()).toString());
        System.out.println("Minecraft Name: " + LabyMod.getInstance().getPlayerName());
        System.out.println("UUID: " + LabyMod.getInstance().getPlayerUUID());
        System.out.println("LabyMod Version: 2.8.05");
        System.out.println("Minecraft Version: 1.8.8");
        System.out.println("Cosmetics loaded: " + !LabyMod.getInstance().getCosmeticManager().getOnlineCosmetics().isEmpty());
        System.out.println("Capes loaded: " + LabyMod.getInstance().getCapeManager().countUserCapes());
        System.out.println("Has LabyMod Cape: " + LabyMod.getInstance().getCapeManager().isWhitelisted(LabyMod.getInstance().getPlayerUUID()));
        System.out.println("Capes enabled: " + ConfigManager.settings.capes);
        System.out.println("Server: " + LabyMod.getInstance().ip);
        System.out.println("-------------------------------");
        ArrayList<String> arraylist = new ArrayList(log);

        try
        {
            String s = "";

            for (String s1 : arraylist)
            {
                s = s + s1 + "\n";
            }

            byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
            int i = abyte.length;
            String s2 = "https://hastebin.com//documents";
            URL url = new URL(s2);
            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setInstanceFollowRedirects(false);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            httpurlconnection.setRequestProperty("Content-Type", "text/plain");
            httpurlconnection.setRequestProperty("charset", "utf-8");
            httpurlconnection.setRequestProperty("Content-Length", Integer.toString(i));
            httpurlconnection.setUseCaches(false);
            DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
            dataoutputstream.write(abyte);
            Reader reader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), "UTF-8"));
            String s3;
            int j;

            for (s3 = ""; (j = ((Reader)reader).read()) >= 0; s3 = s3 + (char)j)
            {
                ;
            }

            if (s3.contains("{\"key\":\"") && s3.contains("\"}"))
            {
                String s4 = "https://hastebin.com/" + s3.replace("{\"key\":\"", "").replace("\"}", "");
                System.out.println(s4);
                LabyMod.getInstance().openWebpage(s4, true);
            }
            else
            {
                System.out.println(s3);
                LabyMod.getInstance().openWebpage(s3, true);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static class PrintStreamLabyMod extends PrintStream
    {
        public PrintStreamLabyMod(OutputStream out)
        {
            super(out);
        }

        public void print(String s)
        {
            SupportLog.log.add(s);

            if (SupportLog.log.size() > 1000)
            {
                SupportLog.log.remove(0);
            }

            super.print(s);
        }

        public void debug(String s)
        {
            SupportLog.log.add(s);

            if (SupportLog.log.size() > 1000)
            {
                SupportLog.log.remove(0);
            }
        }
    }
}

package de.zockermaus.ts3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import org.apache.commons.io.IOUtils;

public class TeamspeakAuth
{
    public static void auth(OutputStream outputStream)
    {
        String s = "";
        String s1 = System.getProperty("os.name").toUpperCase();

        if (s1.contains("MAC"))
        {
            s = System.getProperty("user.home") + "/Library/Application Support/TeamSpeak 3";
        }
        else if (s1.contains("WIN"))
        {
            s = System.getenv("AppData") + "\\TS3Client";
        }
        else
        {
            s = System.getProperty("user.home") + "/.ts3client";
        }

        File file1 = new File(s, "clientquery.ini");

        if (!file1.exists())
        {
            System.err.println("[TeamSpeak] Couldn\'t find teamspeak\'s clientquery.ini!");
        }
        else
        {
            String s2 = null;

            try
            {
                String s3 = IOUtils.toString((Reader)(new FileReader(file1)));

                for (String s4 : s3.split("\n"))
                {
                    if (s4.startsWith("api_key"))
                    {
                        String s5 = s4.split("api_key=")[1].replace("\r", "").replace("\n", "");

                        if (s5.length() != 29)
                        {
                            System.err.println("[TeamSpeak] Invalid TeamSpeak3 api_key! Length: " + s5.length() + " but it should be 29");
                        }
                        else
                        {
                            s2 = s5;
                        }

                        break;
                    }
                }
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
                return;
            }

            if (s2 != null)
            {
                PrintWriter printwriter = new PrintWriter(outputStream, true);
                printwriter.println("auth apikey=" + s2);
                System.out.println("[TeamSpeak] Authed with api-key " + s2);
            }
        }
    }
}

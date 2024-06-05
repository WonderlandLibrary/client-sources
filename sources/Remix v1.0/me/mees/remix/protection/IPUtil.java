package me.mees.remix.protection;

import java.net.*;
import java.io.*;

public class IPUtil
{
    public static String getIP() {
        try {
            final URL url = new URL("http://checkip.amazonaws.com/");
            final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void e() {
        ShutDown.Shutdown();
        ShutDown.SysExit();
    }
}

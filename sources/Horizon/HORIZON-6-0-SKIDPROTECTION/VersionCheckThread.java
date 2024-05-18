package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionCheckThread extends Thread
{
    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            Config.HorizonCode_Horizon_È("Checking for new version");
            final URL e = new URL("http://optifine.net/version/1.8/HD_U.txt");
            conn = (HttpURLConnection)e.openConnection();
            if (Config.ÇªØ­().áŒŠá€) {
                conn.setRequestProperty("OF-MC-Version", "1.8");
                conn.setRequestProperty("OF-MC-Brand", ClientBrandRetriever.HorizonCode_Horizon_È());
                conn.setRequestProperty("OF-Edition", "HD_U");
                conn.setRequestProperty("OF-Release", "B2");
                conn.setRequestProperty("OF-Java-Version", new StringBuilder().append(System.getProperty("java.version")).toString());
                conn.setRequestProperty("OF-CpuCount", new StringBuilder().append(Config.Šà()).toString());
                conn.setRequestProperty("OF-OpenGL-Version", new StringBuilder().append(Config.Ó).toString());
                conn.setRequestProperty("OF-OpenGL-Vendor", new StringBuilder().append(Config.Ø).toString());
                conn.setRequestProperty("OF-ResourcePack", Config.£ÂµÄ());
            }
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.connect();
            try {
                final InputStream in = conn.getInputStream();
                final String verStr = Config.HorizonCode_Horizon_È(in);
                in.close();
                final String[] verLines = Config.HorizonCode_Horizon_È(verStr, "\n\r");
                if (verLines.length < 1) {
                    return;
                }
                final String newVer = verLines[0];
                Config.HorizonCode_Horizon_È("Version found: " + newVer);
                if (Config.Â(newVer, "B2") > 0) {
                    Config.Ó(newVer);
                    return;
                }
            }
            finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        catch (Exception var11) {
            Config.HorizonCode_Horizon_È(String.valueOf(var11.getClass().getName()) + ": " + var11.getMessage());
        }
    }
}

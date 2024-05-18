// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.src;

import java.io.InputStream;
import net.minecraft.client.ClientBrandRetriever;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionCheckThread extends Thread
{
    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            Config.dbg("Checking for new version");
            final URL e = new URL("http://optifine.net/version/1.8/HD_U.txt");
            conn = (HttpURLConnection)e.openConnection();
            if (Config.getGameSettings().snooperEnabled) {
                conn.setRequestProperty("OF-MC-Version", "1.8");
                conn.setRequestProperty("OF-MC-Brand", ClientBrandRetriever.getClientModName());
                conn.setRequestProperty("OF-Edition", "HD_U");
                conn.setRequestProperty("OF-Release", "B2");
                conn.setRequestProperty("OF-Java-Version", new StringBuilder().append(System.getProperty("java.version")).toString());
                conn.setRequestProperty("OF-CpuCount", new StringBuilder().append(Config.getAvailableProcessors()).toString());
                conn.setRequestProperty("OF-OpenGL-Version", new StringBuilder().append(Config.openGlVersion).toString());
                conn.setRequestProperty("OF-OpenGL-Vendor", new StringBuilder().append(Config.openGlVendor).toString());
                conn.setRequestProperty("OF-ResourcePack", Config.getResourcePackNames());
            }
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.connect();
            try {
                final InputStream in = conn.getInputStream();
                final String verStr = Config.readInputStream(in);
                in.close();
                final String[] verLines = Config.tokenize(verStr, "\n\r");
                if (verLines.length < 1) {
                    return;
                }
                final String newVer = verLines[0];
                Config.dbg("Version found: " + newVer);
                if (Config.compareRelease(newVer, "B2") > 0) {
                    Config.setNewRelease(newVer);
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
            Config.dbg(String.valueOf(var11.getClass().getName()) + ": " + var11.getMessage());
        }
    }
}

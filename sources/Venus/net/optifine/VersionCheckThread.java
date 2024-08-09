/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.ClientBrandRetriever;
import net.optifine.Config;

public class VersionCheckThread
extends Thread {
    public VersionCheckThread() {
        super("VersionCheck");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        HttpURLConnection httpURLConnection = null;
        try {
            Config.dbg("Checking for new version");
            URL uRL = new URL("http://optifine.net/version/1.16.5/HD_U.txt");
            httpURLConnection = (HttpURLConnection)uRL.openConnection();
            if (Config.getGameSettings().snooper) {
                httpURLConnection.setRequestProperty("OF-MC-Version", "1.16.5");
                httpURLConnection.setRequestProperty("OF-MC-Brand", ClientBrandRetriever.getClientModName());
                httpURLConnection.setRequestProperty("OF-Edition", "HD_U");
                httpURLConnection.setRequestProperty("OF-Release", "G8");
                httpURLConnection.setRequestProperty("OF-Java-Version", System.getProperty("java.version"));
                httpURLConnection.setRequestProperty("OF-CpuCount", "" + Config.getAvailableProcessors());
                httpURLConnection.setRequestProperty("OF-OpenGL-Version", Config.openGlVersion);
                httpURLConnection.setRequestProperty("OF-OpenGL-Vendor", Config.openGlVendor);
            }
            httpURLConnection.setDoInput(false);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            try {
                InputStream inputStream = httpURLConnection.getInputStream();
                String string = Config.readInputStream(inputStream);
                inputStream.close();
                String[] stringArray = Config.tokenize(string, "\n\r");
                if (stringArray.length >= 1) {
                    String string2 = stringArray[0].trim();
                    Config.dbg("Version found: " + string2);
                    if (Config.compareRelease(string2, "G8") <= 0) {
                        return;
                    }
                    Config.setNewRelease(string2);
                    return;
                }
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        } catch (Exception exception) {
            Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }
}


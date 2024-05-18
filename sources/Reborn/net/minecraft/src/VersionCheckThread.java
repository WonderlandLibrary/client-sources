package net.minecraft.src;

import java.net.*;
import java.io.*;

public class VersionCheckThread extends Thread
{
    @Override
    public void run() {
        HttpURLConnection var1 = null;
        try {
            Config.dbg("Checking for new version");
            final URL var2 = new URL("http://optifine.net/version/1.5.2/HD_U.txt");
            var1 = (HttpURLConnection)var2.openConnection();
            var1.setDoInput(true);
            var1.setDoOutput(false);
            var1.connect();
            try {
                final InputStream var3 = var1.getInputStream();
                final String var4 = Config.readInputStream(var3);
                var3.close();
                final String[] var5 = Config.tokenize(var4, "\n\r");
                if (var5.length >= 1) {
                    final String var6 = var5[0];
                    Config.dbg("Version found: " + var6);
                    if (Config.compareRelease(var6, "D2") <= 0) {
                        return;
                    }
                    Config.setNewRelease(var6);
                    return;
                }
            }
            finally {
                if (var1 != null) {
                    var1.disconnect();
                }
            }
            if (var1 != null) {
                var1.disconnect();
            }
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
    }
}

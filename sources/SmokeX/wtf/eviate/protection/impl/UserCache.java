// 
// Decompiled by Procyon v0.6.0
// 

package wtf.eviate.protection.impl;

import java.io.IOException;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.util.Objects;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import gg.childtrafficking.smokex.utils.system.StringUtils;

public final class UserCache
{
    private static String username;
    private static int uid;
    
    public static void init() {
        try {
            System.out.println(StringUtils.getHWID());
            final URL url = new URL("https://pastebin.com/raw/nvY2E6bb");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = null;
            String[] split = new String[3];
            while ((s = reader.readLine()) != null) {
                split = s.split("::");
                if (Objects.equals(split[0], StringUtils.getHWID())) {
                    UserCache.username = split[1];
                    UserCache.uid = Integer.parseInt(split[2]);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "You are not authorized. Contact the admins. Your HWID: " + StringUtils.getHWID(), "Not authorized", 0);
            StringUtils.copyStringToClipboard(StringUtils.getHWID());
            System.exit(0);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getUsername() {
        return UserCache.username;
    }
    
    public static int getUid() {
        return UserCache.uid;
    }
}

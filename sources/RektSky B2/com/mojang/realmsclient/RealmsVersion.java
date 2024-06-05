package com.mojang.realmsclient;

import java.io.*;

public class RealmsVersion
{
    private static String version;
    
    public static String getVersion() {
        if (RealmsVersion.version != null) {
            return RealmsVersion.version;
        }
        BufferedReader reader = null;
        try {
            final InputStream versionStream = RealmsVersion.class.getResourceAsStream("/version");
            reader = new BufferedReader(new InputStreamReader(versionStream));
            RealmsVersion.version = reader.readLine();
            reader.close();
            return RealmsVersion.version;
        }
        catch (Exception ignore) {}
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException ex) {}
            }
        }
        return null;
    }
}

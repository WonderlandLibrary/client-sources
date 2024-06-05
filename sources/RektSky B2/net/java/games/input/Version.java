package net.java.games.input;

import java.util.*;
import java.io.*;

public final class Version
{
    private Version() {
    }
    
    public static String getVersion() {
        String version = "Unversioned";
        try {
            final Properties p = new Properties();
            final InputStream is = Version.class.getResourceAsStream("/META-INF/maven/net.java.jinput/coreapi/pom.properties");
            if (is != null) {
                p.load(is);
                version = p.getProperty("version", "");
            }
        }
        catch (IOException ex) {}
        return version;
    }
}

package com.mpatric.mp3agic;

public class Version
{
    private static final String VERSION;
    private static final String URL = "http://github.com/mpatric/mp3agic";
    
    public static String asString() {
        return getVersion() + " - " + getUrl();
    }
    
    public static String getVersion() {
        return Version.VERSION;
    }
    
    public static String getUrl() {
        return "http://github.com/mpatric/mp3agic";
    }
    
    static {
        final String implementationVersion = Version.class.getPackage().getImplementationVersion();
        VERSION = ((implementationVersion != null) ? implementationVersion : "UNKNOWN-SNAPSHOT");
    }
}

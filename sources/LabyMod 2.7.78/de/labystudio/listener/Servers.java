package de.labystudio.listener;

import de.labystudio.labymod.LabyMod;

public class Servers
{
    private static boolean isDeinProjektHost = false;
    private static boolean isMineVerse = false;

    public static void updateDeinProjektHost()
    {
        String s = LabyMod.getInstance().ip.toLowerCase();
        isDeinProjektHost = s.contains("deinprojekthost") || s.contains("miniminerlps.de") || s.contains("dph-games.de");
    }

    public static void updateMineVerse()
    {
        String s = LabyMod.getInstance().ip.toLowerCase();
        isDeinProjektHost = s.contains("mineverse");
    }

    public static boolean isMineVerse()
    {
        return isMineVerse;
    }

    public static boolean isDeinProjektHost()
    {
        return isDeinProjektHost;
    }
}

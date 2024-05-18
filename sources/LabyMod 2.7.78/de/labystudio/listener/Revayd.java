package de.labystudio.listener;

import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.ModGui;

public class Revayd
{
    public static int kills = 0;
    public static int deaths = 0;
    public static boolean isRevayd = false;

    public static void updateRevayd()
    {
        isRevayd = LabyMod.getInstance().ip.toLowerCase().contains("revayd.net") || LabyMod.getInstance().ip.toLowerCase().contains("revayd.living-bots.net") || LabyMod.getInstance().ip.toLowerCase().contains("212.224.88.206");
    }

    public static boolean isRevayd()
    {
        return isRevayd;
    }

    public static void serverRevaydChat(String clean, String raw)
    {
        if (clean.contains("Du hast") && clean.contains(" mit ") && clean.contains(" get\u00f6tet."))
        {
            ++kills;
        }

        if (clean.contains("Du wurdest von ") && clean.contains(" mit ") && clean.contains(" get\u00f6tet."))
        {
            ++deaths;
        }
    }

    public static void drawRevaydGui()
    {
        if (isRevayd())
        {
            ModGui.addMainLabel("Stats", Color.cl("a") + kills + Color.cl("7") + " | " + Color.cl("c") + deaths, ModGui.mainList);
        }
    }

    public static void reset()
    {
        kills = 0;
        deaths = 0;
    }
}

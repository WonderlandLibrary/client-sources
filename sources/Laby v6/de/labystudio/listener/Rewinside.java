package de.labystudio.listener;

import de.labystudio.labymod.LabyMod;

public class Rewinside
{
    public static boolean isRewinside = false;

    public static void updateRewinside()
    {
        isRewinside = LabyMod.getInstance().ip.toLowerCase().contains("rewinside.tv");
    }

    public static boolean isRewinside()
    {
        return isRewinside;
    }
}

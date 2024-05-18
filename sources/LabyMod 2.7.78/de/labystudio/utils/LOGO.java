package de.labystudio.utils;

public class LOGO
{
    public static boolean isLogisch(String player)
    {
        return player.equals("LOGO") ? false : (player.toLowerCase().contains("logo") ? true : (player.toLowerCase().contains("logi") ? true : (player.toLowerCase().contains("log0") ? true : (player.toLowerCase().contains("l0g0") ? true : (player.toLowerCase().contains("l0go") ? true : (player.toLowerCase().contains("l0gi") ? true : (player.equals("LabyStudio") ? true : (player.equals("Buttspencer") ? true : player.equals("CraftingPat")))))))));
    }

    public static boolean isLogo(String player)
    {
        return player.equals("LOGO");
    }
}

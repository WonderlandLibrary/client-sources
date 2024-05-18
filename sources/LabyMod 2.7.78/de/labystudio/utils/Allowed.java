package de.labystudio.utils;

import de.labystudio.listener.GommeHD;
import de.labystudio.listener.HiveMC;
import de.labystudio.listener.Hypixel;
import de.labystudio.listener.JumpLeague;
import de.labystudio.listener.Revayd;
import de.labystudio.listener.Rewinside;
import de.labystudio.listener.Servers;
import de.labystudio.listener.Timolia;
import de.labystudio.modapi.ModManager;
import net.minecraft.client.Minecraft;

public class Allowed
{
    static boolean food = true;
    static boolean gui = true;
    static boolean nick = true;
    static boolean blockBuild = false;
    static boolean chat = true;
    static boolean extras = true;
    static boolean animations = true;
    static boolean potions = true;
    static boolean armor = true;

    public static void update(String address)
    {
        GommeHD.updateGommeHD();
        Hypixel.updateHypixel();
        JumpLeague.updatePlayMinity();
        Revayd.updateRevayd();
        Rewinside.updateRewinside();
        Servers.updateDeinProjektHost();
        Timolia.updateTimolia();
        Servers.updateMineVerse();
        HiveMC.updateHiveMC();
        food = true;
        gui = true;
        nick = true;
        blockBuild = false;
        chat = true;
        extras = true;
        animations = true;
        potions = true;
        armor = true;
    }

    public static boolean foodSaturation()
    {
        return !Timolia.isTimolia() && !Servers.isDeinProjektHost() && food;
    }

    public static boolean gui()
    {
        return gui;
    }

    public static boolean nick()
    {
        return !Revayd.isRevayd() && !Hypixel.isHypixel() && !Servers.isDeinProjektHost() && nick;
    }

    public static boolean blockBuild()
    {
        return GommeHD.isGommeHD() || Minecraft.getMinecraft().isSingleplayer() || Revayd.isRevayd() || blockBuild;
    }

    public static boolean chat()
    {
        return !Servers.isDeinProjektHost() && !Servers.isMineVerse() && chat;
    }

    public static boolean unfairExtra()
    {
        return !Servers.isDeinProjektHost() && extras;
    }

    public static boolean animations()
    {
        return !Servers.isDeinProjektHost() && animations;
    }

    public static boolean potions()
    {
        return !Servers.isDeinProjektHost() && potions;
    }

    public static boolean armorHud()
    {
        return !Servers.isDeinProjektHost() && armor;
    }

    public static void set(String key, boolean value)
    {
        System.out.println("[PLUGINMESSAGE] Set " + key + " to " + value);

        if (key.equalsIgnoreCase("food"))
        {
            food = value;
        }

        if (key.equalsIgnoreCase("gui"))
        {
            gui = value;
        }

        if (key.equalsIgnoreCase("nick"))
        {
            nick = value;
        }

        if (key.equalsIgnoreCase("blockbuild"))
        {
            blockBuild = value;
        }

        if (key.equalsIgnoreCase("chat"))
        {
            chat = value;
        }

        if (key.equalsIgnoreCase("extras"))
        {
            extras = value;
        }

        if (key.equalsIgnoreCase("animations"))
        {
            animations = value;
        }

        if (key.equalsIgnoreCase("potions"))
        {
            potions = value;
        }

        if (key.equalsIgnoreCase("armor"))
        {
            armor = value;
        }

        ModManager.pluginMessage(key, value);
    }
}

package de.labystudio.listener;

import de.labystudio.chat.ChatHandler;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.ModGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class JumpLeague
{
    public static boolean playminityServer_jl_lock = false;
    public static boolean playminityServer_jl = false;
    public static int playminityServer_jl_module = -1;
    public static int playminityServer_jl_falls = 0;
    public static int playminityServer_jl_kills = 0;
    public static boolean isPlayMinity = false;
    static BlockPos lastPos;

    public static void updatePlayMinity()
    {
        isPlayMinity = LabyMod.getInstance().ip.toLowerCase().contains("playminity.net") || LabyMod.getInstance().ip.toLowerCase().contains("playminity.com");
    }

    public static boolean isPlayMinity()
    {
        return isPlayMinity;
    }

    public static void resetJumpLeague()
    {
        playminityServer_jl = false;
        playminityServer_jl_lock = false;
        playminityServer_jl_kills = 0;
        playminityServer_jl_falls = 0;
        playminityServer_jl_module = -1;
    }

    public static void isFallingDown()
    {
        if (playminityServer_jl)
        {
            if (playminityServer_jl_module != -1)
            {
                if (LabyMod.getInstance().isInGame())
                {
                    if (lastPos == null)
                    {
                        lastPos = Minecraft.getMinecraft().thePlayer.getPosition();
                    }

                    if (Minecraft.getMinecraft().thePlayer.getPosition().distanceSq((double)lastPos.getX(), (double)Minecraft.getMinecraft().thePlayer.getPosition().getY(), (double)lastPos.getZ()) > 4.0D)
                    {
                        if (!playminityServer_jl_lock)
                        {
                            playminityServer_jl_lock = true;
                            ++playminityServer_jl_falls;
                        }
                    }
                    else
                    {
                        playminityServer_jl_lock = false;
                    }

                    lastPos = Minecraft.getMinecraft().thePlayer.getPosition();
                }
            }
        }
    }

    public static void serverPlayMinityChat(String clean, String raw)
    {
        if (isPlayMinity())
        {
            String s = "[JumpLeague] ";

            if (clean.startsWith(s + ""))
            {
                playminityServer_jl = true;
                ChatHandler.updateGameMode("JumpLeague");
            }

            if (clean.startsWith(s + "The round has started!") || clean.startsWith(s + "Die Runde beginnt!"))
            {
                playminityServer_jl_module = 1;
                playminityServer_jl_kills = 0;
                playminityServer_jl_falls = 0;
            }

            if (clean.startsWith(" [PM]") && playminityServer_jl_module != -1)
            {
                ++playminityServer_jl_module;
            }

            if (clean.startsWith(s + " Teleporting all players to the arena") || clean.startsWith(s + "Teleportiere alle Spieler in die Arena..."))
            {
                playminityServer_jl_module = -1;
            }

            if (clean.startsWith(s + " +5 Points | +3 Minitys"))
            {
                ++playminityServer_jl_kills;
            }

            if ((clean.contains("> Willkommen auf mc.PlayMinity.com") || clean.contains("> Welcome to mc.PlayMinity.com")) && !clean.contains(":"))
            {
                LabyMod.getInstance().resetMod();
            }
        }
    }

    public static void drawPlayMinityGui()
    {
        if (ConfigManager.settings.gamePlayMinity.booleanValue())
        {
            if (playminityServer_jl)
            {
                ModGui.mainListNext();

                if (playminityServer_jl_module != -1)
                {
                    ModGui.addMainLabel("Module", playminityServer_jl_module + "", ModGui.mainList);
                }

                if (playminityServer_jl_falls != 0)
                {
                    ModGui.addMainLabel("Falls", playminityServer_jl_falls + "", ModGui.mainList);
                }

                if (playminityServer_jl_kills != 0)
                {
                    ModGui.addMainLabel("Kills", playminityServer_jl_kills + "", ModGui.mainList);
                }
            }
        }
    }
}

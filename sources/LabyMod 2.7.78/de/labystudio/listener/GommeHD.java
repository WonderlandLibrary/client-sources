package de.labystudio.listener;

import de.labystudio.chat.ChatHandler;
import de.labystudio.gommehd.EnumBWMap;
import de.labystudio.gommehd.EnumBWTeam;
import de.labystudio.gommehd.GommeHDBed;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.ModGui;
import java.util.ArrayList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GommeHD
{
    public static boolean gommeHDServer_BW = false;
    public static String gommeHDServer_BW_Team = "";
    public static boolean gommeHDServer_BW_Bed = true;
    public static int elapsedTime;
    public static boolean isGommeHD = false;

    public static void updateGommeHD()
    {
        isGommeHD = LabyMod.getInstance().ip.toLowerCase().contains("gommehd.net");
    }

    public static boolean isGommeHD()
    {
        return isGommeHD;
    }

    public static void resetGommeHD()
    {
        gommeHDServer_BW = false;
        gommeHDServer_BW_Team = "";
        gommeHDServer_BW_Bed = true;
        GommeHDBed.reset();
    }

    public static void loop()
    {
        if (gommeHDServer_BW)
        {
            ++elapsedTime;
        }

        if (LabyMod.getInstance().header != null && LabyMod.getInstance().header.toString().contains("Lobby"))
        {
            resetGommeHD();
        }
    }

    public static void serverGommeHDChat(String clean, String raw)
    {
        if (LabyMod.getInstance().ip.toLowerCase().contains("gommehd.net"))
        {
            String s = "[NICK] ";

            if (clean.startsWith(s + "Du spielst als: "))
            {
                LabyMod.getInstance().nickname = clean.replace(s + "Du spielst als: ", "");
            }

            if (clean.startsWith(s + "Dein Nickname wurde entfernt"))
            {
                LabyMod.getInstance().nickname = "";
            }

            chatBedWars(clean, raw);
            chatSurvivalGames(clean, raw);
            chatOther(clean, raw);
        }
    }

    private static void chatOther(String clean, String raw)
    {
        String s = "[RageMode] ";

        if (clean.startsWith(s))
        {
            ChatHandler.updateGameMode("RageMode");
        }

        String s1 = "[TTT] ";

        if (clean.startsWith(s1))
        {
            ChatHandler.updateGameMode("TTT");
        }

        String s2 = "[Surf] ";

        if (clean.startsWith(s2))
        {
            ChatHandler.updateGameMode("Surf");
        }

        String s3 = "[Conquest] ";

        if (clean.startsWith(s3))
        {
            ChatHandler.updateGameMode("Conquest");
        }

        String s4 = "[AuraPvP] ";

        if (clean.startsWith(s4))
        {
            ChatHandler.updateGameMode("AuraPvP");
        }

        String s5 = "[EnderGames] ";

        if (clean.startsWith(s5))
        {
            ChatHandler.updateGameMode("EnderGames");
        }

        String s6 = "[SkyWars] ";

        if (clean.startsWith(s6))
        {
            ChatHandler.updateGameMode("SkyWars");
        }

        String s7 = "[Survival] ";

        if (clean.startsWith(s7))
        {
            ChatHandler.updateGameMode("Survival");
        }
    }

    private static void chatSurvivalGames(String clean, String raw)
    {
        String s = "[SurvivalGames] ";

        if (clean.startsWith(s))
        {
            ChatHandler.updateGameMode("SG");
        }

        if (clean.startsWith(s + "Die Spiele beginnen!"))
        {
            Games.kills = 0;
        }

        if (clean.startsWith(s) && clean.contains(" wurde von " + LabyMod.getInstance().getPlayerName()) && clean.contains("get\u00f6tet"))
        {
            ++Games.kills;
        }
    }

    private static void chatBedWars(String clean, String raw)
    {
        String s = "[BedWars] ";

        if (clean.startsWith(s + "Du bist nun in Team "))
        {
            ArrayList<String> arraylist = new ArrayList();

            for (String s1 : raw.split(" "))
            {
                arraylist.add(s1);
            }

            gommeHDServer_BW_Team = (String)arraylist.get(arraylist.size() - 1);
        }

        if (clean.startsWith(s + "Das Bett deines Teams"))
        {
            gommeHDServer_BW_Bed = false;
        }

        if (clean.startsWith(s + "Diese Runde ging: "))
        {
            LabyMod.getInstance().resetMod();
        }

        if (clean.startsWith(s + "Server startet in "))
        {
            LabyMod.getInstance().resetMod();
        }

        if (clean.startsWith(s + "Das Spiel beginnt"))
        {
            gommeHDServer_BW_Bed = true;
            gommeHDServer_BW = true;
            elapsedTime = 0;
            ChatHandler.updateGameMode("BedWars");
            GommeHDBed.noBeds.clear();
        }

        if (clean.startsWith(s + "Du bist nun Spectator"))
        {
            gommeHDServer_BW_Team = "Spectator";
        }

        if (clean.startsWith(s + "Map: "))
        {
            String s2 = clean.replace(s + "Map: ", "");
            boolean flag = false;

            for (EnumBWMap enumbwmap : EnumBWMap.values())
            {
                if (s2.toLowerCase().contains(enumbwmap.name().toLowerCase()))
                {
                    GommeHDBed.setPreset(enumbwmap);
                    flag = true;
                }
            }

            if (!flag)
            {
                GommeHDBed.reset();
            }
        }

        if (clean.startsWith(s + "Das Bett von Team "))
        {
            String s3 = clean.replace(s + "Das Bett von Team ", "");

            for (EnumBWTeam enumbwteam : EnumBWTeam.values())
            {
                if (s3.toLowerCase().contains(enumbwteam.name().toLowerCase()))
                {
                    GommeHDBed.noBeds.add(enumbwteam);
                    GommeHDBed.updateHolograms();
                }
            }
        }

        if (clean.startsWith(s + "Es konnte kein passendes Team gefunden werden. Die Party ist NICHT im selben Team.") && ConfigManager.settings.autoLeave)
        {
            LabyMod.getInstance().sendCommand("hub");
        }
    }

    public static void drawGommeHDGui()
    {
        if (ConfigManager.settings.gameGommeHD.booleanValue())
        {
            if (gommeHDServer_BW)
            {
                if (!gommeHDServer_BW_Team.isEmpty())
                {
                    ModGui.addMainLabel("Elapsed Time", ModGui.translateTimer(elapsedTime), ModGui.mainList);

                    if (gommeHDServer_BW_Team.contains("Spectator"))
                    {
                        ModGui.addMainLabel("Mode", Color.cl("7") + "Spectator", ModGui.mainList);
                    }
                    else
                    {
                        ModGui.addMainLabel("Team", gommeHDServer_BW_Team + "", ModGui.mainList);
                        String s = "" + Color.c + "2Yes";

                        if (!gommeHDServer_BW_Bed)
                        {
                            s = "" + Color.c + "4No";
                        }

                        ModGui.addMainLabel("Bed", s + "", ModGui.mainList);
                    }

                    if (ConfigManager.settings.showBWTimer.booleanValue())
                    {
                        drawTimer();
                    }
                }
            }
        }
    }

    private static void drawTimer()
    {
        int i = 30 - elapsedTime % 30;
        int j = 10 - elapsedTime % 10;
        String s = "";

        if (i <= 5)
        {
            s = Color.cl("c");
        }

        String s1 = "";

        if (j <= 5)
        {
            s1 = Color.cl("c");
        }

        int k = getPos();
        double d0 = LabyMod.getInstance().draw.getScale(ConfigManager.settings.size);

        if (ConfigManager.settings.gommePosLeft)
        {
            LabyMod.getInstance().draw.drawItem(new ItemStack(Item.getItemById(266)), 2.0D, (double)k, "" + s + i + "");
            LabyMod.getInstance().draw.drawString(s + " sec", 17.0D, (double)(k + 9));
            k = k + 16;
            LabyMod.getInstance().draw.drawItem(new ItemStack(Item.getItemById(265)), 2.0D, (double)k, "" + s + j + "");
            LabyMod.getInstance().draw.drawString(s + " sec", 17.0D, (double)(k + 9));
        }
        else
        {
            int l = LabyMod.getInstance().draw.getWidth() - 39;
            LabyMod.getInstance().draw.drawItem(new ItemStack(Item.getItemById(266)), (double)l / d0, (double)k, "" + s + i + "");
            LabyMod.getInstance().draw.drawString(s + " sec", (double)(l + 15) / d0, (double)(k + 9));
            k = k + 16;
            LabyMod.getInstance().draw.drawItem(new ItemStack(Item.getItemById(265)), (double)l / d0, (double)k, "" + s + j + "");
            LabyMod.getInstance().draw.drawString(s + " sec", (double)(l + 15) / d0, (double)(k + 9));
        }

        k = k + 20;
        setPos(k);
    }

    private static void setPos(int pos)
    {
        if (ModGui.isSwitch())
        {
            if (ConfigManager.settings.gommePosLeft)
            {
                ModGui.offList = pos;
            }
            else
            {
                ModGui.mainList = pos;
            }
        }
        else if (ConfigManager.settings.gommePosLeft)
        {
            ModGui.mainList = pos;
        }
        else
        {
            ModGui.offList = pos;
        }
    }

    private static int getPos()
    {
        return ModGui.isSwitch() ? (ConfigManager.settings.gommePosLeft ? ModGui.offList : ModGui.mainList) : (ConfigManager.settings.gommePosLeft ? ModGui.mainList : ModGui.offList);
    }
}

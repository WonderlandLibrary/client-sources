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
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class GommeHD
{
    public static boolean gommeHDServer_BW = false;
    public static String gommeHDServer_BW_Team = "";
    public static boolean gommeHDServer_BW_Bed = true;
    public static int elapsedTime;
    public static String skyBlockOwner = null;
    private static long stoppedBedLong = 0L;
    private static int stoppedBedPercent = 0;
    public static boolean isGommeHD = false;

    public static void updateGommeHD()
    {
        isGommeHD = LabyMod.getInstance().ip.toLowerCase().contains("gommehd.net") || LabyMod.getInstance().ip.toLowerCase().contains("gommehd.com");
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
        skyBlockOwner = null;
        GommeHDBed.reset();
    }

    public static void loop()
    {
        if (gommeHDServer_BW)
        {
            ++elapsedTime;
        }

        if (LabyMod.getInstance().header != null && LabyMod.getInstance().header.getFormattedText().contains("Lobby"))
        {
            resetGommeHD();
        }
    }

    public static void serverGommeHDChat(String clean, String raw)
    {
        if (LabyMod.getInstance().ip.toLowerCase().contains("gommehd.net"))
        {
            String s = "[NICK] ";

            if (clean.startsWith(s + "Du spielst als: ") || clean.startsWith(s + "Aktueller Nickname: "))
            {
                LabyMod.getInstance().nickname = clean.replace(s + "Du spielst als: ", "").replace(s + "Aktueller Nickname: ", "");
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

        if (clean.startsWith("Du betrittst nun die Insel von: "))
        {
            skyBlockOwner = clean.replace("Du betrittst nun die Insel von: ", "");
        }
    }

    public static void drawGommeHDGui()
    {
        if (ConfigManager.settings.gameGommeHD)
        {
            if (ConfigManager.settings.skyblock && skyBlockOwner != null)
            {
                ModGui.addMainLabel("Island", skyBlockOwner, ModGui.mainList);
            }

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

                    if (ConfigManager.settings.showBWTimer)
                    {
                        drawTimer();
                    }
                }

                if (ConfigManager.settings.gommeBedTimer || ConfigManager.settings.gommeBeaconTimer)
                {
                    boolean flag = false;
                    WorldClient worldclient = Minecraft.getMinecraft().theWorld;

                    if (worldclient != null && Minecraft.getMinecraft().objectMouseOver != null)
                    {
                        BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();

                        if (blockpos != null)
                        {
                            IBlockState iblockstate = worldclient.getBlockState(blockpos);

                            if (iblockstate != null)
                            {
                                Item item = iblockstate.getBlock().getItem(worldclient, blockpos);
                                flag = item != null && (ConfigManager.settings.gommeBedTimer && item == Item.getItemById(355) || ConfigManager.settings.gommeBeaconTimer && item == Item.getItemById(138));
                            }
                        }
                    }

                    if (Minecraft.getMinecraft().playerController.curBlockDamageMP != 0.0F && flag)
                    {
                        stoppedBedPercent = (int)(Minecraft.getMinecraft().playerController.curBlockDamageMP * 100.0F);
                        stoppedBedLong = System.currentTimeMillis() + 3000L;
                    }

                    if (stoppedBedLong > System.currentTimeMillis())
                    {
                        if (Minecraft.getMinecraft().playerController.blockHitDelay != 0)
                        {
                            stoppedBedPercent = 100;
                        }

                        if (stoppedBedPercent != 0 && (Minecraft.getMinecraft().playerController.blockHitDelay == 0 || stoppedBedPercent == 100))
                        {
                            LabyMod.getInstance().draw.drawCenteredString(Color.cl("a") + stoppedBedPercent + "% ", (double)(LabyMod.getInstance().draw.getWidth() / 2 + 3), (double)(LabyMod.getInstance().draw.getHeight() / 2 - 10), 1.0D);
                        }
                    }
                    else
                    {
                        stoppedBedPercent = 0;

                        if (Minecraft.getMinecraft().playerController.blockHitDelay != 0 && flag)
                        {
                            LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + Minecraft.getMinecraft().playerController.blockHitDelay, (double)(LabyMod.getInstance().draw.getWidth() / 2 + 1), (double)(LabyMod.getInstance().draw.getHeight() / 2 - 10), 1.0D);
                        }
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

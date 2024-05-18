package de.labystudio.listener;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Allowed;
import de.labystudio.utils.Color;
import de.labystudio.utils.FilterLoader;
import de.labystudio.utils.FriendsLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class ChatListener
{
    public static long init = 0L;

    public static void serverChat(String raw, String clean)
    {
        GommeHD.serverGommeHDChat(clean, raw);
        JumpLeague.serverPlayMinityChat(clean, raw);
        Timolia.serverTimoliaChat(clean, raw);
        Revayd.serverRevaydChat(clean, raw);
        HiveMC.serverHiveChat(clean, raw);
    }

    public static boolean allowedToPrint(String raw, String clean)
    {
        if (raw == null)
        {
            return true;
        }
        else
        {
            serverChat(raw, clean);
            return true;
        }
    }

    public static String replaceMessage(String raw, String clean)
    {
        if (Allowed.nick())
        {
            boolean flag = false;

            for (String s : FriendsLoader.friends.keySet())
            {
                if (FriendsLoader.friends.get(s) != null && !((String)FriendsLoader.friends.get(s)).replace(" ", "").isEmpty() && clean.contains(s))
                {
                    if (raw.contains(":") && raw.split(":")[0].contains(s))
                    {
                        return raw.replaceFirst(s, ((String)FriendsLoader.friends.get(s)).replace("&", "\u00a7") + "\u00a7r");
                    }

                    if (raw.contains(">") && raw.split(":")[0].contains(s))
                    {
                        return raw.replaceFirst(s, ((String)FriendsLoader.friends.get(s)).replace("&", "\u00a7") + "\u00a7r");
                    }

                    if (raw.contains(":") && raw.split(":")[1].contains(s))
                    {
                        return raw;
                    }

                    if (raw.contains(">") && raw.split(":")[1].contains(s))
                    {
                        return raw;
                    }

                    try
                    {
                        return raw.replaceFirst(s, ((String)FriendsLoader.friends.get(s)).replace("&", "\u00a7") + "\u00a7r");
                    }
                    catch (Exception var6)
                    {
                        flag = true;
                    }
                }
            }

            if (flag)
            {
                FriendsLoader.friends.clear();
                FriendsLoader.saveFriends();
            }
        }

        return raw;
    }

    public static boolean isServerMSG(String msg)
    {
        if (ConfigManager.settings.chatFilter.booleanValue() && FilterLoader.enabled)
        {
            for (String s : FilterLoader.filters)
            {
                String s1 = s.toLowerCase();

                if (contains(msg, s1) && !s1.contains("%k%") && (!s1.contains("%s%") || s1.contains("%k%")))
                {
                    return true;
                }
            }
        }

        if (ConfigManager.settings.extraChat.booleanValue())
        {
            if (LabyMod.getInstance().ip == null || LabyMod.getInstance().ip.isEmpty())
            {
                return false;
            }

            if ((LabyMod.getInstance().ip.toLowerCase().contains("gommehd.net") || LabyMod.getInstance().ip.toLowerCase().contains("minekits")) && (msg.toLowerCase().contains(LabyMod.getInstance().getPlayerName().toLowerCase()) || msg.contains("Du") || msg.contains("Dir")) && msg.startsWith("[F") && (msg.contains(" -> ") || msg.contains(" <- ")))
            {
                return true;
            }

            if ((LabyMod.getInstance().ip.toLowerCase().contains("infect") || LabyMod.getInstance().ip.toLowerCase().contains("kitpvp")) && msg.contains("me") && (msg.contains(" -> ") || msg.contains(" <- ")))
            {
                return true;
            }

            if (LabyMod.getInstance().ip.toLowerCase().contains("timolia") && msg.startsWith(Color.c + "1") && msg.contains("MSG") && msg.contains("Du"))
            {
                return true;
            }

            if ((LabyMod.getInstance().ip.toLowerCase().contains("brawl") || LabyMod.getInstance().ip.toLowerCase().contains("mcpvp") || LabyMod.getInstance().ip.toLowerCase().contains("mc-hg")) && (msg.startsWith("(To ") || msg.startsWith("(From ")))
            {
                return true;
            }

            if (LabyMod.getInstance().ip.toLowerCase().contains("hivemc") && msg.toLowerCase().contains(LabyMod.getInstance().getPlayerName().toLowerCase()) && msg.contains("PRIVATE"))
            {
                return true;
            }

            if ((LabyMod.getInstance().ip.toLowerCase().contains("mineplex") || LabyMod.getInstance().ip.toLowerCase().contains("playminity")) && msg.toLowerCase().contains(LabyMod.getInstance().getPlayerName().toLowerCase()) && (msg.contains(" > ") || msg.contains(" < ")))
            {
                return true;
            }
        }

        return false;
    }

    private static boolean contains(String msg, String m)
    {
        if (m.contains("%b%"))
        {
            String[] astring = m.replace("%s%", "").replace("%k%", "").split("%b%");
            boolean flag = false;
            boolean flag1 = true;

            for (String s : astring)
            {
                if (!s.isEmpty())
                {
                    if (flag1)
                    {
                        if (!msg.toLowerCase().contains(s))
                        {
                            flag = false;
                            break;
                        }

                        flag = true;
                        flag1 = false;
                    }
                    else
                    {
                        if (msg.toLowerCase().contains(s))
                        {
                            flag = false;
                            break;
                        }

                        flag = true;
                    }
                }
            }

            if (flag)
            {
                playSound(m);
                return true;
            }
        }
        else if (msg.toLowerCase().contains(m.replace("%s%", "").replace("%k%", "")))
        {
            playSound(m);
            return true;
        }

        return false;
    }

    private static void playSound(String m)
    {
        if (m.contains("%s%") && init + 1000L < Minecraft.getSystemTime() && LabyMod.getInstance().isInGame())
        {
            Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation("note.bassattack"), 5.0F, 1.0F, (float)Minecraft.getMinecraft().thePlayer.posX, (float)Minecraft.getMinecraft().thePlayer.posY, (float)Minecraft.getMinecraft().thePlayer.posZ));
        }
    }

    public static boolean isMarked(String msg)
    {
        for (String s : FilterLoader.filters)
        {
            String s1 = s.toLowerCase();

            if (s1.startsWith("%k%") && contains(msg, s1.replace("%k%", "").replace("%s%", "")))
            {
                return true;
            }
        }

        return false;
    }
}

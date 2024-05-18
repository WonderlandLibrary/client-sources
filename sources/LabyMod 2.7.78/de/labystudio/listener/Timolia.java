package de.labystudio.listener;

import de.labystudio.chat.ChatHandler;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.ModGui;

public class Timolia
{
    public static String timoliaLobby = "";
    public static String timoliaRequestPlayer = "";
    public static String timoliaRequestKit = "";
    public static int timoliaStatsPlus = 0;
    public static int timoliaStatsMinus = 0;
    public static int winStreak = 0;
    public static boolean isTimolia = false;

    public static void updateTimolia()
    {
        isTimolia = LabyMod.getInstance().ip.toLowerCase().contains("timolia.de");
    }

    public static boolean isTimolia()
    {
        return isTimolia;
    }

    public static void resetTimolia()
    {
        timoliaLobby = "";
        timoliaRequestPlayer = "";
        timoliaRequestKit = "";
        timoliaStatsPlus = 0;
        timoliaStatsMinus = 0;
    }

    public static void listenToTablist()
    {
        if (LabyMod.getInstance().getHeader().contains("Timolia"))
        {
            String s = LabyMod.getInstance().getFooter();

            if (!s.contains("."))
            {
                return;
            }

            int i = s.indexOf(".");
            String s1 = s.substring(0, i);
            s1 = s1.replace(" ", "");

            if (s1.startsWith("Duspielstauf"))
            {
                String s2 = s1.replace("Duspielstauf", "");

                if (!timoliaLobby.equals(s2))
                {
                    ChatHandler.updateGameMode(s2);
                }

                timoliaLobby = s2;
            }
            else
            {
                timoliaLobby = "";
            }
        }
        else
        {
            timoliaLobby = "";
        }
    }

    public static void serverTimoliaChat(String clean, String raw)
    {
        if (isTimolia())
        {
            if (clean.contains(" hat dich mit dem Kit \'") && clean.contains("\' zu einem Kampf herausgefordert (1vs1)!"))
            {
                try
                {
                    String[] astring = clean.replace("\' zu einem Kampf herausgefordert (1vs1)!", "").split(" hat dich mit dem Kit \'");
                    String s = astring[0];

                    if (s != null)
                    {
                        timoliaRequestPlayer = s;
                    }

                    String s1 = astring[1];

                    if (s1 != null)
                    {
                        timoliaRequestKit = s1;
                    }
                }
                catch (Exception var6)
                {
                    timoliaRequestKit = "";
                    timoliaRequestPlayer = "";
                }
            }

            if (clean.startsWith("Du hast ") && clean.contains(" zu einem Kampf herausgefordert (1vs1)!"))
            {
                String s2 = clean.replace(" zu einem Kampf herausgefordert (1vs1)!", "").replace("Du hast ", "");
                timoliaRequestPlayer = s2;
                timoliaRequestKit = "?";
            }

            if (clean.startsWith("Du wurdest zur Warteschlange hinzugef\u00fcgt!"))
            {
                timoliaRequestPlayer = "?";
                timoliaRequestKit = "?";
            }

            if (clean.startsWith("Kit: ") && clean.contains(" | Einstellungen: ") && !clean.contains(" | Einstellungen: -"))
            {
                String s3 = "";

                if (!clean.contains(timoliaRequestKit) || !timoliaRequestKit.isEmpty() || timoliaRequestKit.equals("?"))
                {
                    try
                    {
                        String[] astring1 = clean.split("Einstellungen");
                        timoliaRequestKit = astring1[0].replace("Kit: ", "").replace(" | ", "");
                        s3 = astring1[1].replace(": ", "");
                    }
                    catch (Exception var5)
                    {
                        s3 = "";
                    }
                }
            }

            if (clean.startsWith(timoliaRequestPlayer + " hat seine Herausforderung zur\u00fcckgezogen!"))
            {
                timoliaRequestPlayer = "";
                timoliaRequestKit = "";
            }

            if (clean.startsWith("Du hast den Kampf gegen ") || clean.startsWith("Dein Team hat den Kampf gegen das Team von "))
            {
                if (clean.contains("gewonnen"))
                {
                    ++timoliaStatsPlus;
                    ++winStreak;
                }

                if (clean.contains("verloren"))
                {
                    ++timoliaStatsMinus;
                    winStreak = 0;
                }

                timoliaRequestPlayer = "";
                timoliaRequestKit = "";
            }
        }
    }

    public static void drawTimoliaGui()
    {
        if (ConfigManager.settings.gameTimolia.booleanValue())
        {
            if (!LabyMod.getInstance().ip.isEmpty() && LabyMod.getInstance().ip.toLowerCase().contains("timolia.de"))
            {
                listenToTablist();
                ModGui.mainListNext();
                ModGui.addMainLabel("Lobby", timoliaLobby, ModGui.mainList);

                if (timoliaLobby.startsWith("games"))
                {
                    timoliaStatsPlus = 0;
                    timoliaStatsMinus = 0;
                }

                if (timoliaLobby.startsWith("pvp"))
                {
                    ModGui.addMainLabel("Stats", Color.cl("a") + timoliaStatsPlus + Color.cl("7") + " | " + Color.cl("c") + timoliaStatsMinus, ModGui.mainList);

                    if (!timoliaRequestPlayer.isEmpty())
                    {
                        String s = "Gegner";

                        if (!isInMatch())
                        {
                            s = "Herausforderung";
                        }

                        if (!timoliaRequestPlayer.equals("?"))
                        {
                            ModGui.addMainLabel(s, timoliaRequestPlayer, ModGui.mainList);
                        }

                        if (isInMatch())
                        {
                            ModGui.addMainLabel("Kit", timoliaRequestKit, ModGui.mainList);
                        }
                    }

                    if (winStreak != 0)
                    {
                        ModGui.addMainLabel("Winstreak", "" + winStreak, ModGui.mainList);
                    }
                }
            }
        }
    }

    public static boolean isInMatch()
    {
        return !timoliaRequestKit.equalsIgnoreCase(timoliaRequestPlayer);
    }
}

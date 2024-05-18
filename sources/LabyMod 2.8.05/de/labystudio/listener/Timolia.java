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
            String s = LabyMod.getInstance().getHeader();

            if (s.contains(" ") && s.contains("."))
            {
                String[] astring = s.split(" ");
                String s1 = astring[astring.length - 1].replace(".", " ").replace(" ", "");

                if (!timoliaLobby.equals(s1))
                {
                    ChatHandler.updateGameMode(s1);
                }

                timoliaLobby = s1;
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
            String s = Color.cl("r") + Color.cl("1") + "\u2502" + Color.cl("r") + Color.cl("9");

            if (raw.startsWith(s + " Nick") && clean.contains("Du wirst jetzt als "))
            {
                LabyMod.getInstance().nickname = clean.split("Du wirst jetzt als ")[1].replace(" angezeigt.", "");
            }

            if (raw.startsWith(s + " Nick") && clean.contains("Du wirst jetzt wieder als "))
            {
                LabyMod.getInstance().nickname = "";
            }

            if (raw.startsWith(Color.cl("r") + Color.cl("7") + Color.cl("1")) && clean.contains(" hat dich mit dem Kit ") && clean.contains("herausgefordert."))
            {
                try
                {
                    String[] astring = raw.split(Color.cl("7") + " " + Color.cl("r") + Color.cl("7"))[1].split(" hat dich mit dem Kit " + Color.cl("6"));
                    String s1 = astring[0];

                    if (s1 != null)
                    {
                        timoliaRequestPlayer = s1;
                    }

                    String s2 = astring[1].split(" ")[0];

                    if (s2 != null)
                    {
                        timoliaRequestKit = s2;
                    }
                }
                catch (Exception var6)
                {
                    timoliaRequestKit = "";
                    timoliaRequestPlayer = "";
                }
            }

            if (raw.startsWith(Color.cl("r") + Color.cl("1")) && clean.contains(" Du hast ") && clean.contains("herausgefordert."))
            {
                String[] astring1 = clean.split(" Du hast ")[1].split(" mit dem Kit ");
                timoliaRequestPlayer = astring1[0];
                timoliaRequestKit = astring1[1].split(" herausgefordert.")[0];
            }

            if (raw.startsWith(Color.cl("r") + Color.cl("1")) && clean.contains(" Der Kampf gegen ") && clean.contains(" beginnt."))
            {
                timoliaRequestPlayer = clean.split(" Der Kampf gegen ")[1].split(" beginnt.")[0];
            }

            if (raw.startsWith(Color.cl("r") + Color.cl("1")) && raw.contains(Color.cl("7") + "Kit: " + Color.cl("6")) && raw.contains(" " + Color.cl("8") + "- " + Color.cl("7") + "Einstellungen:"))
            {
                timoliaRequestKit = raw.split(Color.cl("7") + "Kit: " + Color.cl("6"))[1].split(" " + Color.cl("8") + "- " + Color.cl("7") + "Einstellungen:")[0];
            }

            if (raw.startsWith(Color.cl("r") + Color.cl("1")) && clean.contains(" Du wurdest zur Warteschlange hinzugef\u00fcgt."))
            {
                timoliaRequestPlayer = "[Warteschlange]";
                timoliaRequestKit = "?";
            }

            if (raw.startsWith(Color.cl("r") + Color.cl("1")) && clean.contains(" Du wurdest aus der Warteschlange entfernt"))
            {
                timoliaRequestPlayer = "?";
                timoliaRequestKit = "?";
            }

            if (raw.startsWith(Color.cl("r") + Color.cl("1")) && clean.contains(" hat seine Herausforderung zur\u00fcckgezogen."))
            {
                timoliaRequestPlayer = "?";
                timoliaRequestKit = "?";
            }

            if (raw.startsWith(Color.cl("r") + Color.cl("7") + Color.cl("1")) && (clean.contains("hast den Kampf gegen") || clean.contains("habt den Kampf gegen ")))
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
        if (ConfigManager.settings.gameTimolia)
        {
            if (isTimolia())
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

package de.labystudio.utils;

import de.labystudio.labymod.ConfigManager;

public class Debug
{
    private static Debug.EnumDebugMode aktiveMode = Debug.EnumDebugMode.NONE;
    private static String lastDebugMessage = "";
    private static int stackingMessages = 0;
    private static long checkSpam = 0L;
    private static Debug.EnumDebugMode lastDebugMode = Debug.EnumDebugMode.NONE;

    public static Debug.EnumDebugMode getAktiveMode()
    {
        return aktiveMode;
    }

    public static boolean debug(Debug.EnumDebugMode mode, String message, int stackSize, boolean aktive)
    {
        if (mode != getAktiveMode() && !aktive)
        {
            return false;
        }
        else if (lastDebugMessage.equals(message) && stackingMessages <= stackSize)
        {
            ++stackingMessages;
            lastDebugMode = mode;
            checkSpam = System.currentTimeMillis();
            return true;
        }
        else
        {
            if (stackingMessages != 0)
            {
                ++stackingMessages;
                outStack(lastDebugMode.name(), stackingMessages, lastDebugMessage);
                stackingMessages = 0;
                checkSpam = 0L;
                lastDebugMode = Debug.EnumDebugMode.NONE;
            }

            if (!lastDebugMessage.equals(message))
            {
                out(mode.name(), message);
            }

            lastDebugMessage = message;
            return true;
        }
    }

    public static boolean debug(Debug.EnumDebugMode mode, String message)
    {
        return debug(mode, message, 10, false);
    }

    public static boolean debug(String message)
    {
        return debug(Debug.EnumDebugMode.NONE, message, 10, true);
    }

    public static boolean debug(String message, int stackSize)
    {
        return debug(Debug.EnumDebugMode.NONE, message, stackSize, true);
    }

    private static void out(String prefix, String message)
    {
        outStack(prefix, 1, message);
    }

    private static void outStack(String prefix, int amount, String message)
    {
        String s = "[Debug]";

        if (!prefix.equals("NONE"))
        {
            s = s + "[" + prefix + "]";
        }

        if (amount > 1)
        {
            System.out.println(s + "[" + amount + "x] " + message);
        }
        else
        {
            System.out.println(s + " " + message);
        }
    }

    public static void updateDebugMessages()
    {
        if (stackingMessages != 0 && checkSpam + 1000L < System.currentTimeMillis())
        {
            out(lastDebugMode.name(), lastDebugMessage);
            stackingMessages = 0;
            checkSpam = 0L;
            lastDebugMode = Debug.EnumDebugMode.NONE;
        }

        if (api())
        {
            aktiveMode = Debug.EnumDebugMode.API;
        }
        else if (capes())
        {
            aktiveMode = Debug.EnumDebugMode.CAPES;
        }
        else if (chat())
        {
            aktiveMode = Debug.EnumDebugMode.CHAT;
        }
        else if (server())
        {
            aktiveMode = Debug.EnumDebugMode.SERVER;
        }
        else if (teamspeak())
        {
            aktiveMode = Debug.EnumDebugMode.TEAMSPEAK;
        }
        else if (timings())
        {
            aktiveMode = Debug.EnumDebugMode.TIMINGS;
        }
        else
        {
            aktiveMode = Debug.EnumDebugMode.NONE;
        }
    }

    public static boolean chat()
    {
        return ConfigManager.settings == null ? false : ConfigManager.settings.motd.startsWith("/debug chat");
    }

    public static boolean teamspeak()
    {
        return ConfigManager.settings == null ? false : ConfigManager.settings.motd.equals("/debug ts");
    }

    public static boolean timings()
    {
        return ConfigManager.settings == null ? false : ConfigManager.settings.motd.equals("/debug timings");
    }

    public static boolean server()
    {
        return ConfigManager.settings == null ? false : ConfigManager.settings.motd.equals("/debug chat-setport");
    }

    public static boolean capes()
    {
        return ConfigManager.settings == null ? false : ConfigManager.settings.motd.equals("/debug capes");
    }

    public static boolean api()
    {
        return ConfigManager.settings == null ? false : ConfigManager.settings.motd.equals("/debug api");
    }

    public static enum EnumDebugMode
    {
        API,
        CAPES,
        SERVER,
        TIMINGS,
        TEAMSPEAK,
        CHAT,
        NONE;
    }
}

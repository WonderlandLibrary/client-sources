package de.labystudio.labymod;

public class ClickCounter
{
    static long time = 0L;
    static double clicks = 0.0D;
    static double result = 0.0D;

    public static double getClickResult()
    {
        return result;
    }

    public static void click()
    {
        if (ConfigManager.settings.clickTest)
        {
            ++clicks;
        }
    }

    public static void tick()
    {
        if (ConfigManager.settings.clickTest && time < System.currentTimeMillis())
        {
            result = clicks;
            time = System.currentTimeMillis() + 1000L;
            clicks = 0.0D;
        }
    }
}

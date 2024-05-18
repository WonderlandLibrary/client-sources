package mods.batty.main;

import java.util.logging.Logger;
import net.minecraft.util.MathHelper;

public class BattyUtils
{
    private static BattyUI ui = BattyMod.getInstance().getBatheartgui();

    public static int nameSearch(String[] names, String name)
    {
        for (int i = 0; i < names.length; ++i)
        {
            if (names[i].equals(name))
            {
                return i;
            }
        }

        return -1;
    }

    public static int getCardinalPoint(float par0)
    {
        double d0 = (double)MathHelper.wrapAngleTo180_float(par0) + 180.0D;
        d0 = d0 + 22.5D;
        d0 = d0 % 360.0D;
        d0 = d0 / 45.0D;
        return MathHelper.floor_double(d0);
    }

    public static String constructCoordVisString()
    {
        String s = "";
        s = s + ui.showCoords;
        return s;
    }

    public static String constructTimerVisString()
    {
        String s;

        if (ui.hideTimer)
        {
            s = "false";
        }
        else
        {
            s = "true";
        }

        return s;
    }

    public static String constructCoordLocString()
    {
        String s = "";
        s = s + ui.coordLocation;
        return s;
    }

    public static String constructTimerRunString()
    {
        String s;

        if (ui.timerRunning)
        {
            s = "true";
        }
        else
        {
            s = "false";
        }

        return s;
    }

    public static String constructFPSVisString()
    {
        String s;

        if (ui.hideFPS)
        {
            s = "false";
        }
        else
        {
            s = "true";
        }

        return s;
    }

    public static String constructFPSLocString()
    {
        String s = "";
        s = s + ui.fpsLocation;
        return s;
    }

    public static String constructTimerLocString()
    {
        String s = "";
        s = s + ui.timerLocation;
        return s;
    }

    public static void parseTimeString(String var1)
    {
        Logger.getLogger("Minecraft").info(var1);
        String[] astring = var1.split("\\|");
        ui.hourCounter = Integer.parseInt(astring[0]);
        ui.minuteCounter = Integer.parseInt(astring[1]);
        ui.secondCounter = Integer.parseInt(astring[2]);
    }

    public static String constructTimeString()
    {
        String s = "";
        s = s + (ui.hourCounter >= 10 ? "" : "0");
        s = s + ui.hourCounter;
        s = s + ":";
        s = s + (ui.minuteCounter >= 10 ? "" : "0");
        s = s + ui.minuteCounter;
        s = s + ":";
        s = s + (ui.secondCounter >= 10 ? "" : "0");
        s = s + ui.secondCounter;
        return s;
    }

    public static String getSaveString()
    {
        return constructTimeString().replace(":", "|");
    }

    public static void resetTimer()
    {
        ui.resetTimer = false;
        ui.tickCounter = ui.hourCounter = ui.minuteCounter = ui.secondCounter = 0;
        BattyConfig.storeRuntimeOptions();
    }

    public static void addOneSecond()
    {
        ++ui.secondCounter;

        if (ui.secondCounter >= 60)
        {
            ui.secondCounter -= 60;
            ++ui.minuteCounter;
        }

        if (ui.minuteCounter >= 60)
        {
            ui.minuteCounter -= 60;
            ++ui.hourCounter;
        }
    }

    public static void updateTimer(int var1)
    {
        if (ui.resetTimer)
        {
            resetTimer();
        }

        if (ui.toggleTimer)
        {
            ui.toggleTimer = false;
            ui.tickCounter = 0;
            ui.timerRunning = !ui.timerRunning;
            BattyConfig.storeRuntimeOptions();
        }

        if (ui.timerRunning)
        {
            if (ui.tickCounter == 0)
            {
                ui.tickCounter = var1;
            }

            if (var1 - ui.tickCounter >= 20)
            {
                addOneSecond();
                ui.tickCounter += 20;
            }
        }
    }
}

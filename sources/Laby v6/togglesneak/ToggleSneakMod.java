package togglesneak;

import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.Module;
import java.io.File;

public class ToggleSneakMod extends Module
{
    public static final String ModID = "ToggleSneak";
    public static final String ModName = "ToggleSneak";
    public static final String ModVersion = "3.1.1";
    public static Configuration config = null;
    public static File configFile = null;
    public static boolean optionToggleSprint = true;
    public static boolean optionToggleSneak = true;
    public static boolean optionShowHUDText = true;
    public static int optionHUDTextPosX = 1;
    public static int optionHUDTextPosY = 1;
    public static boolean optionDoubleTap = false;
    public static boolean optionEnableFlyBoost = false;
    public static double optionFlyBoostAmount = 4.0D;
    public static String optionPositionMode = "CUSTOM";
    public static boolean wasSprintDisabled = false;

    public void onEnable()
    {
        updateConfig(new File("ToggleSneak_labymod.cfg"), true);
        ModAPI.registerListener(new ToggleSneakModEvents());
        ModAPI.addSettingsButton("ToggleSneak", new GuiTSConfig());
    }

    public static void reloadConfig()
    {
        updateConfig(configFile, true);
    }

    public static void saveConfig()
    {
        updateConfig(configFile, false);
    }

    public static void updateConfig(File cfgFile, boolean isLoading)
    {
        if (isLoading)
        {
            config = new Configuration(cfgFile);
            config.load();
            configFile = cfgFile;
        }

        Property property = config.get("optionToggleSprint", Boolean.valueOf(optionToggleSprint));

        if (isLoading)
        {
            optionToggleSprint = property.getBoolean();
        }
        else
        {
            property.set(optionToggleSprint + "");
        }

        property = config.get("optionToggleSneak", Boolean.valueOf(optionToggleSneak));

        if (isLoading)
        {
            optionToggleSneak = property.getBoolean();
        }
        else
        {
            property.set(optionToggleSneak + "");
        }

        property = config.get("optionShowHUDText", Boolean.valueOf(optionShowHUDText));

        if (isLoading)
        {
            optionShowHUDText = property.getBoolean();
        }
        else
        {
            property.set(optionShowHUDText + "");
        }

        property = config.get("optionPositionMode", optionPositionMode);

        if (isLoading)
        {
            optionPositionMode = property.getString();
        }
        else
        {
            property.set(optionPositionMode + "");
        }

        property = config.get("optionHUDTextPosX", Integer.valueOf(optionHUDTextPosX));

        if (isLoading)
        {
            optionHUDTextPosX = property.getInt();
        }
        else
        {
            property.set(optionHUDTextPosX + "");
        }

        property = config.get("optionHUDTextPosY", Integer.valueOf(optionHUDTextPosY));

        if (isLoading)
        {
            optionHUDTextPosY = property.getInt();
        }
        else
        {
            property.set(optionHUDTextPosY + "");
        }

        property = config.get("optionDoubleTap", Boolean.valueOf(optionDoubleTap));

        if (isLoading)
        {
            optionDoubleTap = property.getBoolean();
        }
        else
        {
            property.set(optionDoubleTap + "");
        }

        property = config.get("optionEnableFlyBoost", Boolean.valueOf(optionEnableFlyBoost));

        if (isLoading)
        {
            optionEnableFlyBoost = property.getBoolean();
        }
        else
        {
            property.set(optionEnableFlyBoost + "");
        }

        property = config.get("optionFlyBoostAmount", Double.valueOf(optionFlyBoostAmount));

        if (isLoading)
        {
            optionFlyBoostAmount = property.getDouble();
        }
        else
        {
            property.set(optionFlyBoostAmount + "");
        }

        config.save();
    }
}

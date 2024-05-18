package mods.betterhat.main;

import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.Module;

public class BetterHat extends Module
{
    private static BetterHat instance;
    public static final String[] fTextureOffsetX = new String[] {"textureOffsetX", "r", "textureOffsetX"};
    public static final String[] fTextureOffsetY = new String[] {"textureOffsetY", "s", "textureOffsetY"};

    public void onEnable()
    {
        instance = this;
        ModAPI.registerListener(new BetterHatListener());
    }

    public static BetterHat getInstance()
    {
        return instance;
    }
}

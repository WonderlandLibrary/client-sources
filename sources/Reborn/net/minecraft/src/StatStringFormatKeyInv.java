package net.minecraft.src;

import net.minecraft.client.*;

public class StatStringFormatKeyInv implements IStatStringFormat
{
    final Minecraft mc;
    
    public StatStringFormatKeyInv(final Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
    }
    
    @Override
    public String formatString(final String par1Str) {
        try {
            return String.format(par1Str, GameSettings.getKeyDisplayString(this.mc.gameSettings.keyBindInventory.keyCode));
        }
        catch (Exception var3) {
            return "Error: " + var3.getLocalizedMessage();
        }
    }
}

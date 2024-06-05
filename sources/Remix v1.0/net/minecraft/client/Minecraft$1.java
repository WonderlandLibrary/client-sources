package net.minecraft.client;

import net.minecraft.stats.*;
import net.minecraft.client.settings.*;

class Minecraft$1 implements IStatStringFormat {
    @Override
    public String formatString(final String p_74535_1_) {
        try {
            return String.format(p_74535_1_, GameSettings.getKeyDisplayString(Minecraft.this.gameSettings.keyBindInventory.getKeyCode()));
        }
        catch (Exception var3) {
            return "Error: " + var3.getLocalizedMessage();
        }
    }
}
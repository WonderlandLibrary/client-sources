package net.minecraft.client;

import net.minecraft.client.gui.*;

class Minecraft$5 implements GuiYesNoCallback {
    @Override
    public void confirmClicked(final boolean result, final int id) {
        if (result) {
            Minecraft.this.getTwitchStream().func_152930_t();
        }
        Minecraft.this.displayGuiScreen(null);
    }
}
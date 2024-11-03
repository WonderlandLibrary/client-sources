package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiOptions.class)
public class GuiOptionsMixin extends GuiScreen {
    @Override
    public void onGuiClosed() {
        mc.gameSettings.saveOptions();
    }
}

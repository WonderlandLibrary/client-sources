package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.silentclient.client.mixin.ducks.GameSettingsExt;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiVideoSettings.class)
public class GuiVideoSettingsMixin extends GuiScreen {
    //#if MC==10809
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        ((GameSettingsExt) mc.gameSettings).silent$onSettingsGuiClosed();
    }
    //#endif
}

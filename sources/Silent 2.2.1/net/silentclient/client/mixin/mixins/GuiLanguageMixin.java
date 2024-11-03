package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiLanguage.class)
public class GuiLanguageMixin extends GuiScreen {
    @Override
    public void onGuiClosed() {
        mc.ingameGUI.getChatGUI().refreshChat();
    }
}

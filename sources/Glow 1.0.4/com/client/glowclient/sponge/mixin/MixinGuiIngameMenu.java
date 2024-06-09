package com.client.glowclient.sponge.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import java.util.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.text.*;

@Mixin({ GuiIngameMenu.class })
public abstract class MixinGuiIngameMenu extends GuiScreen
{
    private int saveStep;
    private int visibleTime;
    
    public MixinGuiIngameMenu() {
        super();
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") }, cancellable = true)
    public void postInitGui(final CallbackInfo callbackInfo) {
        HookTranslator.m17(GuiIngameMenu.class.cast(this), this.buttonList);
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") }, cancellable = true)
    public void preInitGui(final CallbackInfo callbackInfo) {
        final GuiButton guiButton = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 - 16 + 24, I18n.format("menu.returnToMenu", new Object[0]));
        this.buttonList.add(guiButton);
        this.buttonList.add(new GuiButton(69, this.width / 2 - 100, this.height / 4 + 120 - 16, I18n.format("Reconnect", new Object[0])));
        if (!this.mc.isIntegratedServerRunning()) {
            guiButton.displayString = I18n.format("menu.disconnect", new Object[0]);
        }
        this.buttonList.remove(0);
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("RETURN") }, cancellable = true)
    public void preActionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) {
        if (guiButton.id == 69) {
            HookTranslator.m16().closeChannel((ITextComponent)new TextComponentString("Reconnecting..."));
        }
        HookTranslator.m18(GuiIngameMenu.class.cast(this), guiButton);
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("RETURN") }, cancellable = true)
    public void postDrawScreen(final CallbackInfo callbackInfo) {
        HookTranslator.m19();
    }
    
    @Inject(method = { "updateScreen" }, at = { @At("RETURN") }, cancellable = true)
    protected void preUpdateScreen(final CallbackInfo callbackInfo) {
        HookTranslator.m20();
    }
}

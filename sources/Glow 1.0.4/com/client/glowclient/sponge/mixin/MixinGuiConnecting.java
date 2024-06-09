package com.client.glowclient.sponge.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraftforge.fml.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiConnecting.class })
public abstract class MixinGuiConnecting extends GuiScreen
{
    @Shadow
    @Final
    private GuiScreen field_146374_i;
    
    public MixinGuiConnecting() {
        super();
    }
    
    private ServerData getLastConnectedServerData() {
        return (HookTranslator.m11() != null) ? HookTranslator.m11() : HookTranslator.mc.getCurrentServerData();
    }
    
    private void reconnect() {
        final ServerData lastConnectedServerData = this.getLastConnectedServerData();
        if (lastConnectedServerData != null) {
            FMLClientHandler.instance().showGuiScreen((Object)new GuiConnecting(this.previousGuiScreen, HookTranslator.mc, lastConnectedServerData));
        }
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") }, cancellable = true)
    private void initGui(final CallbackInfo callbackInfo) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12 + 22, I18n.format("Retry Connection", new Object[0])));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") }, cancellable = true)
    private void actionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) {
        if (guiButton.id == 1) {
            this.reconnect();
        }
    }
}

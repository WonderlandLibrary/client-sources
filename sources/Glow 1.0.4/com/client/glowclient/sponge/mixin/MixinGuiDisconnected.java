package com.client.glowclient.sponge.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.text.*;
import java.util.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.multiplayer.*;

@Mixin({ GuiDisconnected.class })
public abstract class MixinGuiDisconnected extends GuiScreen
{
    @Shadow
    public String field_146306_a;
    @Shadow
    public ITextComponent field_146304_f;
    @Shadow
    public List<String> field_146305_g;
    @Shadow
    public GuiScreen field_146307_h;
    @Shadow
    public int field_175353_i;
    private boolean shouldReconnect;
    private GuiButton reconnectButton;
    private GuiButton reconnectButtonSimple;
    private double reconnectTimer;
    
    public MixinGuiDisconnected() {
        super();
        this.shouldReconnect = true;
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") }, cancellable = true)
    private void initGui(final CallbackInfo callbackInfo) {
        this.reconnectTimer = 0.0;
        this.reconnectButton = new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 22, "Reconnect");
        if (HookTranslator.m13()) {
            this.reconnectButtonSimple = new GuiButton(2, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 44, "Reconnect");
            this.buttonList.add(this.reconnectButton);
        }
        else {
            this.reconnectButtonSimple = new GuiButton(2, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 22, "Reconnect");
            this.shouldReconnect = false;
        }
        this.buttonList.add(this.reconnectButtonSimple);
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") }, cancellable = true)
    private void actionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) {
        switch (guiButton.id) {
            case 1: {
                if (this.shouldReconnect) {
                    this.shouldReconnect = false;
                    break;
                }
                this.shouldReconnect = true;
                break;
            }
            case 2: {
                this.reconnect();
                break;
            }
        }
    }
    
    public void updateScreen() {
        HookTranslator.m63();
        if (this.shouldReconnect) {
            this.reconnectTimer += 0.1;
        }
        if (this.reconnectTimer > HookTranslator.m12() * 2.0) {
            this.reconnect();
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("RETURN") }, cancellable = true)
    private void drawScreen(final CallbackInfo callbackInfo) {
        this.reconnectButton.displayString = "Reconnecting (" + (this.shouldReconnect ? "§6ON" : "§4OFF") + "§r) (" + String.format("%.1f", HookTranslator.m12() - this.reconnectTimer / 2.0) + ")...";
    }
    
    private ServerData getLastConnectedServerData() {
        return (HookTranslator.m64() != null) ? HookTranslator.m64() : HookTranslator.mc.getCurrentServerData();
    }
    
    private void reconnect() {
        final ServerData lastConnectedServerData = this.getLastConnectedServerData();
        if (lastConnectedServerData != null) {
            FMLClientHandler.instance().showGuiScreen((Object)new GuiConnecting(this.parentScreen, HookTranslator.mc, lastConnectedServerData));
        }
    }
}

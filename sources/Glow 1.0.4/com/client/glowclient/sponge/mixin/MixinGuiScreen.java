package com.client.glowclient.sponge.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import java.io.*;

@Mixin({ GuiScreen.class })
public abstract class MixinGuiScreen extends Gui implements GuiYesNoCallback
{
    public MixinGuiScreen() {
        super();
    }
    
    @Inject(method = { "keyTyped" }, at = { @At("RETURN") }, cancellable = true)
    protected void preKeyTyped(final char c, final int n, final CallbackInfo callbackInfo) {
        if (GuiScreen.class.cast(this) instanceof GuiIngameMenu) {
            HookTranslator.m21(c, n);
        }
    }
    
    @Inject(method = { "mouseClicked" }, at = { @At("RETURN") }, cancellable = true)
    protected void postMouseClicked(final int n, final int n2, final int n3, final CallbackInfo callbackInfo) throws IOException {
        if (GuiScreen.class.cast(this) instanceof GuiIngameMenu) {
            HookTranslator.m22(n, n2, n3);
        }
    }
}

package com.client.glowclient.sponge.mixin;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiChat.class })
public abstract class MixinGuiChat extends GuiScreen
{
    @Shadow
    protected GuiTextField field_146415_a;
    
    public MixinGuiChat() {
        super();
    }
    
    @Inject(method = { "keyTyped" }, at = { @At("HEAD") }, cancellable = true)
    public void runGivenCommand(final char c, final int n, final CallbackInfo callbackInfo) {
        HookTranslator.m10(this.inputField, n, callbackInfo);
    }
}

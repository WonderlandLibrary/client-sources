package com.cout970.fira.coremod.mixin;

import com.cout970.fira.Config;
import com.cout970.fira.util.Misc;
import net.minecraft.client.gui.GuiDisconnected;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiDisconnected.class)
public class MixinGuiDisconnected {

    @Inject(
        method = "initGui",
        at = @At("HEAD"),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void initGui(CallbackInfo ci) {
        if (!Config.Debug.debugMode) return;
        Misc.INSTANCE.autoConnect(2000L);
    }
}

package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.GuiGameOver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGameOver.class)
public class GuiGameOverMixin {
    //#if MC==10809
    @Shadow private int enableButtonsTimer;

    @Inject(method = "initGui", at = @At("HEAD"))
    private void silent$allowClickable(CallbackInfo ci) {
        this.enableButtonsTimer = 0;
    }
    //#endif
}

/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.event.OpenScreenListener;
import de.dietrichpaul.clientbase.event.PreTickRaytraceListener;
import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;updateTargetedEntity(F)V", shift = At.Shift.BEFORE))
    public void onPreRaytrace(CallbackInfo ci) {
        ClientBase.INSTANCE.getEventDispatcher().post(new PreTickRaytraceListener.PreTickRaytraceEvent());
    }

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void onSetScreen(Screen screen, CallbackInfo ci) {
        if (ClientBase.INSTANCE.getEventDispatcher().post(new OpenScreenListener.OpenScreenEvent(screen)).isCancelled())
            ci.cancel();
    }
}

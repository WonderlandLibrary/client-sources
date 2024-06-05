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

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.MoveCameraListener;
import de.dietrichpaul.clientbase.event.RaytraceListener;
import de.dietrichpaul.clientbase.injection.accessor.IGameRenderer;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "updateTargetedEntity", at = @At("HEAD"), cancellable = true)
    public void onUpdateTargetedEntity(float tickDelta, CallbackInfo ci) {
        if (!((IGameRenderer) this).isCustomRaytrace()) {
            RaytraceListener.RaytraceEvent raytraceEvent = ClientBase.INSTANCE.getEventDispatcher()
                    .post(new RaytraceListener.RaytraceEvent(tickDelta));

            if (raytraceEvent.isCancelled())
                ci.cancel();
        }
    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;skipGameRender:Z", shift = At.Shift.BEFORE))
    public void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        ClientBase.INSTANCE.getEventDispatcher().post(new MoveCameraListener.MoveCameraEvent(tickDelta));
    }
}

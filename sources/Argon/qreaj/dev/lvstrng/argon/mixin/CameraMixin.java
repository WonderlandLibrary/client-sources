// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.event.events.CameraEvent;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin({Camera.class})
public class CameraMixin {
    @ModifyArgs(method = {"update"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"))
    private void update(final Args args) {
        final CameraEvent event = new CameraEvent(args.get(0), args.get(1), args.get(2));
        EventBus.postEvent(event);
        args.set(0, (Object) event.getX());
        args.set(1, (Object) event.getY());
        args.set(2, (Object) event.getZ());
    }
}

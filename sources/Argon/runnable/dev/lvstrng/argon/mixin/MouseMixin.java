// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.event.events.MouseMoveEvent;
import dev.lvstrng.argon.event.events.MouseUpdateEvent;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Mouse.class})
public class MouseMixin {
    @Inject(method = {"updateMouse"}, at = {@At("RETURN")})
    private void onMouseUpdate(final CallbackInfo ci) {
        EventBus.postEvent(new MouseUpdateEvent());
    }

    @Inject(method = {"onCursorPos"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseMove(final long window, final double x, final double y, final CallbackInfo ci) {
        final MouseMoveEvent event = new MouseMoveEvent(window, x, y);
        EventBus.postEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}

// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.event.events.Render2DEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InGameHud.class})
public class InGameHudMixin {
    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void onRenderHud(final DrawContext context, final float tickDelta, final CallbackInfo ci) {
        EventBus.postEvent(new Render2DEvent(context, tickDelta));
    }
}

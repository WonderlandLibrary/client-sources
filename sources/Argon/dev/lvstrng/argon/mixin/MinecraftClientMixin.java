// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.event.events.*;
import dev.lvstrng.argon.utils.Mouse;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

@Mixin({MinecraftClient.class})
public class MinecraftClientMixin {
    @Shadow
    @Final
    private Window window;

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void onTick(final CallbackInfo ci) {

        EventBus.postEvent(new TickEvent());

    }

    @Inject(method = {"onResolutionChanged"}, at = {@At("HEAD")})
    private void onResolutionChanged(final CallbackInfo ci) {
        EventBus.postEvent(new ResolutionChangedEvent(this.window));
    }

    @Inject(method = {"doItemUse"}, at = {@At("HEAD")}, cancellable = true)
    private void onItemUse(final CallbackInfo ci) {
        final ItemUseEvent event = new ItemUseEvent();
        EventBus.postEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
        if (Mouse.isKeyPressed(1)) {
            Mouse.inputs.put(1, false);
            ci.cancel();
        }
    }

    @Inject(method = {"doAttack"}, at = {@At("HEAD")}, cancellable = true)
    private void onAttack(final CallbackInfoReturnable cir) {
        final AttackEvent event = new AttackEvent();
        EventBus.postEvent(event);
        if (event.isCancelled()) {
            cir.setReturnValue(false);
        }
        if (Mouse.isKeyPressed(0)) {
            Mouse.inputs.put(0, false);
            cir.setReturnValue(false);
        }
    }

    @Inject(method = {"handleBlockBreaking"}, at = {@At("HEAD")}, cancellable = true)
    private void onBlockBreaking(final boolean breaking, final CallbackInfo ci) {
        final BreakBlockEvent event = new BreakBlockEvent();
        EventBus.postEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
        if (Mouse.isKeyPressed(0)) {
            Mouse.inputs.put(0, false);
            ci.cancel();
        }
    }

    @Inject(method = {"stop"}, at = {@At("HEAD")})
    private void onClose(final CallbackInfo ci) throws IOException {
        Argon.INSTANCE.getConfigManager().saveConfig();
    }
}

package in.momin5.cookieclient.api.mixin.mixins;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.WaterPushEvent;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer {
    @Shadow
    public abstract String getName();


    @Inject(method = "isPushedByWater", at = @At("HEAD"), cancellable = true)
    private void onPushedByWater(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        WaterPushEvent event = new WaterPushEvent();
        CookieClient.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}

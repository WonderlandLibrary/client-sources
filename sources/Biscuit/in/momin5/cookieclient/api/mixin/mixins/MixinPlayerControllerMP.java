package in.momin5.cookieclient.api.mixin.mixins;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.ClickBlockEvent;
import in.momin5.cookieclient.api.event.events.DamageBlockEvent;
import in.momin5.cookieclient.api.event.events.DestroyBlockEvent;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.client.modules.player.MultiTask;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP {
    @Shadow
    public abstract void syncCurrentPlayItem();

    @Inject(method = "clickBlock", at = @At(value = "HEAD"), cancellable = true)
    private void onClickBlock(BlockPos position, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
        ClickBlockEvent event = new ClickBlockEvent(position, face);
        CookieClient.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            cir.cancel();
        }
    }

    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
    private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        CookieClient.EVENT_BUS.post(new DestroyBlockEvent(pos));
    }

    @Inject(method = "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z", at = @At("HEAD"), cancellable = true)
    private void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        DamageBlockEvent event = new DamageBlockEvent(posBlock, directionFacing);
        CookieClient.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = "resetBlockRemoving", at = @At("HEAD"), cancellable = true)
    private void resetBlock(CallbackInfo callbackInfo) {
        if (ModuleManager.isModuleEnabled(MultiTask.class)) {
            callbackInfo.cancel();
        }
    }

}

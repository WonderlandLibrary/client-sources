package me.finz0.osiris.mixin.mixins;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.DestroyBlockEvent;
import me.finz0.osiris.module.ModuleManager;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
    private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        AuroraMod.EVENT_BUS.post(new DestroyBlockEvent(pos));
    }

    //credit cookiedragon234
    @Inject(method = "resetBlockRemoving", at = @At("HEAD"), cancellable = true)
    private void resetBlock(CallbackInfo ci){
        if(ModuleManager.isModuleEnabled("BreakTweaks")) ci.cancel();
    }

}

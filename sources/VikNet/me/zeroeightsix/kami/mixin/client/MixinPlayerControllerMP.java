package me.zeroeightsix.kami.mixin.client;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.DestroyBlockEvent;
import me.zeroeightsix.kami.module.ModuleManager;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created by 086 on 3/10/2018.
 */
@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
    private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        KamiMod.EVENT_BUS.post(new DestroyBlockEvent(pos));
    }

    //credit cookiedragon234
    @Inject(method = "resetBlockRemoving", at = @At("HEAD"), cancellable = true)
    private void resetBlock(CallbackInfo ci){
        if(ModuleManager.isModuleEnabled("BreakTweaks")) ci.cancel();
    }

}
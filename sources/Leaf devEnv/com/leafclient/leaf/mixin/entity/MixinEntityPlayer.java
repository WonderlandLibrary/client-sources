package com.leafclient.leaf.mixin.entity;

import com.leafclient.leaf.event.game.entity.PlayerSlowEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {

    private PlayerSlowEvent.Attack e;

    @ModifyConstant(
            method = "attackTargetEntityWithCurrentItem",
            constant = @Constant(doubleValue = 0.6)
    )
    private double modifyAttackSlow(double factor) {
        e = EventManager.INSTANCE.publish(new PlayerSlowEvent.Attack(factor));
        if(e.isCancelled())
            return 1.0;

        return e.getFactor();
    }

    @Inject(
            method = "attackTargetEntityWithCurrentItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;setSprinting(Z)V"
            ),
            cancellable = true
    )
    private void inject$sprinting(CallbackInfo info) {
        if(e.isSprinting() || e.isCancelled())
            info.cancel();
    }

}

package com.cout970.fira.coremod.mixin;

import com.cout970.fira.modules.CrystalPvP;
import net.minecraft.world.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public class MixinExplosion {

    @Inject(
        method = "doExplosionA",
        at = @At("HEAD"),
        cancellable = true
    )
    public void doExplosionA(CallbackInfo ci) {
        if (CrystalPvP.INSTANCE.suppressExplosions()) {
            ci.cancel();
        }
    }

    @Inject(
        method = "doExplosionB",
        at = @At("HEAD"),
        cancellable = true
    )
    public void doExplosionB(CallbackInfo ci) {
        if (CrystalPvP.INSTANCE.suppressExplosions()) {
            ci.cancel();
        }
    }
}
